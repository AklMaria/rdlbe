package com.rdlbe.foundations.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdlbe.foundations.exceptions.SystemException;
import org.springframework.data.domain.Pageable;

import javax.sql.DataSource;
import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DBUtils {

    private DBUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static <R> R connect(DataSource ds, Function<Connection,R> f) throws SQLException {
        try (var conn = ds.getConnection()) {
            return f.apply(conn);
        }
    }

    public static <R> R execute(DataSource ds, Function<Statement,R> f) throws SQLException {
        try (var conn = ds.getConnection(); var stmt = conn.createStatement()) {
            return f.apply(stmt);
        }
    }

    public static Boolean getBoolean(ResultSet rs, String columnName) throws SQLException {
        boolean value = rs.getBoolean(columnName);
        return rs.wasNull() ? null : value;
    }

    public static Byte getByte(ResultSet rs, String columnName) throws SQLException {
        byte value = rs.getByte(columnName);
        return rs.wasNull() ? null : value;
    }

    public static Long getLong(ResultSet rs, String columnName) throws SQLException {
        long value = rs.getLong(columnName);
        return rs.wasNull() ? null : value;
    }

    public static Integer getInt(ResultSet rs, String columnName) throws SQLException {
        int value = rs.getInt(columnName);
        return rs.wasNull() ? null : value;
    }

    public static Short getShort(ResultSet rs, String columnName) throws SQLException {
        short value = rs.getShort(columnName);
        return rs.wasNull() ? null : value;
    }

    public static Float getFloat(ResultSet rs, String columnName) throws SQLException {
        float value = rs.getFloat(columnName);
        return rs.wasNull() ? null : value;
    }

    public static Double getDouble(ResultSet rs, String columnName) throws SQLException {
        double value = rs.getFloat(columnName);
        return rs.wasNull() ? null : value;
    }

    public static ZonedDateTime getZonedDateTime(ResultSet rs, String columnName) throws SQLException {
        Timestamp ts = rs.getTimestamp(columnName);
        return ts == null ? null : ts.toInstant().atZone(ZoneId.systemDefault());
    }

    public static <T> T getObject(ResultSet rs, String columnName, ObjectMapper objectMapper, Class<T> clazz) throws SQLException {
        var obj = rs.getString(columnName);
        try {
            return obj == null ? null : objectMapper.readValue(obj, clazz);
        } catch (JsonProcessingException e) {
            throw new SystemException(e);
        }
    }

    public static String buildQuery(String sql, Pageable pageable) {
        var q = sql;

        if (pageable != null) {
            var sort = pageable.getSort();
            if (sort.isSorted()) {
                q += sort.stream()
                        .map(order -> String.format("%s %s", order.getProperty(), order.isAscending() ? "asc" : "desc"))
                        .collect(Collectors.joining(", ", " ORDER BY ", ""));
            }

            if (pageable.isPaged()) {
                q += " LIMIT " + pageable.getPageSize() + " OFFSET " + pageable.getOffset();
            }
        }

        return q;
    }

    public static String andConditions(final Map<String, ?> filters, final Map<String,String> fieldMap) {
        if (filters == null || filters.isEmpty()) {
            return "";
        }

        var conditions = filters.entrySet().stream()
                .filter(k -> fieldMap.containsKey(k.getKey()))
                .map(entry -> {
                    var k = fieldMap.get(entry.getKey());
                    if (entry.getValue() == null) {
                        return String.format("%s is null", k);
                    }

                    var op = "=";
                    if (entry.getValue() instanceof String value && value.contains("*")) {
                        op = "LIKE";
                    }

                    return String.format("%s %s :%s", k, op, entry.getKey());
                })
                .toList();

        return conditions.isEmpty() ? "" : conditions.stream().collect(Collectors.joining(" AND ", " WHERE ", ""));
    }

    public static Map<String, Object> mapFilters(final Map<String, ?> filters) {
        return filters == null ? Map.of() : filters.entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .map(entry -> {
                    if (entry.getValue() instanceof String s) {
                        return Map.entry(entry.getKey(), s.replace("*", "%"));
                    }

                    return entry;
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}

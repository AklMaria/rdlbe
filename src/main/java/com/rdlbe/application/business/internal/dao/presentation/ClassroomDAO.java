package com.rdlbe.application.business.internal.dao.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdlbe.application.business.internal.domains.Classroom;
import com.rdlbe.foundations.core.Dao;
import jakarta.annotation.Nonnull;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ClassroomDAO extends Dao<Classroom,Long> {

    final Map<String, String> fieldMap = Map.of(
            "name", "name",
            "description", "description"
    );

    static MapSqlParameterSource params(Classroom classroom, ObjectMapper mapper) {
        // The parameters are mapped to the named parameters in the SQL statement.
        Map<String, Object> params = new HashMap<>();
        params.put("id", classroom.getId());
        if (classroom.getName() != null) {
            params.put("name", classroom.getName());
        }
        //TODO: aggiungi tutti gli altri filtri possibili

        return new MapSqlParameterSource(params);
    }

    class ClassroomRowMapper implements RowMapper<Classroom> {
        final ObjectMapper objectMapper;

        public ClassroomRowMapper(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public Classroom mapRow(@Nonnull ResultSet rs, int rowNum) throws SQLException {
            var classroom = new Classroom();
            classroom.setId(rs.getLong("id"));
            classroom.setName(rs.getString("name"));
            classroom.setDescription(rs.getString("description"));
            classroom.setLink(rs.getString("link"));
            classroom.setMaxSeats(rs.getInt("max_seats"));
            classroom.setIsActive(rs.getBoolean("is_active"));
            classroom.setDate(rs.getDate("date").toLocalDate());
            classroom.setTime(rs.getTime("time").toLocalTime());
            classroom.setDuration(rs.getInt("duration"));

            // var endDate = rs.getTimestamp("date_end_time");
           // if (endDate != null) classroom.setDateEndTime(endDate.toLocalDateTime());
            return classroom;
        }
    }


    List<Classroom> findByUserAndDate(Long userId, LocalDateTime date);
    List<Classroom> findAvailableByDate(LocalDateTime date);
    List<Classroom> findByUserInDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate);


}


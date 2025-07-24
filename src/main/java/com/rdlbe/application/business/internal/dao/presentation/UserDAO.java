package com.rdlbe.application.business.internal.dao.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdlbe.application.business.internal.domains.User;
import com.rdlbe.foundations.core.Dao;
import jakarta.annotation.Nonnull;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public interface UserDAO extends Dao<User, Long> {

    // Campi usati per i filtri dinamici (se servono)
    final Map<String, String> fieldMap = Map.of(
            "username", "username",
            "email", "email"
    );

    static MapSqlParameterSource params(User user, ObjectMapper mapper) {
        Map<String, Object> params = new HashMap<>();
        params.put("iduser", user.getId());
        if (user.getUsername() != null) {
            params.put("username", user.getUsername());
        }
        if (user.getEmail() != null) {
            params.put("email", user.getEmail());
        }
        if (user.getBirthDate() != null) {
            params.put("birth_date", user.getBirthDate());
        }
        if (user.getRole() != null) {
            params.put("role", user.getRole().name());  // Enum -> String
        }
        return new MapSqlParameterSource(params);
    }

    class UserRowMapper implements RowMapper<User> {
        final ObjectMapper objectMapper;

        public UserRowMapper(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public User mapRow(@Nonnull ResultSet rs, int rowNum) throws SQLException {
            var user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            var birthDate = rs.getDate("birth_date");
            if (birthDate != null) {
                user.setBirthDate(birthDate.toLocalDate());
            }
            String roleString = rs.getString("role");
            if (roleString != null) {
                user.setRole(User.Role.valueOf(roleString));  // String -> Enum
            }
            return user;
        }
    }
}
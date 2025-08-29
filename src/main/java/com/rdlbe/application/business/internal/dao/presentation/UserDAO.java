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
import java.util.Optional;

@Repository
public interface UserDAO extends Dao<User, Long> {

    // Campi usati per i filtri dinamici (se servono)
    final Map<String, String> fieldMap = Map.of(
            "username", "username",
            "email", "email"
    );

    static MapSqlParameterSource params(User user, ObjectMapper mapper) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", user.getId());
        if (user.getUsername() != null) params.put("username", user.getUsername());
        if (user.getEmail() != null) params.put("email", user.getEmail());
        if (user.getBirthDate() != null) params.put("birth_date", user.getBirthDate());
        if (user.getRole() != null) params.put("role", user.getRole().name().toLowerCase());
        if (user.getState() != null) params.put("state", user.getState());
        if (user.getCredits() != null) params.put("credits", user.getCredits());
        if (user.getFirstName() != null) {
            params.put("first_name", user.getFirstName());
        }
        if (user.getLastName() != null) {
            params.put("last_name", user.getLastName());
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
            user.setPassword(rs.getString("password"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));


            user.setEmail(rs.getString("email"));
            var birthDate = rs.getDate("birth_date");
            if (birthDate != null) {
                user.setBirthDate(birthDate.toLocalDate());
            }
            String roleString = rs.getString("role");
            if (roleString != null) {
                user.setRole(User.Role.valueOf(roleString));  // String -> Enum
            }
            // Nuovi campi
            user.setState(rs.getBoolean("state"));
            int credits = rs.getInt("credits");
            user.setCredits(rs.wasNull() ? 0 : credits);
            return user;
        }



    }
    Optional<User> findByUsername(String username);
}
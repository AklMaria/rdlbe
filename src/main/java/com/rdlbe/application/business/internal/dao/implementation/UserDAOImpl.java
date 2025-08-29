package com.rdlbe.application.business.internal.dao.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdlbe.application.business.internal.dao.presentation.UserDAO;
import com.rdlbe.application.business.internal.domains.User;
import com.rdlbe.foundations.utils.DBUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
public class UserDAOImpl implements UserDAO {

    private final static String SELECT_USERS = "SELECT u.* FROM users u";
    private final static String FIND_BY_ID = "SELECT u.* FROM users u WHERE u.id = :id";
    private final static String INSERT_USER = """
    INSERT INTO users (username,first_name, last_name, email, birth_date, role, state, credits)
    VALUES (:username,:first_name, :last_name, :email, :birth_date, :role, :state, :credits)
    RETURNING id
""";

    private static final String FIND_BY_USERNAME = "SELECT * FROM users WHERE username = :username";
    private final static String UPDATE_USER = """
    UPDATE users
    SET username = :username,
        email = :email,
        birth_date = :birth_date,
        role = :role,
        state = :state,
        credits = :credits,
        first_name = :first_name,
        last_name = :last_name
    WHERE id = :id
""";
    private final static String DELETE_USER = "DELETE FROM users WHERE id = :id";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public UserDAOImpl(NamedParameterJdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Long create(User entity) {
        var params = new MapSqlParameterSource()
                .addValue("username", entity.getUsername())
                .addValue("first_name", entity.getFirstName())
                .addValue("last_name", entity.getLastName())
                .addValue("email", entity.getEmail())
                .addValue("birth_date", entity.getBirthDate())
                .addValue("role", entity.getRole() != null ? entity.getRole().name() : User.Role.USER.name())
                .addValue("state", entity.getState())
                .addValue("credits", entity.getCredits());

        return jdbcTemplate.queryForObject(INSERT_USER, params, Long.class);
    }

    @Override
    public void update(User entity) {
        var params = new MapSqlParameterSource()
                .addValue("id", entity.getId())
                .addValue("username", entity.getUsername())
                .addValue("first_name", entity.getFirstName())
                .addValue("last_name", entity.getLastName())
                .addValue("email", entity.getEmail())
                .addValue("birth_date", entity.getBirthDate())
                .addValue("role", entity.getRole() != null ? entity.getRole().name() : User.Role.USER.name())
                .addValue("state", entity.getState())
                .addValue("credits", entity.getCredits());

        jdbcTemplate.update(UPDATE_USER, params);
    }

    @Override
    public Optional<User> findById(Long id) {
        var params = new MapSqlParameterSource().addValue("id", id);
        var results = jdbcTemplate.query(FIND_BY_ID, params, new UserRowMapper(objectMapper));
        return results.stream().findFirst();
    }

    @Override
    public void delete(Long id, Long idUtenteAggiornamento) {
        var params = new MapSqlParameterSource().addValue("id", id);
        jdbcTemplate.update(DELETE_USER, params);
    }

    @Override
    public int count(Map<String, ?> filters) {
        // Non necessario per ora, se serve si può implementare
        return 0;
    }

    @Override
    public List<User> find(Map<String, ?> filters) {
        var sql = DBUtils.buildQuery(SELECT_USERS, null);
        return jdbcTemplate.query(sql, DBUtils.mapFilters(filters), new UserDAO.UserRowMapper(objectMapper));
    }



    @Override
    public Optional<User> findByUsername(String username) {
        var params = new MapSqlParameterSource().addValue("username", username);
        var results = jdbcTemplate.query(FIND_BY_USERNAME, params, new UserRowMapper(objectMapper));
        return results.stream().findFirst();
    }

}

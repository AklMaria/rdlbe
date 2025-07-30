package com.rdlbe.application.business.internal.dao.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdlbe.application.business.internal.dao.presentation.InscriptionDAO;
import com.rdlbe.application.business.internal.domains.User;
import com.rdlbe.application.business.internal.dao.presentation.UserDAO.UserRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class InscriptionDAOImpl implements InscriptionDAO {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    private static final String SELECT_USERS_BY_CLASSROOM = """
        SELECT u.* 
        FROM users u
        JOIN inscriptions i ON u.id = i.user_id
        WHERE i.classroom_id = :classroomId
    """;

    public InscriptionDAOImpl(NamedParameterJdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<User> findUsersByClassroom(Long classroomId) {
        var params = new MapSqlParameterSource().addValue("classroomId", classroomId);
        return jdbcTemplate.query(SELECT_USERS_BY_CLASSROOM, params, new UserRowMapper(objectMapper));
    }
}

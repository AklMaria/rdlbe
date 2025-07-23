package com.rdlbe.application.business.internal.dao.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdlbe.application.business.internal.dao.presentation.ClassroomDAO;
import com.rdlbe.application.business.internal.domains.Classroom;
import com.rdlbe.foundations.utils.DBUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
public class ClassroomDAOImpl implements ClassroomDAO {

    private final static String SELECT_CLASSROOMS = "SELECT c.* FROM classrooms";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public ClassroomDAOImpl(NamedParameterJdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Long create(Classroom entity) {
        return 0L;
    }

    @Override
    public void update(Classroom entity) {

    }

    @Override
    public int count(Map<String, ?> filters) {
        return 0;
    }

    @Override
    public Optional<Classroom> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id, Long idUtenteAggiornamento) {

    }

    @Override
    public List<Classroom> find(Map<String, ?> filters) {
        var sql = DBUtils.buildQuery(SELECT_CLASSROOMS, null);
        // Per le altre query per cui serve la WHERE condition scrivi questo
        // var sql = DBUtils.buildQuery(SELECT_CLASSROOMS + DBUtils.andConditions(filters, fieldMap), null);
        return jdbcTemplate.query(sql, DBUtils.mapFilters(filters), new ClassroomRowMapper(objectMapper));
    }
}

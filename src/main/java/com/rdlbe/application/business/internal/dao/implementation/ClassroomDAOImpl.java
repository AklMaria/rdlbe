package com.rdlbe.application.business.internal.dao.implementation;

import com.rdlbe.application.business.internal.dao.presentation.ClassroomDAO;
import com.rdlbe.application.business.internal.domains.Classroom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
public class ClassroomDAOImpl implements ClassroomDAO {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ClassroomDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Classroom> getClassroomById(Long classroomId) {
        return Optional.empty();
    }
}

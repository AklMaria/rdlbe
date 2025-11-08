package com.rdlbe.application.business.internal.dao.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdlbe.application.business.internal.domains.Affiliation;
import com.rdlbe.foundations.core.Dao;
import jakarta.annotation.Nonnull;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public interface AffiliationDAO extends Dao<Affiliation,Long> {

    class AffiliationRowMapper implements RowMapper<Affiliation> {
        final ObjectMapper objectMapper;

        public AffiliationRowMapper(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public Affiliation mapRow(@Nonnull ResultSet rs, int rowNum) throws SQLException {
            var classroom = new Affiliation();
            classroom.setId(rs.getLong("id"));
            classroom.setName(rs.getString("name"));
            classroom.setLink(rs.getString("link"));
            return classroom;
        }
    }
}

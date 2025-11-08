package com.rdlbe.application.business.internal.dao.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdlbe.application.business.internal.dao.presentation.AffiliationDAO;
import com.rdlbe.application.business.internal.domains.Affiliation;
import com.rdlbe.foundations.utils.DBUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AffiliationDAOImpl implements AffiliationDAO {

    private final static String SELECT_AFFILIATIONS = "SELECT a.* FROM affiliations a";
    private final static String INSERT_AFFILIATION = """
        INSERT INTO affiliations (name, link)
        VALUES (:name, :link)
        RETURNING id
    """;
    private final static String DELETE_AFFILIATIONS = "DELETE FROM affiliations WHERE id = :id";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public AffiliationDAOImpl(NamedParameterJdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Long create(Affiliation entity) {
        var params = new MapSqlParameterSource()
                .addValue("name", entity.getName())
                .addValue("link", entity.getLink());

        return jdbcTemplate.queryForObject(INSERT_AFFILIATION, params, Long.class);
    }

    @Override
    public void update(Affiliation entity) {

    }

    @Override
    public int count(Map<String, ?> filters) {
        return 0;
    }

    @Override
    public Optional<Affiliation> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id, Long idUtenteAggiornamento) {
        var params = new MapSqlParameterSource().addValue("id", id);
        jdbcTemplate.update(DELETE_AFFILIATIONS, params);
    }

    @Override
    public List<Affiliation> find(Map<String, ?> filters) {
        var sql = DBUtils.buildQuery(SELECT_AFFILIATIONS, null);
        return jdbcTemplate.query(sql, DBUtils.mapFilters(filters), new AffiliationDAO.AffiliationRowMapper(objectMapper));
    }

}

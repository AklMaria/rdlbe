package com.rdlbe.application.business.internal.dao.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdlbe.application.business.internal.dao.presentation.DocumentDAO;
import com.rdlbe.application.business.internal.domains.Document;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Slf4j
public class DocumentDAOImpl implements DocumentDAO {
	private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

	private static final String FIND_BY_USER_ID = "SELECT id, user_id, content FROM documents WHERE user_id = :userId";

    private static final String SAVE_DOCUMENT = "INSERT INTO documents (user_id, content) VALUES (:userId, :content)";

    private static final String DELETE_DOCUMENT = "DELETE FROM documents WHERE id = :id";


	public DocumentDAOImpl(NamedParameterJdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.objectMapper = objectMapper;
	}


    @Override
    public List<Document> findByUserId(Long userId) {
        var params = new MapSqlParameterSource().addValue("userId", userId);
        return jdbcTemplate.query(FIND_BY_USER_ID, params, new DocumentRowMapper(objectMapper));
    }

    @Override
    public void saveDoc(Long userId, String content) {
        var params = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("content", content);

        jdbcTemplate.update(SAVE_DOCUMENT, params);
        log.info("Document saved for user with id {}", userId);
    }

    @Override
    public boolean deleteDoc(Long id) {
        var params = new MapSqlParameterSource().addValue("id", id);
        int rowsAffected = jdbcTemplate.update(DELETE_DOCUMENT, params);

        if (rowsAffected > 0) {
            log.info("Document with id {} deleted successfully", id);
        } else {
            log.warn("Could not find document with id {} to delete", id);
        }

        return rowsAffected > 0;
    }
}

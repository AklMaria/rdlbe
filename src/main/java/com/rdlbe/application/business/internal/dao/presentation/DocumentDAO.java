package com.rdlbe.application.business.internal.dao.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdlbe.application.business.internal.domains.Document;
import com.rdlbe.application.business.internal.domains.User;
import jakarta.annotation.Nonnull;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface DocumentDAO {
	List<DocumentItem> findByUserId(Long userId);
	void saveDoc(Long userId, String content);
	boolean deleteDoc(Long id);

	class DocumentRowMapper implements RowMapper<Document> {
        final ObjectMapper objectMapper;

        public DocumentRowMapper(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public Document mapRow(@Nonnull ResultSet rs, int rowNum) throws SQLException {
            var document = new Document();
            document.setId(rs.getLong("id"));
            document.setContent(rs.getString("content"));

            User owner = new User();
            long ownerId = rs.getLong("user_id");
            owner.setId(ownerId);

            document.setUser(owner);

            return document;
        }
    }
}

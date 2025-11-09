package com.rdlbe.application.business.internal.dao.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdlbe.application.business.internal.domains.Classroom;
import com.rdlbe.application.business.internal.domains.Document;
// import com.rdlbe.application.views.DocumentItem; // ❌ non usato
import com.rdlbe.foundations.core.Dao;
import jakarta.annotation.Nonnull;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
// import org.springframework.web.multipart.MultipartFile; // ❌ non usato

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// import java.util.Optional; // ❌ non usato

@Repository
public interface ClassroomDAO extends Dao<Classroom, Long> {

    Map<String, String> fieldMap = Map.of(
            "name", "name",
            "description", "description"
    );

    // Mantengo la firma esistente; il parametro 'mapper' non è usato (solo warning).
    static MapSqlParameterSource params(Classroom classroom, ObjectMapper mapper) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", classroom.getId());
        if (classroom.getName() != null) {
            params.put("name", classroom.getName());
        }
        // TODO: aggiungi tutti gli altri filtri possibili
        return new MapSqlParameterSource(params);
    }

    /** RowMapper per Classroom */
    class ClassroomRowMapper implements RowMapper<Classroom> {
        final ObjectMapper objectMapper;

        public ClassroomRowMapper(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public Classroom mapRow(@Nonnull ResultSet rs, int rowNum) throws SQLException {
            var classroom = new Classroom();
            classroom.setId(rs.getLong("id"));
            classroom.setName(rs.getString("name"));
            classroom.setDescription(rs.getString("description"));
            classroom.setLink(rs.getString("link"));
            classroom.setMaxSeats(rs.getInt("max_seats"));
            classroom.setIsActive(rs.getBoolean("is_active"));
            classroom.setCompleted(rs.getBoolean("completed"));

            // Attenzione: getDate/getTime possono essere null a seconda della query
            var date = rs.getDate("date");
            if (date != null) classroom.setDate(date.toLocalDate());
            var time = rs.getTime("time");
            if (time != null) classroom.setTime(time.toLocalTime());

            classroom.setDuration(rs.getInt("duration"));
            return classroom;
        }
    }

    /** RowMapper per Document (documenti di aula) */
    class ClassroomDocumentRowMapper implements RowMapper<Document> {
        final ObjectMapper objectMapper;

        public ClassroomDocumentRowMapper(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public Document mapRow(@Nonnull ResultSet rs, int rowNum) throws SQLException {
            var document = new Document();
            document.setId(rs.getLong("id"));
            document.setFileName(rs.getString("file_name"));
            document.setContentType(rs.getString("content_type"));
            document.setData(rs.getBytes("content"));


            return document;
        }
    }

    List<Classroom> findClassroomsByUser(Long userId);

    List<Classroom> findByUserAndDate(Long userId, LocalDateTime date);
    List<Classroom> findAvailableByDate(LocalDateTime date);
    List<Classroom> findByUserInDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    List<Classroom> findCompletedClassrooms(Long userId);

    // Documenti di aula (admin)
    List<Document> findClassroomsDocs(Long classroomId);
    void uploadDoc(Long classroomId, byte[] fileData, String fileName, String contentType);
    void deleteDoc(Long classroomId, Long docId);
    Document findDocById(Long id);
    byte[] getFileBytesById(Long id);
}

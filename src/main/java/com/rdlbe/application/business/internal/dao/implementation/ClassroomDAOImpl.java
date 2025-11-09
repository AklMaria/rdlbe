package com.rdlbe.application.business.internal.dao.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdlbe.application.business.internal.dao.presentation.ClassroomDAO;
import com.rdlbe.application.business.internal.dao.presentation.DocumentDAO;
import com.rdlbe.application.business.internal.domains.Classroom;
import com.rdlbe.application.business.internal.domains.Document;
import com.rdlbe.application.views.DocumentItem;
import com.rdlbe.foundations.utils.DBUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
public class ClassroomDAOImpl implements ClassroomDAO {

    private final static String SELECT_CLASSROOMS = "SELECT c.* FROM classrooms c";
    private final static String INSERT_CLASSROOM = """
        INSERT INTO classrooms (name, description, link,max_seats, is_active, date, time,duration, completed)
        VALUES (:name, :description, :link, :maxSeats, :isActive, :date, :time, :duration, :completed)
        RETURNING id
    """;
    private final static String UPDATE_CLASSROOM = """
        UPDATE classrooms
        SET name = :name,
            description = :description,
            link = :link,
            max_seats = :maxSeats,
            is_active = :isActive,
            date = :date,
            time = :time,
            duration = :duration,
            completed = :completed
        WHERE id = :id
    """;
    private final static String DELETE_CLASSROOM = "DELETE FROM classrooms WHERE id = :id";
    private final static String FIND_BY_ID = "SELECT c.* FROM classrooms c WHERE id = :id";

    private final static String CLASSROOMS_BY_USER_AND_DATE = """
    SELECT c.* FROM classrooms c
    JOIN inscriptions i ON c.id = i.classroom_id
    WHERE i.user_id = :userId AND DATE(c.date_start_time) = DATE(:date)
""";

    private final static String AVAILABLE_CLASSROOMS_BY_DATE = """
    SELECT c.* FROM classrooms c
    WHERE DATE(c.date_start_time) = DATE(:date)
    AND (SELECT COUNT(*) FROM inscriptions i WHERE i.classroom_id = c.id) < c.max_seats
""";

    private final static String CLASSROOMS_BY_USER_IN_DATE_RANGE = """
    SELECT c.* FROM classrooms c
    JOIN inscriptions i ON c.id = i.classroom_id
    WHERE i.user_id = :userId
    AND c.date_start_time BETWEEN :startDate AND :endDate
""";

    private static final String SELECT_CLASSROOMS_BY_USER = """
    SELECT c.*
    FROM classrooms c
    JOIN inscriptions i ON c.id = i.classroom_id
    WHERE i.user_id = :userId
""";

    private static final String SELECT_CLASSROOMS_DOCS = """
    SELECT cd.*
    FROM classroom_documents cd
    -- JOIN inscriptions i ON c.id = i.classroom_id
    -- JOIN classrooms c ON c.id = cd.classroom_id
    -- WHERE i.user_id = :userId
    WHERE cd.classroom_id = :classroomId
""";

    private static final String SELECT_COMPLETED_CLASSROOMS = """
    SELECT c.*
    FROM classrooms c
    JOIN inscriptions i ON c.id = i.classroom_id
    JOIN classroom_documents d ON c.id = d.classroom_id
    WHERE i.user_id = :userId AND c.completed = true
""";

    private static final String UPLOAD_DOC = """
    INSERT INTO classroom_documents (classroom_id, file_name, content_type, content, created_at)
    VALUES (:classroomId, :fileName, :contentType, :content, :createdAt)
""";

    private static final String DELETE_DOC = """
    DELETE FROM classroom_documents
    -- WHERE classroom_id = :classroomId
    WHERE id = :docId
""";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public ClassroomDAOImpl(NamedParameterJdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Long create(Classroom entity) {
        var params = new MapSqlParameterSource()
                .addValue("name", entity.getName())
                .addValue("description", entity.getDescription())
                .addValue("link", entity.getLink())
                .addValue("maxSeats", entity.getMaxSeats())
                .addValue("isActive", entity.getIsActive())
                .addValue("date", entity.getDate())
                .addValue("time", entity.getTime())
                .addValue("completed", entity.getCompleted())
                .addValue("duration", entity.getDuration());
               // .addValue("dateEndTime", entity.getDateEndTime());

        return jdbcTemplate.queryForObject(INSERT_CLASSROOM, params, Long.class);
    }

    @Override
    public void update(Classroom entity) {
        var params = new MapSqlParameterSource()
                .addValue("id", entity.getId())
                .addValue("name", entity.getName())
                .addValue("description", entity.getDescription())
                .addValue("link", entity.getLink())
                .addValue("maxSeats", entity.getMaxSeats())
                .addValue("isActive", entity.getIsActive())
                .addValue("date", entity.getDate())
                .addValue("time", entity.getTime())
                .addValue("completed", entity.getCompleted())
                .addValue("duration", entity.getDuration());
               // .addValue("dateEndTime", entity.getDateEndTime());

        jdbcTemplate.update(UPDATE_CLASSROOM, params);
    }

    @Override
    public Optional<Classroom> findById(Long id) {
        var params = new MapSqlParameterSource().addValue("id", id);
        var results = jdbcTemplate.query(FIND_BY_ID, params, new ClassroomRowMapper(objectMapper));
        return results.stream().findFirst();
    }

    @Override
    public void delete(Long id, Long idUtenteAggiornamento) {
        var params = new MapSqlParameterSource().addValue("id", id);
        jdbcTemplate.update(DELETE_CLASSROOM, params);
    }

    @Override
    public int count(Map<String, ?> filters) {
        // opzionale, non usato ancora
        return 0;
    }

    @Override
    public List<Classroom> find(Map<String, ?> filters) {
        var sql = DBUtils.buildQuery(SELECT_CLASSROOMS, null);
        log.debug(sql);
        return jdbcTemplate.query(sql, DBUtils.mapFilters(filters), new ClassroomRowMapper(objectMapper));
    }

    //metodi per query personalizzate

    @Override
    public List<Classroom> findByUserAndDate(Long userId, LocalDateTime date) {
        var params = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("date", date);
        return jdbcTemplate.query(CLASSROOMS_BY_USER_AND_DATE, params, new ClassroomRowMapper(objectMapper));
    }

    @Override
    public List<Classroom> findAvailableByDate(LocalDateTime date) {
        var params = new MapSqlParameterSource()
                .addValue("date", date);
        return jdbcTemplate.query(AVAILABLE_CLASSROOMS_BY_DATE, params, new ClassroomRowMapper(objectMapper));
    }

    @Override
    public List<Classroom> findByUserInDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        var params = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("startDate", startDate)
                .addValue("endDate", endDate);
        return jdbcTemplate.query(CLASSROOMS_BY_USER_IN_DATE_RANGE, params, new ClassroomRowMapper(objectMapper));
    }

    @Override
    public List<Classroom> findClassroomsByUser(Long userId) {
        var params = new MapSqlParameterSource().addValue("userId", userId);
        return jdbcTemplate.query(SELECT_CLASSROOMS_BY_USER, params, new ClassroomDAO.ClassroomRowMapper(objectMapper));
    }

    @Override
    public List<Classroom> findCompletedClassrooms(Long userId) {
        var params = new MapSqlParameterSource().addValue("userId", userId);
        return jdbcTemplate.query(SELECT_COMPLETED_CLASSROOMS, params, new ClassroomDAO.ClassroomRowMapper(objectMapper));
    }

    @Override
    public List<Document> findClassroomsDocs(Long classroomId) {
        var params = new MapSqlParameterSource().addValue("classroomId", classroomId);
        return jdbcTemplate.query(SELECT_CLASSROOMS_DOCS, params, new DocumentDAO.DocumentRowMapper(objectMapper));
    }

    @Override
    public void uploadDoc(Long classroomId, MultipartFile file) {
        var params = new MapSqlParameterSource()
                .addValue("classroomId", classroomId)
                .addValue("fileName", file.getName())
                .addValue("contentType", file.getContentType())
                .addValue("content", file)
                .addValue("createdAt", LocalDateTime.now());
        jdbcTemplate.update(UPLOAD_DOC, params);
    }

    @Override
    public void deleteDoc(Long classroomId, Long docId) {
        var params = new MapSqlParameterSource()
                .addValue("classroomId", classroomId)
                .addValue("docId", docId);
        jdbcTemplate.update(DELETE_DOC, params);
    }

}

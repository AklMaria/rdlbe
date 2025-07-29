package com.rdlbe.application.business.internal.dao.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdlbe.application.business.internal.domains.Classroom;
import com.rdlbe.foundations.core.Dao;
import jakarta.annotation.Nonnull;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ClassroomDAO extends Dao<Classroom,Long> {

    final Map<String, String> fieldMap = Map.of(
            "name", "name",
            "description", "description"
    );

    static MapSqlParameterSource params(Classroom classroom, ObjectMapper mapper) {
        // The parameters are mapped to the named parameters in the SQL statement.
        Map<String, Object> params = new HashMap<>();
        params.put("id", classroom.getId());
        if (classroom.getName() != null) {
            params.put("name", classroom.getName());
        }
        //TODO: aggiungi tutti gli altri filtri possibili

        return new MapSqlParameterSource(params);
    }

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

            //TODO: nel caso in cui l'oggetto Classroom avesse altri oggetti da mappare aggiungi qui
            // per esempio:
            // Utente utente = new Utente();
            // utente.setId(rs.getLong("id_utente"));
            // utente.setNome(rs.getString("nome_utente"));
            // utente.setCognome(rs.getString("cognome_utente"));
            // classroom.setUtenteInserimento(utente);

            return classroom;
        }
    }


    List<Classroom> findByUserAndDate(Long userId, LocalDateTime date);
    List<Classroom> findAvailableByDate(LocalDateTime date);
    List<Classroom> findByUserInDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate);


}


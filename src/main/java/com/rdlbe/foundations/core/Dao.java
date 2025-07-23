package com.rdlbe.foundations.core;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Dao<T,K> {
    K create(T entity);
    void update(T entity);
    int count(Map<String, ?> filters);
    Optional<T> findById(K id);
    void delete(K id, Long idUtenteAggiornamento);
    List<T> find(Map<String, ?> filters);
}
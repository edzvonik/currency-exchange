package com.dzvonik.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {

    Optional<T> findById(Long id) throws SQLException;

    List<T> findAll() throws SQLException;

    T save(T entity) throws SQLException;

    void update(T entity) throws SQLException;

    void delete(Long id) throws SQLException;

}

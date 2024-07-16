package org.example.exohopital.repository;

import org.hibernate.Session;

import java.util.List;

public abstract class BaseRepository<T> {
    protected Session _session;
    public BaseRepository(Session session) {
        _session = session;
    }

    public void create(T entity) {
        _session.save(entity);
    }
    public void update(T entity) {
        _session.update(entity);
    }
    public void delete(T entity) {
        _session.delete(entity);
    }
    abstract T findById(int id);

    abstract List<T> findAll();
}

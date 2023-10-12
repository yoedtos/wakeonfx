package net.yoedtos.wakeonfx.repository;

import net.yoedtos.wakeonfx.exceptions.RepositoryException;

import java.util.List;

public interface Repository <T>{
    Object persist(T t) throws RepositoryException;
    void remove(int id) throws RepositoryException;
    T findById(int id) throws RepositoryException;
    Object merge(T t) throws RepositoryException;
    List<T> findAll() throws RepositoryException;
}

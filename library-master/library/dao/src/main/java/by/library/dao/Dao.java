package by.library.dao;

import java.io.Serializable;

import org.hibernate.LockMode;
import org.hibernate.Session;

import by.library.dao.exceptions.DAOException;

/**
 * The interface represents a standard set of functions
 * for Hibernate Session using parameterization
 * @param <T> - type of the inherited class
 */
public interface Dao<T> {
	void saveOrUpdate(T t) throws DAOException;

	T get(Serializable id, LockMode lm) throws DAOException;

	T get(Serializable id) throws DAOException;

	void delete(T t) throws DAOException;

	Session getSession();
}
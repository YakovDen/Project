package by.library.dao.impl;


import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import by.library.dao.Dao;
import by.library.dao.exceptions.DAOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * Class describes standard method used for persisting operations
 * witn DB in hibernate session.
 * @param <T> - type of persistent class
 */
@Repository
public class BaseDao<T> implements Dao<T> {
	
	private static Logger log = Logger.getLogger(BaseDao.class);
	
	@Autowired
	private SessionFactory sessionFactory;

    public BaseDao() {
    }

	public Session getSession(){
        return sessionFactory.getCurrentSession();
    }
	
	public void saveOrUpdate(T t) throws DAOException{
		try {
			getSession().saveOrUpdate(t);
			getSession().flush();
		} catch (HibernateException e) {
			log.error("Error SAVE OR UPDATE in Dao --- " + e);
			throw new DAOException(e);
		}

	}

	@SuppressWarnings("unchecked")
	public T get(Serializable id, LockMode lm) throws DAOException {
		T t = null;
		try {
			t = (T) getSession().get(getPersistentClass(), id);
			getSession().flush();
		} catch (HibernateException e) {
			log.error("Error GET with lock " + getPersistentClass() + " in Dao " + e);
			throw new DAOException(e);
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	public T get(Serializable id) throws DAOException {
		T t = null;
		try {
			t = (T) getSession().get(getPersistentClass(), id);
			getSession().flush();
		} catch (HibernateException e) {
			log.error("Error GET " + getPersistentClass() + " in Dao " + e);
			throw new DAOException(e);
		}
		return t;
	}

	public void delete(T t) throws DAOException {
		try {
			getSession().delete(t);
			getSession().flush();
		} catch (HibernateException e) {
			log.error("Error DELETE in Dao    " + e);
			throw new DAOException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private Class<T> getPersistentClass() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
}

package dao;

import java.io.Serializable;
import daoImpl.base.exception.DaoException;

public interface DAO <T>{
	public void delete(T t) throws DaoException;
	T load(Serializable id) throws DaoException;
	T get(Serializable id) throws DaoException;
	public void saveOrUpdate(T t) throws DaoException;
	public void save(T t) throws DaoException;
	public void saveId(T t) throws DaoException;	
}

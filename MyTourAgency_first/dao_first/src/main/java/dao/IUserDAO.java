package dao;

import java.util.List;

import daoImpl.base.exception.DaoException;
import agency.User;

public interface IUserDAO extends DAO<User>{
	public void InsertDiscount(int id, String discountBytour) throws DaoException;
	public User getUserById(int id) throws DaoException;
	public List<User> getUserDiscount()throws DaoException;	
	//public int authenticate(String username, String password) throws DaoException;
	public int authenticate(String username) throws DaoException;
	//все users для junit-test
	public List<User> getTestAllUsers() throws DaoException;
	// в колонку discountByTour запиь null после junit-теста
	public void updateTest(String discountBytour, int id) throws DaoException;
    
}



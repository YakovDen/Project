package by.library.dao;

import java.util.List;

import by.library.dao.exceptions.DAOException;
import by.library.entities.Order;

public interface OrderDao extends Dao<Order> {
	
	List<Order> getOrders(int user_id, int page) throws DAOException;
	List<Order> getReadings(int user_id, int page) throws DAOException;
	List<Order> getAllOrders(int page) throws DAOException;
	List<Order> dateOverOrders(int page) throws DAOException;

}

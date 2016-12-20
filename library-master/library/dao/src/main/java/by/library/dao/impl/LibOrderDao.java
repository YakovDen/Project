package by.library.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import by.library.dao.OrderDao;
import by.library.dao.UserDao;
import by.library.dao.exceptions.DAOException;
import by.library.entities.*;

/**
 * The class extends the standard DAO methods for orders. 
 * It adds methods for extracting a list of all ordered books, 
 * a list of books ordered by concrete user,
 * a list of books borrowed by a concrete user,
 *  list of books not returned in time. */
@Repository
public class LibOrderDao extends BaseDao<Order> implements OrderDao{

	Logger log = Logger.getLogger(LibOrderDao.class);
	
	@Autowired
	private UserDao libUserDao;
	
	int pageSize = 2;

	public LibOrderDao(){
	}
	
	/**
	 * This method helps to find the user's orders.
	 * Criteria query with conjunction and
	 * pagination is used.
	 * @param user_id - reader ID
	 * @param page - the resulting order list page
	 *               which the user wants to see
	 * @return a list of orders for concrete reader
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public List<Order> getOrders(int user_id, int page) throws DAOException {
		List<Order> orders = new ArrayList<Order>();
		
		try{
			Criteria criteria = getSession().createCriteria(Order.class);
			Conjunction objConjunction = Restrictions.conjunction();
			Criterion dayOn = Restrictions.isNull("dateOn");
			Criterion dayOff = Restrictions.isNull("dateOff");
			User user = libUserDao.get(user_id);
			Criterion id = Restrictions.eq("user", user);
			objConjunction.add(dayOn);
			objConjunction.add(dayOff);
			objConjunction.add(id);
			criteria.add(objConjunction);
			if(page > 0){
				criteria.setFirstResult((page - 1)*pageSize);
				criteria.setMaxResults(pageSize);
			}
			orders = criteria.list();
			Iterator<Order> it = orders.iterator();
			while(it.hasNext()){
				Order o = it.next();
				Inventory inv = o.getInventory();
				Book book = inv.getBook();
				book.getTitle();
			}
		} catch (HibernateException e) {
			log.error("Error extract List of orders for concrete user by Criteria in Dao --- " + e);
			throw new DAOException(e);
		}
		
		return orders;
	}
	
	/**
	 *  This method helps to find the user's book in use.
	 * Criteria query with conjunction and pagination is used.
	 * @param user_id - reader ID
	 * @param page - the resulting readings list page
	 *               which the user wants to see
	 * @return a list of books borrowed by a concrete user
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public List<Order> getReadings(int user_id, int page) throws DAOException {
		List<Order> readings = new ArrayList<Order>();
		
		try{
			Criteria criteria = getSession().createCriteria(Order.class);
			Conjunction objConjunction = Restrictions.conjunction();
			Criterion dayOn = Restrictions.isNotNull("dateOn");
			Criterion dayOff = Restrictions.isNotNull("dateOff");
			User user = libUserDao.get(user_id);
			Criterion id = Restrictions.eq("user", user);
			objConjunction.add(dayOn);
			objConjunction.add(dayOff);
			objConjunction.add(id);
			criteria.add(objConjunction);
			if(page > 0){
				criteria.setFirstResult((page - 1)*pageSize);
				criteria.setMaxResults(pageSize);
			}
			readings = criteria.list();
			Iterator<Order> it = readings.iterator();
			while(it.hasNext()){
				Order o = it.next();
				Inventory inv = o.getInventory();
				Book book = inv.getBook();
				book.getTitle();
			}
		} catch (HibernateException e) {
			log.error("Error extract List of readings for concrete user by Criteria in Dao --- " + e);
			throw new DAOException(e);
		}	
		return readings;
	}
	
	/**
	 * This method helps to find all orders in library.
	 * Criteria query with logical expression and pagination is used.
	 * @param page - the resulting orders list page
	 *               which the user wants to see
	 * @return a list of orders in library
	 * @throws DAOException
	 */
	public List<Order> getAllOrders(int page) throws DAOException{
		List<Order> orders = new ArrayList<Order>();
		try {
			Criteria criteria = getSession().createCriteria(Order.class);
			Criterion dayOn = Restrictions.isNull("dateOn");
			Criterion dayOff = Restrictions.isNull("dateOff");
			LogicalExpression andExp = Restrictions.and(dayOn, dayOff);
			criteria.add(andExp);
			if(page > 0){
				criteria.setFirstResult((page - 1)*pageSize);
				criteria.setMaxResults(pageSize);
			}
			orders = criteria.list();
			Iterator<Order> it = orders.iterator();
			while(it.hasNext()){
				Order o = it.next();
				Inventory inv = o.getInventory();
				Book book = inv.getBook();
				book.getTitle();
			}
		} catch (HibernateException e) {
			log.error("Error select all orders in Dao --- " + e);
			throw new DAOException(e);
		}
		return orders;
	}
	
	/**
	 * This method helps to find all books not returned in time.
	 * Criteria query with restrictions and pagination is used.
	 * @param page- the resulting list page
	 *               which the user wants to see
	 * @return a list of books not returned in time
	 * @throws DAOException
	 */
	@SuppressWarnings("unchecked")
	public List<Order> dateOverOrders(int page) throws DAOException{
		List<Order> orders = new ArrayList<Order>();

		try{
			Criteria criteria = getSession().createCriteria(Order.class);
			java.sql.Date dateToday = new java.sql.Date(new java.util.Date().getTime());
			criteria.add(Restrictions.lt("dateOff", dateToday));
			if(page > 0){
				criteria.setFirstResult((page - 1)*pageSize);
				criteria.setMaxResults(pageSize);
			}
			orders = criteria.list();
			Iterator<Order> it = orders.iterator();
			while(it.hasNext()){
				Order o = it.next();
				Inventory inv = o.getInventory();
				Book book = inv.getBook();
				book.getTitle();
			}
		} catch (HibernateException e) {
			log.error("Error select all orders in Dao --- " + e);
			throw new DAOException(e);
		}
		return orders;
	}		
}
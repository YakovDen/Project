package by.library.services;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;


import by.library.dao.*;
import by.library.dao.exceptions.DAOException;
import by.library.entities.*;
import by.library.services.exceptions.ServiceException;

/** service methods class for administrator:
 * add book, delete inventory (book),
 * retrieve all orders from library,
 * retrieve the book list which not returned in time,
 * give book to user, set user to blacklist,
 * retrieve user information by email
 * 
 *  */

@Service
@Transactional
public class AdminServices {
	final Logger log = Logger.getLogger(AdminServices.class);
	
	public AdminServices(){
	}

	@Autowired
	BookDao libBookDao;
	
	@Autowired
	OrderDao libOrderDao;
	
	@Autowired
	InventoryDao libInventoryDao;
	
	@Autowired
	UserDao libUserDao;

	/** add book to library
	 * @param a - Book entity
	 * @param num - how many inventories (entities) book will have
	 * @throws ServiceException
	 */
	public void addBook(Book a, int num) throws ServiceException {
		Book book = a;
		int number = num;
		try {
			if (book != null){
				libBookDao.saveOrUpdate(book);
			}
			Set<Inventory> inv = new HashSet<Inventory>();
			for(int i = 0; i < number; i++){
				Inventory in = new Inventory();
				in.setBook(book);
				in.setState(1);
				try {
					libInventoryDao.saveOrUpdate(in);
				} catch (DAOException e) {
					log.error(" \n ----- Error in DAO library while save "
							+ "inventory in Test ----- \n" + e);
				}
				inv.add(in);
			}
			book.setInventory(inv);
			libBookDao.saveOrUpdate(book);
		} catch (DAOException e) {
			log.error("Error in ADD book process --- " + e);
			throw new ServiceException(e);
		}
		
	}
	
	/** delete book's inventory (physical entity) from library by inventory_id
	 * @param inventory_id - book's inventory ID
	 * @throws ServiceException
	 */
	public void deleteBook(int inventory_id) throws ServiceException {
		try {
			int id = inventory_id;
			Inventory inv = null;
			if(id != 0){
				inv = libInventoryDao.get(id, LockMode.OPTIMISTIC);				
			}
			if (inv != null){
				if(inv.getOrder() != null){
					Order or = libOrderDao.get(inv.getOrder().getId(), LockMode.OPTIMISTIC);
					or.setInventory(null);
					User us = libUserDao.get(or.getUser().getUser_id());
					Set<Order> set = us.getBooks();
					set.remove(or);
					or.setUser(null);
					libOrderDao.delete(or);
				}
				libInventoryDao.delete(inv);
			}
		} catch (DAOException e) {
			log.error("Error in DELETE book process --- " + e);
			throw new ServiceException(e);
		}
	}
	
	
	/**
	 * Extract list of book which not returned in time
	 * @param currentPage - what result's page user is watching
	 * @return part of total resulting list appropriate current page
	 * @throws ServiceException
	 */
	public List<Order> dateOverOrders(int currentPage) throws ServiceException {
		List<Order> readings = new ArrayList<Order>();
		int page = currentPage;
		try {
			readings = libOrderDao.dateOverOrders(page);
		} catch (DAOException e) {
			log.error("Error in SELECT all orders  --- " + e);
			throw new ServiceException(e);
		}

		return readings;
	}
	
	/** look at ordered books
	 * @param currentPage - what result's page user is watching
	 * @return ordered book list
	 * @throws ServiceException
	 */
	public List<Order> allOrders(int currentPage) throws ServiceException {
		List<Order> orders = new ArrayList<Order>();
		int page = currentPage;
		try {
			orders = libOrderDao.getAllOrders(page);
		} catch (DAOException e) {
			log.error("Error in SELECT all orders  --- " + e);
			throw new ServiceException(e);
		}
		return orders;
	}
	
	/** get user's information by e-mail
	 * @param a - user e-mail
	 * @return user entity
	 * @throws ServiceException 
	 */
	public User getUserByEmail(String a)throws ServiceException {
		User user = null;
		String email = a;
		try {
			if (email != null){
				user = libUserDao.getUserByEmail(email);
			}
		} catch (DAOException e) {
			log.error("Error in extract user by email --- " + e);
			throw new ServiceException(e);
		}
		return user;
		
	}
	
	/** give out book to reader
	 * @param id - order ID
	 * @param num_days - how many days reader will hold book
	 * @throws ServiceException
	 */
	public void giveBook(int id, int num_days) throws ServiceException {
		int order_id = id;
		int days = num_days;
		java.util.Date dateToday = new java.util.Date();
		java.sql.Date dateOn = new java.sql.Date(dateToday.getTime());
		Calendar c = Calendar.getInstance();
		c.setTime(dateToday);
		c.add(Calendar.DAY_OF_YEAR, days);
		java.util.Date off = c.getTime();
		java.sql.Date dateOff = new java.sql.Date(off.getTime());
		try {
			Order order = libOrderDao.get(order_id, LockMode.OPTIMISTIC);
			order.setDateOn(dateOn);
			order.setDateOff(dateOff);
			libOrderDao.saveOrUpdate(order);
		} catch (DAOException e) {
			log.error("Error in GIVE book process --- " + e);
			throw new ServiceException(e);
		}
	}
	
	/** put user in blacklist
	 * @param readerId - reader ID
	 * @param inv_id - book ID
	 * @throws ServiceException
	 */
	public void setUserInBlackList(int readerId, int inv_id) throws ServiceException {
		int user_id = readerId;
		int inventory_id = inv_id;
		try {
			if(user_id > 0 && inventory_id > 0){
				User user = libUserDao.get(user_id);
				user.setBlack(1);
				libUserDao.saveOrUpdate(user);
				Inventory inv = libInventoryDao.get(inventory_id, LockMode.OPTIMISTIC);
				if (inv != null){
					Order or = libOrderDao.get(inv.getOrder().getId(), LockMode.OPTIMISTIC);
					or.setInventory(null);
					User us = libUserDao.get(or.getUser().getUser_id());
					Set<Order> set = us.getBooks();
					set.remove(or);
					or.setUser(null);
					libOrderDao.delete(or);
					libInventoryDao.delete(inv);
				}
			}
		} catch (DAOException e) {
			log.error("Error in set BLACK for user process --- " + e);
			throw new ServiceException(e);
		}
	}
}

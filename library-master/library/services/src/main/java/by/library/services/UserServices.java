package by.library.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;

import by.library.dao.exceptions.DAOException;
import by.library.dao.*;
import by.library.entities.*;
import by.library.services.exceptions.ServiceException;

/** service methods for users: 
 *  search book by title, order book, cancel order,
 *  get list of orders and list book in reading 
 **/

@Service
@Transactional
public class UserServices {
	final Logger log = Logger.getLogger(UserServices.class);
	
	public UserServices(){
	}
	
	@Autowired
	BookDao libBookDao;
	
	@Autowired
	OrderDao libOrderDao;
	
	@Autowired
	InventoryDao libInventoryDao;
	
	@Autowired
	UserDao libUserDao;
	
	/**
	 * search books by title or part of it with pagination
	 * @param s - search request
	 * @param currentPage - what result's page user is watching
	 * @return founded book list
	 * @throws ServiceException 
	 */
	public List<BookDTO> searchBookByTitle(String s, int currentPage) throws ServiceException {
		List<BookDTO> books = new ArrayList<BookDTO>();
		try{
			books = libBookDao.getBookByTitle(s, currentPage);
		} catch (DAOException e) {
			log.error("Error in search book process --- " + e);
			throw new ServiceException(e);
		}		
		return books;
	}
	
	/** Methods find list of books which ordered or is read by reader
	 * @param readerId - reader ID
	 * @param oper - request operation (inorder or inreadinng)
	 * @param currentPage - what result's page user is watching
	 * @return book list for concrete user according specified operation
	 * @throws ServiceException
	 */
	public List<Order> ordersByReaderId(int readerId, String oper, int currentPage) throws ServiceException {
		int id = readerId;
		int page = currentPage;
		String operation = oper;
		List <Order> orders = new ArrayList<Order>();
		User user = null;
		try {

			if (id > 0){
				user = libUserDao.get(id);
			}
			if(user != null){
				if(operation.equals("inorder")){
					orders = libOrderDao.getOrders(id, page);
				}
				if(operation.equals("inreading")){
					orders = libOrderDao.getReadings(id, page);
				}
			}
		} catch (DAOException e) {
			log.error("Error in extract orders list by user ID --- " + e);
			throw new ServiceException(e);
		}
		return orders;
	}
	
	/** method for order book 
	 * @param inventory_id - book's inventory ID
	 * @param user_id - reader ID
	 * @param status - reading status (reading room or circulation library)
	 * @throws ServiceException 
	 */
	public void orderBook(int inventory_id, int user_id, String status) throws ServiceException {
		try {
			Order order = new Order();
			User user = null;
			java.sql.Date dateOrder = new java.sql.Date(new java.util.Date().getTime());
			if(user_id > 0){
				user = libUserDao.get(user_id);
			}
			if(user != null && inventory_id > 0 && 
					(status.equals("читальный зал") || status.equals("абонемент"))){
				order.setUser(user);
				Inventory inv = libInventoryDao.get(inventory_id, LockMode.OPTIMISTIC);
				order.setInventory(inv);
				order.setStatus(status);
				order.setDateOrder(dateOrder);
				libOrderDao.saveOrUpdate(order);
				inv.setOrder(order);
				inv.setState(0);
				Set<Order> set = new HashSet<Order>();
				set = user.getBooks();
				set.add(order);
			}
		} catch (DAOException e) {
			log.error("Error in order book process --- " + e);
			throw new ServiceException(e);
		}
	}
	
	/** cancel order (emulate return book or close order by user)
	 * @param id - order ID
	 * @throws ServiceException 
	 */
	public void closeOrder(int id) throws ServiceException {
		try {
			if(id > 0){
				Order ord = libOrderDao.get(id, LockMode.OPTIMISTIC);
				User user = libUserDao.get(ord.getUser().getUser_id());
				Inventory inv = ord.getInventory();
				Set<Order> set = user.getBooks();
				set.remove(ord);
				libUserDao.saveOrUpdate(user);
				ord.setUser(null);
				inv.setOrder(null);
				inv.setState(1);
				libInventoryDao.saveOrUpdate(inv);
				ord.setInventory(null);
				libOrderDao.saveOrUpdate(ord);		
				libOrderDao.delete(ord);
			}
		} catch (DAOException e) {
			log.error("Error in delete order book  --- " + e);
			throw new ServiceException(e);
		}
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
}

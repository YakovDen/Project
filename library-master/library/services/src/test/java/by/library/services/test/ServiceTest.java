package by.library.services.test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import by.library.dao.*;
import by.library.dao.exceptions.DAOException;
import by.library.entities.*;
import by.library.services.AdminServices;
import by.library.services.UserServices;
import by.library.services.exceptions.ServiceException;

/**
 * This class tests service methods:
 * - addBook and giveBook in AdminServices,
 * - orderBook and closeOrder in UserServices.
 * It write-in the test data into the table,
 * test methods and clear DB table.
 * @param <T>
 */

@ContextConfiguration("/testContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ServiceTest<T> {
	
	@Autowired
	private AdminServices adminServices;
	
	@Autowired
	private UserServices userServices;
	
	@Autowired
	private BookDao libBookDao;
	
	@Autowired
	private OrderDao libOrderDao;
	
	@Autowired
	private InventoryDao libInventoryDao;
	
	@Autowired
	private UserDao libUserDao;
	
	@Autowired
	private Dao<T> baseDao;
	
	Logger log = Logger.getLogger(ServiceTest.class);
	
	
	private Set<Inventory> inv = new HashSet<Inventory>();
	private int user_id = 0;
	private String status = "читальный зал";
	int inventory_id = -10;
	Order order = null;
	User user1 = null;
			
	@Test
	public void test() {
		
		meth1();
		
		meth11();
		
		
		assertFalse(inv.isEmpty());
		assertEquals(inv.size(), 3);
		
		meth2();
		
		Iterator<Inventory> it = inv.iterator();
		Inventory in = null;
		if(it.hasNext()){
			in = it.next();
			try {
				in = libInventoryDao.get(in.getInventory_id());
			} catch (DAOException e) {
				fail("test fail in get test book inventory");
				log.error(" \n ----- Error in DAO while get "
						+ "testing inventory in ServiceTest ----- \n" + e);
			}			
		}
		inventory_id = in.getInventory_id();
		
		meth3();
		
		// checkup added order
		
		Inventory in2 = null;
		try {
			in2 = libInventoryDao.get(inventory_id);
			order = in2.getOrder();
			int order_id = order.getId();
			order = libOrderDao.get(order_id);
		} catch (DAOException e) {
			fail("test fail in get test book inventory and its order");
			log.error(" \n ----- Error in DAO while get "
					+ "testing inventory and its order in ServiceTest ----- \n" + e);
		}
		
		int testInvId = order.getInventory().getInventory_id();
		int testUserId = order.getUser().getUser_id();
		java.sql.Date testDateOrder = order.getDateOrder();
		java.sql.Date dateOrder = new java.sql.Date(new java.util.Date().getTime());
		assertEquals(testInvId, inventory_id);
		assertEquals(testUserId, user_id);
		assertEquals(testDateOrder.toString(), dateOrder.toString());
		assertNull(order.getDateOff());
		assertNull(order.getDateOn());
		
		meth4();
		
		// checkup update in order
		
		Calendar c = Calendar.getInstance();
		c.setTime(dateOrder);
		c.add(Calendar.DAY_OF_YEAR, 5);
		java.util.Date off = c.getTime();
		java.sql.Date dateOff = new java.sql.Date(off.getTime());
		
		assertNotNull(order.getDateOff());
		assertNotNull(order.getDateOn());
		assertEquals(order.getDateOff().toString(), dateOff.toString());
		assertEquals(order.getDateOn().toString(), dateOrder.toString());
		
		user1 = order.getUser();
		
		meth5();
		meth6();
		meth7();
	}
	
	/**
	 * write the test book with inventories into the database
	 */
	public void meth1(){	
		Book book = new Book();
		book.setIsbn("0000-0000-0000");
		book.setTitle("book1");
		book.setWriter("writer1");
		book.setYear("2001");
		int numberBook = 3;
		
		try {
			adminServices.addBook(book, numberBook);
		} catch (ServiceException e) {
			fail("test fail in addition test book into table");
			log.error("Error in ADD book in ServiceTest --- " + e);
		}
	}
		
	public void meth11(){
		Book book1 = new Book();
		try {
			book1 = libBookDao.get("0000-0000-0000");
		} catch (DAOException e) {
			fail("test fail in get test book from table");
			log.error("Error in GET book in ServiceTest --- " + e);
		}
		inv = book1.getInventory();
	}

	/**
	 * addition user entity
	 */
	public void meth2(){

		User user = new User();
		user.setName("name1");
		user.setBlack(0);
		user.setRole('u');
		user.setEmail("email1");
		Set<Order> set = new HashSet<Order>();
		user.setBooks(set);
		try {
			libUserDao.saveOrUpdate(user);
		} catch (DAOException e) {
			fail("test fail in addition user into table");
			log.error(" \n ----- Error in DAO library while save or update "
					+ "testing user in ServiceTest ----- \n" + e);
		}
		user_id = user.getUser_id();
	}
		
	/**
	 * create order (with UserServices method)
	 */
	public void meth3(){	

		try {
			userServices.orderBook(inventory_id, user_id, status);
		} catch (ServiceException e) {
			fail("test fail in addition order into table");
			log.error("Error in ADD order in ServiceTest --- " + e);
		}
	}
		
		
	/**
	 * give book for 5 days
	 */
	public void meth4(){
		try {
			adminServices.giveBook(order.getId(), 5);
		} catch (ServiceException e) {
			fail("test fail in giving book (update dates in order entity into table)");
			log.error("Error in UPDATE order in ServiceTest --- " + e);
		}
	}
		
	
	/**
	 * delete order (emulation return book)
	 */
	public void meth5(){
		try {
			baseDao.getSession().clear();
			userServices.closeOrder(order.getId());
		} catch (ServiceException e) {
			fail("test fail in close order (delete order)");
			log.error("Error in DELETE order in ServiceTest --- " + e);
		}
	}
	
	
	/**
	 * delete user
	 */
	public void meth6(){
		try {
			user1 = libUserDao.get(user_id);
			libUserDao.delete(user1);
		} catch (DAOException e) {
			fail("test fail in delete testing user entity");
			log.error(" \n ----- Error in delete "
					+ "testing user in ServiceTest ----- \n" + e);
		}
	}
	
	/**
	 * Removal of database from testing book and its inventories
	 */
	public void meth7(){
		try {
			String isbn = "0000-0000-0000";
			Book b = libBookDao.get(isbn);
			libBookDao.delete(b);
		} catch (DAOException e) {
			fail("test fail in clean test book from table");
			log.error(" \n ----- Error in delete book and inventories "
					+ " in ServiceTest ----- \n" + e);
		}
	}

}

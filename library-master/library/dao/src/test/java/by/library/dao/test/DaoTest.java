package by.library.dao.test;

import static org.junit.Assert.*;

import java.util.*;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import by.library.dao.BookDao;
import by.library.dao.InventoryDao;
import by.library.dao.OrderDao;
import by.library.dao.UserDao;
import by.library.dao.exceptions.DAOException;

import by.library.entities.*;

/**
 * Class tests BaseDao functions -
 *  get, saveOrUpdate, delete. 
 *  This test indicates the correct chain of actions 
 *  to avoid constrain-violation exception
 *  */

@ContextConfiguration("/testContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DaoTest {
	
	Logger log = Logger.getLogger(DaoTest.class);
	
	@Autowired
	private BookDao libBookDao;
	
	@Autowired
	private InventoryDao libInventoryDao;
	
	@Autowired
	private UserDao libUserDao;
	
	@Autowired
	private OrderDao libOrderDao;

	Set<Inventory> inv = new HashSet<Inventory>();
	User u1 = new User();
	Order or1 = null;
	Book b1 = new Book();
	
	@Test
	public void test() {
		meth1();
		meth2();
		meth3();	
	}
		
	/**
	 * add book and inventory for it
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void meth1(){
		b1.setIsbn("8989889");
		b1.setTitle("Great Title");
		b1.setWriter("Great Writer");
		b1.setYear("2012");
		try {
			libBookDao.saveOrUpdate(b1);
		} catch (DAOException e) {
			fail("test fail in saveOrUpdate");
			log.error(" \n ----- Error in DAO library while save or update "
					+ "book in Test ----- \n" + e);
		}
		
		int count = 4;
		for(int i = 0; i < count; i++){
			Inventory a = new Inventory();
			a.setBook(b1);
			a.setState(1);
			try {
				libInventoryDao.saveOrUpdate(a);
			} catch (DAOException e) {
				fail("test fail in saveOrUpdate");
				log.error(" \n ----- Error in DAO library while save "
						+ "inventory in Test ----- \n" + e);
			}
			inv.add(a);
		}
		b1.setInventory(inv);
		try {
			libBookDao.saveOrUpdate(b1);
		} catch (DAOException e) {
			fail("test fail in saveOrUpdate");
			log.error(" \n ----- Error in DAO library while update "
					+ "book and inventory Set in Test ----- \n" + e);
		}
	}
	
	
	/**
	 * add User and order
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void meth2(){
		
		u1.setBlack(0);
		u1.setRole('a');
		u1.setName("Vas");
		u1.setSecondName("Grib");
		u1.setPassword("111");
		u1.setEmail("email");
		try {
			libUserDao.saveOrUpdate(u1);
		} catch (DAOException e) {
			fail("test fail in saveOrUpdate");
			log.error(" \n ----- Error in DAO library while save "
					+ "User in Test ----- \n" + e);
		}
		
		int b = -1;
		Iterator<Inventory> iterator = inv.iterator();
		while(iterator.hasNext()){
			Inventory i = iterator.next();
			if(i.getState() == 1){
				b = i.getInventory_id();
				break;
			}
		}
		
		if(b > 0){
			try {
				or1 = new Order();
				Inventory inv2 = new Inventory();
				inv2 = libInventoryDao.get(b, LockMode.OPTIMISTIC);
				or1.setInventory(inv2);
				or1.setUser(u1);
				or1.setStatus("читальный зал");
				java.sql.Date dateOrder = new java.sql.Date(new java.util.Date().getTime());
				or1.setDateOrder(dateOrder);
				libOrderDao.saveOrUpdate(or1);
				Set<Order> orders = new HashSet<Order>();
				u1.setBooks(orders);
				(u1.getBooks()).add(or1);
			} catch (DAOException e) {
				fail("test fail in saveOrUpdate");
				log.error(" \n ----- Error in DAO library while save "
						+ "order in Test ----- \n" + e);
			}
		}
	}
	
	/**
	 * delete All
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void meth3(){
		
		try {
			u1.getBooks().remove(or1);
			or1.setUser(null);
			libOrderDao.delete(or1);
			libUserDao.delete(u1);
			libBookDao.delete(b1);
		} catch (DAOException e) {
			fail("test fail in saveOrUpdate");
			log.error(" \n ----- Error in DAO library while delete "
					+ "order and book in Test ----- \n" + e);
		}
		
	}

}

package by.library.dao.test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import by.library.dao.BookDao;
import by.library.dao.UserDao;
import by.library.dao.exceptions.DAOException;

import by.library.entities.Book;
import by.library.entities.BookDTO;
import by.library.entities.Inventory;
import by.library.entities.User;

/**
 * This class tests LibBookDao method 
 * which searches for books by title and LibUserDao method 
 * which get user's entity by e-mail.
 * It write-in the test data into the table,
 * test method (for LibBookDao both options
 *  - all results and considering pagination) and clear DB table.
 */
@ContextConfiguration("/testContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class Dao2Test {

	Logger log = Logger.getLogger(Dao2Test.class);
	
	@Autowired
	private BookDao libBookDao;
	
	@Autowired
	private UserDao libUserDao;
	
	List<BookDTO> testlist = null;
	List<BookDTO> testlist1 = null;
	
	public Dao2Test(){
	}
	
	/**
	 * write the test data into the database
	 */
	public void meth1(){

		for(int i = 1; i < 5; i++){
			Book book = new Book();
			book.setIsbn("0000-0000-000" + i);
			book.setTitle("book" + i);
			book.setWriter("writer" + i);
			book.setYear("200" + i);
			Set<Inventory> inv = new HashSet<Inventory>();
			Inventory in = new Inventory();
			in.setBook(book);
			in.setState(1);
			
			inv.add(in);
			book.setInventory(inv);
			try {
				libBookDao.saveOrUpdate(book);
			} catch (DAOException e) {
				log.error(" \n ----- Error in DAO library while save or update "
						+ "inventory in Test ----- \n" + e);
			}
		}
	}
	
	/**
	 * extract all results with the title containing the search query
	 */
	public void meth2(){
		
		try {
			testlist = libBookDao.getBookByTitle("book", 0);
		} catch (DAOException e) {
			fail("test fail in search book by title");
			log.error(" \n ----- Error in DAO library while get book list "
					+ " by title in Test ----- \n" + e);
		}
	}
	
	/**
	 * extract results only the first page
	 */
	public void meth3(){
		try {
			testlist1 = libBookDao.getBookByTitle("book", 1);
		} catch (DAOException e) {
			fail("test fail in search book by title");
			log.error(" \n ----- Error in DAO library while get book list "
					+ " by title in Test ----- \n" + e);
		}
	}
	
	/**
	 * clean DB from testing data
	 */
	public void meth4(){
		try {
			testlist = libBookDao.getBookByTitle("book", 0);
			for(BookDTO book: testlist){
				String isbn = book.getIsbn();
				Book b = libBookDao.get(isbn);
				libBookDao.delete(b);
			}
		} catch (DAOException e) {
			fail("test fail in clean test book from table");
			log.error(" \n ----- Error in DAO library while get book list "
					+ " by title in Test ----- \n" + e);
		}	
	}
	
	/**
	 * test LibBookDao method
	 */
	@Test
	public void test1() {
		
		meth1();
		meth2();
		assertFalse(testlist.isEmpty());
		assertEquals(testlist.size(), 4);
		meth3();
		assertFalse(testlist1.isEmpty());
		assertEquals(testlist1.size(), 2);
		meth4();
		
	}
	
	/*--------------------------------------------*/
	
	User user1 = null;
	
	/**
	 * test LibUserDao method
	 */
	@Test
	public void test2() {
		
		meth11();
		meth22();
		assertNotNull(user1);
		assertEquals(user1.getEmail(), "email1");
		assertEquals(user1.getName(), "name1");
		meth33();
	}
	
	/**
	 * write the test data into the database
	 */
	public void meth11(){
		User user = new User();
		user.setName("name1");
		user.setBlack(0);
		user.setRole('u');
		user.setEmail("email1");
		try {
			libUserDao.saveOrUpdate(user);
		} catch (DAOException e) {
			fail("test fail in clean test book from table");
			log.error(" \n ----- Error in DAO library while save or update "
					+ "testing user in Test ----- \n" + e);
		}
	}
	
	
	/**
	 * checkup of test data
	 */
	public void meth22(){
		try {
			user1 = libUserDao.getUserByEmail("email1");
		} catch (DAOException e) {
			fail("test fail in get testing user entity");
			log.error(" \n ----- Error in DAO library while get "
					+ "testing user in Test ----- \n" + e);
		}
	}
		
	
	/**
	 * clean DB from testing data
	 */
	public void meth33(){
		try {
			libUserDao.delete(user1);
		} catch (DAOException e) {
			fail("test fail in delete testing user entity");
			log.error(" \n ----- Error in DAO library while delete "
					+ "testing user in Test ----- \n" + e);
		}
	}
}

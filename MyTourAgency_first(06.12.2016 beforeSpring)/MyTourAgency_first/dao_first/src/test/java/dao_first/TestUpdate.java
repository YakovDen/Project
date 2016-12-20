package dao_first;

import java.util.ArrayList;
import java.util.List;


import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import agency.User;
import dao.IUserDAO;
import daoImpl.UserDAOImpl;
import daoImpl.base.exception.DaoException;


public class TestUpdate {
	final Logger log = Logger.getLogger(TestSave.class);
	
	private IUserDAO iud = (IUserDAO) (new UserDAOImpl());	
	int res1 = 0;// счетчик оплаченных туров без доваления тестового
	int res2 = 0;// счетчик оплаченных туров с довалением тестового
	int id = 7;// id тестового user в БД
	String discountBytour = "10,9999%";//тестовое значение скидки для user номер 7
	
	@Before
	public void create() {
		
		List<User> user1 = new ArrayList<User>();
		try {
			user1 = iud.getTestAllUsers();
		} catch (DaoException e) {
			log.error("TestUpdate(getTestAllUsers) ERROR\n" + e);
		}

		//проверяю ее наличие скидки в поле discountBytour и после устанавливаю скидку
		for (User element1 : user1) {
			if (element1.getDiscountBytour() == "10,9999%") {
				res1++;
			}

		}

		try {
			iud.updateTest(discountBytour, id);
		} catch (DaoException e) {
			log.error("TestUpdate(updateTest) ERROR\n" + e);
		}	
		
		//после установки скидки, проверяю ее наличие
		List<User> user2 = new ArrayList<User>();
		try {
			user2 = iud.getTestAllUsers();			
		} catch (DaoException e) {
			log.error("TestUpdate(getTestAllUsers) ERROR\n" + e);
		}
		
		for (User element2 : user2) {
			String var = String.valueOf(element2.getDiscountBytour());			
			
			if(var.equals(discountBytour)){
				res2++;
			}
			
		}
		
	}

	@After
	public void clean() {
					
		//подчищаю базу после добавленной записи
		discountBytour = null;
		try {
			iud.updateTest(discountBytour, id);
		} catch (DaoException e) {
			log.error("TestUpdate(updateTest) ERROR\n" + e);
		}		
		

	}

	@Test
	public void test() {		
		
		Assert.assertTrue(res1 < res2);//если запись  10,9999% в discountBytour добавлена, то тест прошел

	}

}

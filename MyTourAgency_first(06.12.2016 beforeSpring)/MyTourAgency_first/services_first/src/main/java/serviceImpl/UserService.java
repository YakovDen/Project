package serviceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import dao.IUserDAO;
import daoImpl.UserDAOImpl;
import daoImpl.base.ThreadLocalConnection;
import daoImpl.base.exception.DaoException;
import agency.User;
import service.IUserService;
import serviceImpl.UserService;

/**
 * service methods class for user:
 *  1) for choice a user from the database; 
 *  2) for a authentication of concrete user;
 *  3) for extract the user whith a discount(role touragent); 
 *  4) sets a discount for client(role touragent);
 *  5) sets a null in column discountByTour for user№7 (junit-test)
 */

public class UserService implements IUserService {
	final Logger log = Logger.getLogger(UserService.class);
	IUserDAO userDaoI = (IUserDAO)(new UserDAOImpl());
	
	User user;
	
	int authen = 0;	
	
	
	public User getUserById(int id) {
		try {
			Connection connection = ThreadLocalConnection.getConnection();
			connection.setAutoCommit(false);// transaction block start
			user = userDaoI.getUserById(id);
			connection.commit();// transaction block end
		} catch (SQLException | DaoException e) {
			// откатываем транзакцию, если что-то пошло не так;
			// операция будет отменена
			try {
				ThreadLocalConnection.getConnection().rollback();
			} catch (SQLException e1) {
				log.error(e1);
			}
			log.error(e);
		}
		return user;
	}
	
	public int authenticate(String username,
			String password) {
		try {
			Connection connection = ThreadLocalConnection.getConnection();
			connection.setAutoCommit(false);//transaction block start
		 authen = userDaoI.authenticate( username, password);
		 connection.commit();//transaction block end
		} catch (SQLException | DaoException e) {
			try {
				ThreadLocalConnection.getConnection().rollback();// откатываем транзакцию, если что-то пошло не так; операция будет отменена
			} catch (SQLException e1) {
				log.error(e1);
			}
			log.error(e);
		}
		
		 return authen;
		}
	
	public List<User> getUserDiscount() {
		List<User> userDiscount = null;
		try {
			Connection connection = ThreadLocalConnection.getConnection();
			connection.setAutoCommit(false);// transaction block start
			userDiscount = userDaoI.getUserDiscount();
			connection.commit();// transaction block end
		} catch (SQLException | DaoException e) {
			try {
				// откатываем транзакцию, если что-то пошло не так;
				// операция будет отменена
				ThreadLocalConnection.getConnection().rollback();
			} catch (SQLException e1) {
				log.error(e1);
			}
			log.error(e);
		}
		return userDiscount;
	}
	
	public void InsertDiscount( int id,
			String discountBytour) {		
		 try {
				Connection connection = ThreadLocalConnection.getConnection();
				connection.setAutoCommit(false);//transaction block start
				userDaoI.InsertDiscount(id, discountBytour);
				connection.commit();//transaction block end
			} catch (SQLException | DaoException e) {
				try {
					ThreadLocalConnection.getConnection().rollback();// откатываем транзакцию, если что-то пошло не так; операция будет отменена
				} catch (SQLException e1) {
					log.error(e1);
				}
				log.error(e);
			}
				
	}
	
		
	public List<User> getTestAllUsers(){
		List<User> testAllUsers = null;
		try {
			Connection connection = ThreadLocalConnection.getConnection();
			connection.setAutoCommit(false);//transaction block start
			testAllUsers = userDaoI.getTestAllUsers();
			 connection.commit();//transaction block end
		} catch (SQLException | DaoException e) {
			try {
				ThreadLocalConnection.getConnection().rollback();// откатываем транзакцию, если что-то пошло не так; операция будет отменена
			} catch (SQLException e1) {
				log.error(e1);
			}
			log.error(e);
		}
		
		 return testAllUsers;
	};

}

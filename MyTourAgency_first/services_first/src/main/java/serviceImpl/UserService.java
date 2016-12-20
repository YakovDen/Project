package serviceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

@Service
@Transactional
public class UserService implements IUserService {
	final Logger log = Logger.getLogger(UserService.class);
	//IUserDAO userDaoI = (IUserDAO)(new UserDAOImpl());
	
	@Autowired
	IUserDAO userDaoI;
	
	/*protected IUserDAO getDao() {
			return userDaoI;
	}*/
		
	
	int authen = 0;		
	
	public User getUserById(int id) {
		User user = null;
		try {			
			@SuppressWarnings("unused")
			Connection connection = ThreadLocalConnection.getConnection();
			//connection.setAutoCommit(false);// transaction block start
			user = userDaoI.getUserById(id);
			//connection.commit();// transaction block end
		} catch (DaoException e) {
			// откатываем транзакцию, если что-то пошло не так;
			// операция будет отменена
			try {
				ThreadLocalConnection.getConnection().rollback();
			} catch (SQLException e1) {
				log.error(e1);
			}
			log.error("Error getUserById" + e);
		}
		return user;
	}
	
	/*public int authenticate(String username,
			String password) {*/
		public int authenticate(String username) {
		try {
			@SuppressWarnings("unused")
			Connection connection = ThreadLocalConnection.getConnection();
			//connection.setAutoCommit(false);//transaction block start
		 //authen = userDaoI.authenticate( username, password);
		   authen = userDaoI.authenticate( username);
		 //connection.commit();//transaction block end
		} catch (DaoException e) {
			try {
				ThreadLocalConnection.getConnection().rollback();// откатываем транзакцию, если что-то пошло не так; операция будет отменена
			} catch (SQLException e1) {
				log.error(e1);
			}
			log.error("Error authenticate" + e);
		}
		
		 return authen;
		}
	
	public List<User> getUserDiscount() {
		List<User> userDiscount = null;
		try {
			@SuppressWarnings("unused")
			Connection connection = ThreadLocalConnection.getConnection();
			//connection.setAutoCommit(false);// transaction block start
			userDiscount = userDaoI.getUserDiscount();
			//connection.commit();// transaction block end
		} catch (DaoException e) {
			try {
				// откатываем транзакцию, если что-то пошло не так;
				// операция будет отменена
				ThreadLocalConnection.getConnection().rollback();
			} catch (SQLException e1) {
				log.error(e1);
			}
			log.error("Error getUserDiscount()"+ e);
		}
		return userDiscount;
	}
	
	public void InsertDiscount( int id, String discountBytour) {		
		 try {
			 	@SuppressWarnings("unused")
			 	Connection connection = ThreadLocalConnection.getConnection();
				//connection.setAutoCommit(false);//transaction block start
				userDaoI.InsertDiscount(id, discountBytour);
				//connection.commit();//transaction block end
			} catch (DaoException e) {
				try {
					ThreadLocalConnection.getConnection().rollback();// откатываем транзакцию, если что-то пошло не так; операция будет отменена
				} catch (SQLException e1) {
					log.error(e1);
				}
				log.error("Error InsertDiscount()"+ e);
			}
				
	}
	
		
	public List<User> getTestAllUsers(){
		List<User> testAllUsers = null;
		try {
			@SuppressWarnings("unused")
			Connection connection = ThreadLocalConnection.getConnection();
			//connection.setAutoCommit(false);//transaction block start
			testAllUsers = userDaoI.getTestAllUsers();
			// connection.commit();//transaction block end
		} catch (DaoException e) {
			try {
				ThreadLocalConnection.getConnection().rollback();// откатываем транзакцию, если что-то пошло не так; операция будет отменена
			} catch (SQLException e1) {
				log.error(e1);
			}
			log.error("Error getTestAllUsers()"+ e);
		}
		
		 return testAllUsers;
	};

}

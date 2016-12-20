package daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.IUserDAO;
import daoImpl.base.BaseDAO;
import daoImpl.base.exception.DaoException;
import daoImpl.util.HibernateUtil;
import agency.User;
/**
* The class extends the standard DAO methods for user. 
* It adds methods:
* 1) it a method for choice a user from the database; 
* 2) it a method for a authentication of concrete user;
* 3) it a method for extract the user whith a discount(role touragent);
* 4) it a method sets a discount for client(role touragent);
* 5) it a method sets a null in column discountByTour for user№7 (junit-test).
* */

public class UserDAOImpl extends BaseDAO<User> implements IUserDAO {	
	final Logger log = Logger.getLogger(UserDAOImpl.class);
	private Transaction transaction = null;
	
	private static UserDAOImpl thisClientDAOImpl;

	public static UserDAOImpl getClientDAOImpl() {
		if (thisClientDAOImpl == null)
			thisClientDAOImpl = new UserDAOImpl();
		return thisClientDAOImpl;
	}

	
	public User getUserById(int id) throws DaoException{
		// выбор залогиненного пользователя
		User user = new User();
		try {
			//Session session = HibernateUtil.getInstance().getSession();
			Session session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			String hql = "FROM User WHERE id_user = :id";
			Query query = session.createQuery(hql);
			query.setParameter("id",id);			
			
			user = (User)query.uniqueResult();						
			if (!transaction.wasCommitted()) {
				transaction.commit();
			}
		} catch (HibernateException e) {
			transaction.rollback();
			log.error("getUserById ERROR\n" + e);
			throw new DaoException(e);

		}
				
		return user;
	}

	
	public int authenticate( String username,
			String password) throws DaoException{
				
		Integer result = 0;
		User user = new User();
		try {
			//Session session = HibernateUtil.getInstance().getSession();
			Session session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			String hql = "FROM User WHERE username = :username and password = :password";
			Query query = session.createQuery(hql);
			query.setParameter("username",username);	
			query.setParameter("password",password);			
			user = (User) query.uniqueResult();	
			//проверка на соответствие user  в БД и введенного
			if(user!=null){
			result = user.getId_user();	}	
			else{result=0;}
			if (!transaction.wasCommitted()) {
				transaction.commit();
			}
		} catch (HibernateException e) {
			transaction.rollback();
			log.error("authenticate ERROR\n" + e);
			throw new DaoException(e);

		}
		
		return result;
	}

	//вывод usera со скидкой
	public List<User> getUserDiscount() throws DaoException {

		List<User> clients = new ArrayList<User>();
		try {
			//Session session = HibernateUtil.getInstance().getSession();
			Session session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			String hql = "FROM User";
			Query query = session.createQuery(hql);
			clients = query.list();
			for (Object result : clients) {
				log.info(result);				
			}
			if (!transaction.wasCommitted()) {
				transaction.commit();
			}
		} catch (HibernateException e) {
			transaction.rollback();
			log.error("getUserDiscount ERROR\n" + e);
			throw new DaoException(e);

		}
					
		return clients;
	}

	//устанока туроператором скидки пользователю
	public void InsertDiscount(int id, String discountBytour) throws DaoException {		
		//Session session = HibernateUtil.getInstance().getSession();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {			
			transaction = session.beginTransaction();
			User user = (User) session.get(User.class, id);
			user.setDiscountBytour(discountBytour);
			session.saveOrUpdate(user);			
			
         if (!transaction.wasCommitted()) {
				transaction.commit();
			}	
			
		} catch (HibernateException e) {
			transaction.rollback();
			log.error("getInsertDiscount ERROR\n" + e);
			throw new DaoException(e);

		}		
				
	}
	
	//формирование списка всех пользователей для junit-test
	public List<User> getTestAllUsers() throws DaoException {
		List<User> clients = new ArrayList<User>();
		
		try {
			//Session session = HibernateUtil.getInstance().getSession();
			Session session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			String hql = "FROM User";
			Query query = session.createQuery(hql);
			clients = query.list();
			for (Object result : clients) {
				log.info(result);			
				if (!transaction.wasCommitted()) {
					transaction.commit();
				}			
			}			
		} catch (HibernateException e) {
			transaction.rollback();
			log.error("getTestAllUsers ERROR\n" + e);
			throw new DaoException(e);

		}
		
		return clients;
		
	}	
	/**
	 * обнуление колонки discountByTour для тестового юзера №7 для junit-теста
	 */
	public void updateTest(String discountBytour, int id) throws DaoException{
		try {
			//Session session = HibernateUtil.getInstance().getSession();
			Session session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			String hql = "UPDATE User set discountBytour = :discount WHERE id_user = :id";
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			query.setParameter("discount", discountBytour);
			query.executeUpdate();
			
			session.flush();
			session.clear();
			if (!transaction.wasCommitted()) {
				transaction.commit();				
			}			
		} catch (HibernateException e) {
			transaction.rollback();
			log.error("TestSave ERROR\n" + e);
			throw new DaoException(e);
		}

	}
}

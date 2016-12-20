package by.library.dao.impl;


import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import by.library.dao.UserDao;
import by.library.dao.exceptions.DAOException;
import by.library.entities.User;


/**
 * The class extends the standard methods of dao
 * for user. There is added the method to retrieve the user object 
 * by its email.
 */
@Repository
public class LibUserDao extends BaseDao<User> implements UserDao{
	
	Logger log = Logger.getLogger(LibUserDao.class);;
	
	public LibUserDao(){
	}
	
	/**
	 * This method helps to find the person
	 *  with requested e-mail. 
	 *  HQL query is used.
	 * @param email - expected user e-mail 
	 * 		that we are looking for in DB
	 * @return user persist entity
	 * @throws DAOException
	 */
	public User getUserByEmail(String email) throws DAOException {
		User user = null;
		try{			
			String hql = "SELECT U FROM User U WHERE U.email = :template";
			Query query = getSession().createQuery(hql);
			query.setString("template", email);
			user = (User) query.uniqueResult();
		} catch (HibernateException e) {
			log.error("Error extract User by email, HQL in Dao --- " + e);
			throw new DAOException(e);
		}
		return user;
	}

}

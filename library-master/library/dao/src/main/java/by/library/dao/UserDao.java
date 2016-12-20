package by.library.dao;

import by.library.dao.exceptions.DAOException;
import by.library.entities.User;

public interface UserDao extends Dao<User> {
	
	User getUserByEmail(String email) throws DAOException;

}

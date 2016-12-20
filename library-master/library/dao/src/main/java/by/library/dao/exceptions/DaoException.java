package by.library.dao.exceptions;

import java.sql.SQLException;

import org.hibernate.HibernateException;

/** class describes wrapper for exceptions in DAO tier */

public class DAOException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private Exception exception;
	
	public DAOException(String message, SQLException e) {
		setException(e);
	}
	public DAOException(HibernateException e) {
		this.exception = e;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
}

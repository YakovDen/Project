package by.library.services.exceptions;

import by.library.dao.exceptions.DAOException;

public class ServiceException extends Exception {
		
		private static final long serialVersionUID = 1010L;
		
		private Exception exception;
		
		public ServiceException(DAOException e) {
			this.exception = e;
		}
		
		public Exception getException() {
			return exception;
		}
		
		public void setException(Exception exception) {
			this.exception = exception;
		}
}

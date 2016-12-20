package daoImpl.base.exception;

public class DaoException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private Exception exception;

    public DaoException(Exception exception) {
        this.exception = exception;
    }

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

}

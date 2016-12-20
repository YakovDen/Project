package daoImpl.base;
/**
 * класс для инициализации объектов(ссылок на объекты)
 */
import com.mysql.jdbc.Connection;

public class ThreadLocalConnection {
	private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>() {
		protected Connection initialValue() {
			return (Connection) new Connect().getConnection();
		}
	};

	public static Connection getConnection() {
		return connectionHolder.get();
	}
	
	public static void removeConnection() {
		connectionHolder.remove();
		
	}

}

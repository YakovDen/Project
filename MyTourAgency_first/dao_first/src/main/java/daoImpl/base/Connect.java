package daoImpl.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;




public class Connect {		
		
		public Connection getConnection() {		
			FileInputStream stream;
			Properties prop = new Properties();		
			try {
				stream = new FileInputStream("c:/Users/Den/workspace/MyTourAgency_first/dao_first/src/main/resources/dataBaseConnect.properties"); 			
				if(stream !=null){
				prop.load(stream);		}
			       
			   } catch (IOException e) {
			       e.printStackTrace();
			   }
			 String URL = prop.getProperty("dBName");
			 String LOGIN = prop.getProperty("login");
			 String PASSWORD = prop.getProperty("pass");
			//адрес порта сервера,имя базы и пароль к базе в файле properties 				
			Connection connection = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				// параметры драйвера				
				connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			  catch (SQLException e1) {
			}

			return connection;
		}

}

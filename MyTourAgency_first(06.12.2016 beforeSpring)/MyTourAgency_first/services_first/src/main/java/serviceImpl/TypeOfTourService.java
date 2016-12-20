package serviceImpl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import dao.ITypeOfTourDAO;
import daoImpl.TypeOfTourDAOImpl;
import daoImpl.base.ThreadLocalConnection;
import daoImpl.base.exception.DaoException;
import agency.TypeOfTour;
import service.ITypeOfTourService;

public class TypeOfTourService implements ITypeOfTourService {
	final Logger log = Logger.getLogger(TypeOfTourService.class);
	ITypeOfTourDAO totdi = (ITypeOfTourDAO)(new TypeOfTourDAOImpl());
	
/*	public List<TypeOfTour> getAllTypeOfTours() {
		List<TypeOfTour> tOft=null;
		 try {
				Connection connection = ThreadLocalConnection.getConnection();
				connection.setAutoCommit(false);//transaction block start
		 	 
					tOft = totdi.getAllTypeOfTours();
				
		 	   connection.commit();//transaction block end
			} catch (SQLException e) {
				try {
					ThreadLocalConnection.getConnection().rollback();// откатываем транзакцию, если что-то пошло не так; операция будет отменена
				} catch (SQLException e1) {
					log.error(e1);
				}
				log.error(e);
			}
		 	catch (DaoException e) {
		 		log.error("transaction getAllTypeOfTours ERROR \b" + e);

			}	
		 	return	tOft;				
	
	}*/

	@Override
	public TypeOfTour get(int id_typeOfTour) {
		TypeOfTour type = null;
		
		try {
			Connection connection = ThreadLocalConnection.getConnection();
			connection.setAutoCommit(false);//transaction block start
	 	 
				type = totdi.get(id_typeOfTour);
				
	 	   connection.commit();//transaction block end
		} catch (SQLException e) {
			try {
				ThreadLocalConnection.getConnection().rollback();// откатываем транзакцию, если что-то пошло не так; операция будет отменена
			} catch (SQLException e1) {
				log.error(e1);
			}
			log.error(e);
		}
	 	catch (DaoException e) {
	 		log.error("transaction getTypeOfTour ERROR \b" + e);

		}	
		return type;
	}

}

package serviceImpl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import agency.KindOfTour;
import dao.IKindOfTourDAO;
import daoImpl.KindOfTourDAOImpl;
import daoImpl.base.ThreadLocalConnection;
import daoImpl.base.exception.DaoException;
import service.IKindOfTourService;


public class KindOfTourService implements IKindOfTourService{
	final Logger log = Logger.getLogger(KindOfTourService.class);
	IKindOfTourDAO kotdi = (IKindOfTourDAO)(new KindOfTourDAOImpl());
	
	@Override
	public KindOfTour get(int id_kindOfTour) {
		KindOfTour kind = null;
		
		try {
			Connection connection = ThreadLocalConnection.getConnection();
			connection.setAutoCommit(false);//transaction block start
	 	 
				kind = kotdi.get(id_kindOfTour);
				
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
	 		log.error("transaction getKindOfTour ERROR \b" + e);

		}	
		return kind;
	}

	

}

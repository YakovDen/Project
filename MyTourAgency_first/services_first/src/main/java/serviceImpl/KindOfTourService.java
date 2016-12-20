package serviceImpl;

import java.sql.Connection;

import org.apache.log4j.Logger;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import agency.KindOfTour;
import dao.IKindOfTourDAO;
import daoImpl.KindOfTourDAOImpl;
import daoImpl.base.ThreadLocalConnection;
import daoImpl.base.exception.DaoException;
import service.IKindOfTourService;

@Service
@Transactional
public class KindOfTourService implements IKindOfTourService{
	final Logger log = Logger.getLogger(KindOfTourService.class);
	//IKindOfTourDAO kindOfTourDaoI = (IKindOfTourDAO)(new KindOfTourDAOImpl());
	
	@Autowired
	IKindOfTourDAO kindOfTourDaoI;
	
	@Override
	public KindOfTour get(int id_kindOfTour) throws ServiceException {		
		KindOfTour kind = null;
		try {
			@SuppressWarnings("unused")
			Connection connection = ThreadLocalConnection.getConnection();
			//connection.setAutoCommit(false);//transaction block start	 	 
			kind = kindOfTourDaoI.get(id_kindOfTour);				
	 	   //connection.commit();//transaction block end
		} catch (DaoException e) {
	 		log.error("transaction getKindOfTour ERROR \b" + e);

		}	
		return kind;
	}

	

}

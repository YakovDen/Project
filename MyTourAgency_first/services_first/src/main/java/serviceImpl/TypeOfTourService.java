package serviceImpl;

import java.sql.Connection;

import org.apache.log4j.Logger;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.ITypeOfTourDAO;
import daoImpl.TypeOfTourDAOImpl;
import daoImpl.base.ThreadLocalConnection;
import daoImpl.base.exception.DaoException;
import agency.TypeOfTour;
import service.ITypeOfTourService;

@Service
@Transactional
public class TypeOfTourService implements ITypeOfTourService {
	final Logger log = Logger.getLogger(TypeOfTourService.class);
	//ITypeOfTourDAO typeOfTourDaoI = (ITypeOfTourDAO)(new TypeOfTourDAOImpl());
	
	@Autowired
	ITypeOfTourDAO typeOfTourDaoI;

	@Override
	public TypeOfTour get(int id_typeOfTour) throws ServiceException{		
		TypeOfTour type = null;
		try {
			@SuppressWarnings("unused")
			Connection connection = ThreadLocalConnection.getConnection();
			//connection.setAutoCommit(false);//transaction block start	 	 
			type = typeOfTourDaoI.get(id_typeOfTour);				
	 	   //connection.commit();//transaction block end
		} catch (DaoException e) {
	 		log.error("transaction getTypeOfTour ERROR \b" + e);

		}	
		return type;
	}

}

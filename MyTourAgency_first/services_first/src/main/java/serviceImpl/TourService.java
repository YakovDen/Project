package serviceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import service.ITourService;
import dao.ITourDAO;
import daoImpl.TourDAOImpl;
import daoImpl.base.ThreadLocalConnection;
import daoImpl.base.exception.DaoException;
import agency.Tour;
import agency.UserTour;
/** service methods for tours: 
 *  There is added:
 * 1) to extract a list of tours(role touragent);
 * 2) add tour(role touragent);
 * 3) delete tour(role touragent);
 * 4) to extract a list of tours,pagination(role client);
 * 5) to reservation of tour(role client);
 * 6) to choice of current user(role client)
 * 7) to write  payd of tour in table usertour(role client);
 * 8) to pay/off pay of tour(role client);
 **/


@Service
@Transactional
public class TourService implements ITourService  {
	final Logger log = Logger.getLogger(TourService.class);
	//ITourDAO tourDaoI = (ITourDAO)(new TourDAOImpl());
	
	@Autowired
	ITourDAO tourDaoI;
	
	public List<Tour> getAllTours() throws ServiceException{
		List<Tour> tourAll=null;
		 try {
			 @SuppressWarnings("unused")
			Connection connection = ThreadLocalConnection.getConnection();
				//connection.setAutoCommit(false);//transaction block start		 	 
				tourAll = tourDaoI.getAllTours();				
		 	   //connection.commit();//transaction block end
			} catch (DaoException e) {
		 		log.error("transaction getAllTours ERROR \b" + e);
			}	
		 	return	tourAll;				
	
	}

	/*public List<Tour> addTour(String dateOfBeginning, String dateEnd, String name,
			int numberOfNights, int cost, String discount, TypeOfTour typeOfTour, KindOfTour kindOfTour)*/
	public List<Tour> addTour(Tour myTour) throws ServiceException{
		List<Tour> tourAdd = null;
		@SuppressWarnings("unused")
		Connection connection = ThreadLocalConnection.getConnection();
		//connection.setAutoCommit(false);//transaction block start
		//t2 = tdi.addTour(dateOfBeginning, dateEnd, name, numberOfNights, cost, discount, typeOfTour,kindOfTour);
		tourAdd = tourDaoI.addTour(myTour);
		//connection.commit();//transaction block end
		 return tourAdd;
	}

	public void deleteTour(int id) throws ServiceException {
		
		@SuppressWarnings("unused")
		Connection connection = ThreadLocalConnection.getConnection();
		//connection.setAutoCommit(false);//transaction block start			
		tourDaoI.deleteTour(id);
		//connection.commit();//transaction block end
		
	}
	
	public List<Tour> getAllKindTour(int page, int recordsPerPage) throws ServiceException {
		List<Tour> tourAllForUser = null;
		
		try {
			@SuppressWarnings("unused")
			Connection connection = ThreadLocalConnection.getConnection();
			//connection.setAutoCommit(false);//transaction block start			
			tourAllForUser = tourDaoI.getAllKindTour(page,recordsPerPage);
			//connection.commit();//transaction block end
		} catch (DaoException e) {
			try {
				ThreadLocalConnection.getConnection().rollback();// откатываем транзакцию, если что-то пошло не так; операция будет отменена
			} catch (SQLException e1) {
				log.error(e1);
			}
			log.error(e);
		}
		 return tourAllForUser;
	}
	
	public void getReservationTour(int id_tour, int id_user) throws ServiceException {
		
		try {
			@SuppressWarnings("unused")
			Connection connection = ThreadLocalConnection.getConnection();
			//connection.setAutoCommit(false);//transaction block start
			tourDaoI.getReservationTour(id_tour, id_user);
			//connection.commit();//transaction block end
		} catch (DaoException e) {
			try {
				ThreadLocalConnection.getConnection().rollback();// откатываем транзакцию, если что-то пошло не так; операция будет отменена
			} catch (SQLException e1) {
				log.error(e1);
			}
			log.error("Error to reservation of tour(role client)"+ e);
		}
		
	}

	 
	public List<Tour> getUserReservTour(int id_user) throws ServiceException {
		List<Tour> currentUser = null;
		try {
			@SuppressWarnings("unused")
			Connection connection = ThreadLocalConnection.getConnection();
			//connection.setAutoCommit(false);//transaction block start
			currentUser = tourDaoI.getUserReservTour(id_user);
			//connection.commit();//transaction block end
		} catch (DaoException e) {
			try {
				ThreadLocalConnection.getConnection().rollback();// откатываем транзакцию, если что-то пошло не так; операция будет отменена
			} catch (SQLException e1) {
				log.error(e1);
			}
			log.error("Error to choice of current user(role client)"+ e);
		}
		 return currentUser;
	}
	
	public Set<UserTour> getUserReservAllTours(int id_user) throws ServiceException {
		Set<UserTour> currentUserWithTour = null;
		try {
			@SuppressWarnings("unused")
			Connection connection = ThreadLocalConnection.getConnection();
			//connection.setAutoCommit(false);//transaction block start
			currentUserWithTour = tourDaoI.getUserReservAllTours(id_user);
			//connection.commit();//transaction block end
		} catch (DaoException e) {
			try {
				ThreadLocalConnection.getConnection().rollback();// откатываем транзакцию, если что-то пошло не так; операция будет отменена
			} catch (SQLException e1) {
				log.error(e1);
			}
			log.error("Error to reservation of tour(role client) --- "+ e);
		}
		 return currentUserWithTour;
	}
	
	public void payTour(int idPaid, boolean isPressed) throws ServiceException {		
		
		try {
			@SuppressWarnings("unused")
			Connection connection = ThreadLocalConnection.getConnection();
			//connection.setAutoCommit(false);//transaction block start
			tourDaoI.payTour(idPaid, isPressed);
			//connection.commit();//transaction block end
		} catch (DaoException e) {
			try {
				ThreadLocalConnection.getConnection().rollback();// откатываем транзакцию, если что-то пошло не так; операция будет отменена
			} catch (SQLException e1) {
				log.error(e1);
			}
			log.error("Error to pay/off pay of tour(role client)--- "+ e);
		}
		
	}
		
}

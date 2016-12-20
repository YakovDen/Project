package serviceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
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
 * 6) to pay/off pay of tour(role client);
 * 7) to write  payd of tour in table usertour(role client);
 * 8) to choice of current user(role client) 
 **/



public class TourService implements ITourService  {
	final Logger log = Logger.getLogger(TourService.class);
	ITourDAO tdi = (ITourDAO)(new TourDAOImpl());
	
	
	public List<Tour> getAllTours() {
		List<Tour> t1=null;
		 try {
				Connection connection = ThreadLocalConnection.getConnection();
				connection.setAutoCommit(false);//transaction block start
		 	 
					t1 = tdi.getAllTours();
				
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
		 		log.error("transaction getAllTours ERROR \b" + e);

			}	
		 	return	t1;				
	
	}

	/*public List<Tour> addTour(String dateOfBeginning, String dateEnd, String name,
			int numberOfNights, int cost, String discount, TypeOfTour typeOfTour, KindOfTour kindOfTour)*/
	public List<Tour> addTour(Tour myTour){
		List<Tour> t2 = null;
		try {
			Connection connection = ThreadLocalConnection.getConnection();
			connection.setAutoCommit(false);//transaction block start
			//t2 = tdi.addTour(dateOfBeginning, dateEnd, name, numberOfNights, cost, discount, typeOfTour,kindOfTour);
			t2 = tdi.addTour(myTour);
			connection.commit();//transaction block end
		} catch (SQLException e) {
			try {
				ThreadLocalConnection.getConnection().rollback();// откатываем транзакцию, если что-то пошло не так; операция будет отменена
			} catch (SQLException e1) {
				log.error(e1);
			}
			log.error(e);
		}
		 return t2;
	}

	public void deleteTour(int id) {
		
		try {
			Connection connection = ThreadLocalConnection.getConnection();
			connection.setAutoCommit(false);//transaction block start
			
			tdi.deleteTour(id);
			connection.commit();//transaction block end
		} catch (SQLException e) {
			try {
				ThreadLocalConnection.getConnection().rollback();// откатываем транзакцию, если что-то пошло не так; операция будет отменена
			} catch (SQLException e1) {
				log.error(e1);
			}
			log.error(e);
		}
		
	}
	
	public List<Tour> getAllKindTour(int page, int recordsPerPage) {
		List<Tour> t4 = null;
		
		try {
			Connection connection = ThreadLocalConnection.getConnection();
			connection.setAutoCommit(false);//transaction block start			
			t4 = tdi.getAllKindTour(page,recordsPerPage);
			connection.commit();//transaction block end
		} catch (SQLException | DaoException e) {
			try {
				ThreadLocalConnection.getConnection().rollback();// откатываем транзакцию, если что-то пошло не так; операция будет отменена
			} catch (SQLException e1) {
				log.error(e1);
			}
			log.error(e);
		}
		 return t4;
	}
	
	public void getReservationTour(int id_tour, int id_user) {
		
		try {
			Connection connection = ThreadLocalConnection.getConnection();
			connection.setAutoCommit(false);//transaction block start
			tdi.getReservationTour(id_tour, id_user);
			connection.commit();//transaction block end
		} catch (SQLException | DaoException e) {
			try {
				ThreadLocalConnection.getConnection().rollback();// откатываем транзакцию, если что-то пошло не так; операция будет отменена
			} catch (SQLException e1) {
				log.error(e1);
			}
			log.error(e);
		}
		
	}

	 
	public List<Tour> getUserReservTour(int id_user) {
		List<Tour> t5 = null;
		try {
			Connection connection = ThreadLocalConnection.getConnection();
			connection.setAutoCommit(false);//transaction block start
			t5 = tdi.getUserReservTour(id_user);
			connection.commit();//transaction block end
		} catch (SQLException | DaoException e) {
			try {
				ThreadLocalConnection.getConnection().rollback();// откатываем транзакцию, если что-то пошло не так; операция будет отменена
			} catch (SQLException e1) {
				log.error(e1);
			}
			log.error(e);
		}
		 return t5;
	}
	
	public Set<UserTour> getUserReservAllTours(int id_user) {
		Set<UserTour> t6 = null;
		try {
			Connection connection = ThreadLocalConnection.getConnection();
			connection.setAutoCommit(false);//transaction block start
			t6 = tdi.getUserReservAllTours(id_user);
			connection.commit();//transaction block end
		} catch (SQLException | DaoException e) {
			try {
				ThreadLocalConnection.getConnection().rollback();// откатываем транзакцию, если что-то пошло не так; операция будет отменена
			} catch (SQLException e1) {
				log.error(e1);
			}
			log.error(e);
		}
		 return t6;
	}
	
	public void payTour(int idPaid, boolean isPressed) {		
		
		try {
			Connection connection = ThreadLocalConnection.getConnection();
			connection.setAutoCommit(false);//transaction block start
			tdi.payTour(idPaid, isPressed);
			connection.commit();//transaction block end
		} catch (SQLException | DaoException e) {
			try {
				ThreadLocalConnection.getConnection().rollback();// откатываем транзакцию, если что-то пошло не так; операция будет отменена
			} catch (SQLException e1) {
				log.error(e1);
			}
			log.error(e);
		}
		
	}
		
}

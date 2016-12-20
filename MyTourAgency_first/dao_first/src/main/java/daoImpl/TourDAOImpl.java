package daoImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import dao.ITourDAO;
import daoImpl.base.BaseDAO;
import daoImpl.base.exception.DaoException;
import daoImpl.util.HibernateUtil;
import agency.*;
/**
 * The class extends the standard methods of dao
 * for info-object of book. There is added:
 * 1) the method to extract a list of tours(role touragent);
 * 2) the metod add tour(role touragent);
 * 3) the metod delete tour(role touragent);
 * 4) the method to extract a list of tours(role client);
 * 5) the metod to reservation of tour(role client);
 * 6) the metod to pay/off pay of tour(role client);
 * 7) the metod to write  payd of tour in table usertour(role client);
 * 8) the metod to choice of current user(role client) 
 */
@Repository
public class TourDAOImpl extends BaseDAO<Tour> implements ITourDAO {
	final Logger log = Logger.getLogger(TourDAOImpl.class);
	private Transaction transaction = null;

	// список всех туров в роли touragent
	@SuppressWarnings("unchecked")
	public List<Tour> getAllTours() throws DaoException {		
		List<Tour> tours = new ArrayList<Tour>();
		try {
			//Session session = HibernateUtil.getInstance().getSession();
			Session session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			String hql = "FROM Tour";
			Query query = session.createQuery(hql);
			tours = query.list();
			for (Object result : tours) {
				log.info(result);
				
			session.flush();
			session.clear();	
			if (!transaction.wasCommitted())
			transaction.commit();
			
			}
		} catch (HibernateException e) {
			log.error("getAllTours ERROR\n" + e);
			throw new DaoException(e);

		}

		return tours;
	}

	// добавление тура в роли touragent
	public List<Tour> addTour(Tour myTour) {		
		List<Tour> tour = new ArrayList<Tour>();
		try {
			//Session session = HibernateUtil.getInstance().getSession();
			Session session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			//Tour myTour = new Tour(dateOfBeginning, dateEnd, name, numberOfNights, cost, discount, typeOfTour, kindOfTour);			
			session.save(myTour);
			log.info("Read of Tour" + myTour);
			
			if (!transaction.wasCommitted())
			    transaction.commit();
			
			log.info("Save (commit):" + myTour);
		} catch (HibernateException e) {
			log.error("Error save Tour in Dao" + e);
			transaction.rollback();
			try {
				throw new DaoException(e);
			} catch (DaoException e1) {
				log.error("Error save Tour in Dao" + e1);
			}
		}

		return tour;
	}
	
	// удаление тура в роли touragent
	public void deleteTour(int id) {
		TourDAOImpl tdi = new TourDAOImpl();
		Tour tour = null;
		try {
			tour = tdi.get(id);
			tdi.delete(tour);
		} catch (DaoException e) {
			log.error("Error get or delete Tour in Dao" + e);
		}

	}

	// метод вывода списка туров для бронирования заказчиком в роли-заказчик
	@SuppressWarnings("unchecked")
	public List<Tour> getAllKindTour(int page, int recordsPerPage) throws DaoException {				
		
		List<Tour> toursCustomer = new ArrayList<Tour>();
		try {
			//Session session = HibernateUtil.getInstance().getSession();
			Session session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			//Criteria criteria = HibernateUtil.getInstance().getSession().createCriteria(Tour.class);
			Criteria criteria = HibernateUtil.getSessionFactory().openSession().createCriteria(Tour.class);
			int s = (page-1)*recordsPerPage;	
			criteria.setFirstResult(s);
			criteria.setMaxResults(recordsPerPage);
			criteria.addOrder(Order.asc("id_tour"));
			toursCustomer = criteria.list();
			
			session.flush();			
			if (!transaction.wasCommitted())
			    transaction.commit();
		} catch (HibernateException e) {
			log.error("Error extract List of tourCustomer for role user by Criteria in Dao "
					+ "getAllTours ERROR\n" + e);
			throw new DaoException(e);

		}

		return toursCustomer;
	}
		
	// в роли заказчик добавление записи об оплате в users и выбранного тура в
	// таблице сопряжения в usertour
	public void getReservationTour(int id_tour, int id_user) throws DaoException {
		UserTour usertour = new UserTour();

		try {
			//Session session = HibernateUtil.getInstance().getSession();
			Session session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();

			Tour tour = (Tour) session.get(Tour.class, id_tour);
			User user = (User) session.get(User.class, id_user);
			usertour.setTour(tour);
			usertour.setUser(user);
			tour.getUserTours().add(usertour);

			session.saveOrUpdate(usertour);

			String var = "Тур ' " + id_tour + " ' оплачен";
			Query query = session.createQuery("update User set tourReserved = :tourReserved where id_user = :id_user");
			query.setParameter("tourReserved", var);
			query.setParameter("id_user", id_user);
			int result = query.executeUpdate();
			
			session.flush();
			session.clear();
			if (!transaction.wasCommitted())
			    transaction.commit();
			
			log.info("Reserved (commit):" + result);

		} catch (HibernateException e) {
			log.error("Error reserved Tour in Dao" + e);
			transaction.rollback();

		}

	}
		
	// в роли заказчик добавление записи об оплате в таблицу сопряжения в
	// usertour	
	public void payTour(int idPaid, boolean isPressed) throws DaoException {

		try {
			//Session session = HibernateUtil.getInstance().getSession();
			Session session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();

			String hql = "UPDATE UserTour ut set isPaid =:isPaid WHERE ut.idUT = :id";
			Query query = session.createQuery(hql);
			query.setParameter("id", idPaid);
			query.setParameter("isPaid", isPressed);
			query.executeUpdate();

			session.flush();
			session.clear();

			if (!transaction.wasCommitted())
			    transaction.commit();
			log.info("Reserved (commit): Paid");

		} catch (HibernateException e) {
			log.error("Error reserved Tour in Dao" + e);
			transaction.rollback();
		}

	}

	// результирующая запись с выбранным туром и информацией об оплате для роли
	// заказчик
	public Set<UserTour> getUserReservAllTours(int id_user) throws DaoException {
		User user = new User();
		try {
			//Session session = HibernateUtil.getInstance().getSession();
			Session session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.clear();
			
			String hql = "FROM User WHERE id_user = :id";
			Query query = session.createQuery(hql);
			query.setParameter("id", id_user);
			user = (User) query.uniqueResult();

			if (!transaction.wasCommitted()) {
				transaction.commit();
			}
			session.flush();			
			
			log.info("UserReservTour (commit):" + user);
		} catch (HibernateException e) {
			log.error("Error user reserv Tour in Dao" + e);
			transaction.rollback();

		}

		return user.GetTours();

	}

	// выбор текущего клиента, оплачивающего тур для роли-заказчик
	@SuppressWarnings("unchecked")
	public List<Tour> getUserReservTour(int id_user) throws DaoException {
		User user = new User();
		List<Tour> tourForClient = new ArrayList<Tour>();
		try {
			//Session session = HibernateUtil.getInstance().getSession();
			Session session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();

			String hql = "FROM User WHERE id_user = :id";
			Query query = session.createQuery(hql);
			query.setParameter("id", id_user);
			tourForClient = query.list();
			user = (User) query.uniqueResult();
			
			session.flush();
			if (!transaction.wasCommitted()) {
				transaction.commit();
			}
			log.info("UserReservTour (commit):" + user);
		} catch (HibernateException e) {
			log.error("Error user reserv Tour in Dao" + e);
			transaction.rollback();

		}

		return tourForClient;

	}
	
}

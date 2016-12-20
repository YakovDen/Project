package dao;
import java.util.List;
import java.util.Set;

import daoImpl.base.exception.DaoException;
import agency.Tour;
import agency.UserTour;



public interface ITourDAO extends DAO<Tour>{
		//список всех туров, включая типы туров для роли туроператора
		public List<Tour> getAllTours () throws DaoException;
		//добавление тура для роли оператора
		/*public List<Tour> addTour(String dateOfBeginning, String dateEnd, 
				String name, int numberOfNights, int cost, String discount, TypeOfTour typeOfTour,KindOfTour kindOfTour);*/
		public List<Tour> addTour(Tour myTour);
		//удаление тура для роли оператора
		//public List<Tour> deleteTour(int id);
		public void deleteTour(int id);
		//для роли заказчик, метод вывода списка туров и их видов
		//public List<Tour>getAllKindTour() throws DaoException;
		public List<Tour>getAllKindTour(int page, int recordsPerPage) throws DaoException;
		//бронирование(выбор) тура заказчиком	
		public void getReservationTour(int id_tour, int id_user) throws DaoException;
		//вывод выбранного заказчиком тура с довавлением индекса в поле
		public List<Tour> getUserReservTour(int id_user) throws DaoException;
		//вывод всех туров,забронированных клиентом
		public Set<UserTour> getUserReservAllTours(int id_user) throws DaoException;		
		//добавление записи об оплате в таблице связи usertour
		public void payTour(int idPaid,boolean isPressed) throws DaoException;
		
}

package service;
import java.util.List;
import java.util.Set;

import agency.Tour;
import agency.UserTour;



public interface ITourService {

	//список всех туров, включая типы туров для роли туроператора
			public List<Tour> getAllTours();
			//добавление тура для роли оператора
			/*public List<Tour> addTour(String dateOfBeginning, String dateEnd, 
					String name, int numberOfNights, int cost, String discount, TypeOfTour typeOfTour, KindOfTour kindOfTour);*/
			public List<Tour> addTour(Tour myTour);
			//удаление тура для роли оператора
			public void deleteTour(int id);			
			//для роли заказчик, метод вывода списка туров и их видов
			//public List<Tour> getAllKindTour();
			public List<Tour> getAllKindTour(int page, int recordsPerPage);
			//бронирование(выбор) тура заказчиком	
			public void getReservationTour(int id_tour, int id_user);
			//вывод выбранного заказчиком тура с довавлением индекса в поле
			public List<Tour> getUserReservTour(int id_user);
			//вывод списка туров,забронированных заказчиком
			public Set<UserTour> getUserReservAllTours(int id_user);
			//оплата выбранного тура
			public void payTour(int idPaid,boolean isPressed);
			
		
}

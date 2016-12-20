package dao_first;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import agency.KindOfTour;
import agency.Tour;
import agency.TypeOfTour;
import dao.ITourDAO;
import daoImpl.TourDAOImpl;
import daoImpl.base.exception.DaoException;

public class TestDelete {
	final Logger log = Logger.getLogger(TestDelete.class);	
	private ITourDAO itd = (ITourDAO) (new TourDAOImpl());
	int res = 0;// счетчик добавленных записей в таблицу tours
	int id_tour;//номер тестового тура
	Tour tt;
	
	@Test
	public void test() {
		
		TypeOfTour typeOfTour = new TypeOfTour(2,"Обычный");				
		KindOfTour kindOfTour = new KindOfTour(3,"Шоппинг");
		//добавляю тестовый тур в базу
		Tour myTour = new Tour("2015-10-10", "2015-10-15", "Appolon", 5, 50, "(0-10)%", typeOfTour,kindOfTour);
		//itd.addTour("2015-10-10", "2015-10-15", "Appolon", 5, 50, "(0-10)%", typeOfTour,kindOfTour);		
		itd.addTour(myTour);		
		List<Tour> tour = new ArrayList<Tour>();
		try {
			tour = itd.getAllTours();
		} catch (DaoException e) {
			log.error("TestDeleteTours(getAllTours) ERROR\n" + e);
		}
		//тестовый объект тура, по которому идет сравнение
		tt = new Tour("2015-10-10", "2015-10-15", "Appolon", 5, 
				50, "(0-10)%", typeOfTour,kindOfTour);//объект, по которому идет сравнение

		for (Tour element : tour) {
			//сравниваю по 2-м полям
			if (tt.getName().equals(element.getName())
					&& (tt.getDateOfBeginning().equals(element
							.getDateOfBeginning()))) {
				id_tour = element.getId_tour();
				itd.deleteTour(id_tour);
				
			}

		}
		
		//список туров после удаления тестового тура
		try {
			tour = itd.getAllTours();
		} catch (DaoException e) {
			log.error("TestDeleteTours(getAllTours) ERROR\n" + e);
		}
		
		//проверяю, удалился ли тестовый тур
		for (Tour element : tour) {
			//сравниваю по 2-м полям
			if (tt.getName().equals(element.getName())
					&& (tt.getDateOfBeginning().equals(element
							.getDateOfBeginning()))) {								
				res++;
			}

		}
		
		
		Assert.assertTrue(res==0);//если запись после добавления удалена, то тест прошел
	}

}

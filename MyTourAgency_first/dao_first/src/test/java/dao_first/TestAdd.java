package dao_first;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import agency.KindOfTour;
import agency.Tour;
import agency.TypeOfTour;
import dao.ITourDAO;
import daoImpl.TourDAOImpl;
import daoImpl.base.exception.DaoException;


public class TestAdd {
	final Logger log = Logger.getLogger(TestAdd.class);
    
	private ITourDAO itd = (ITourDAO) (new TourDAOImpl());
	Tour tt;
	int res = 0;// счетчик добавленных записей в таблицу tours
	int id_tour;//номер тестового тура
		
	@Before
	public void create() {
		
		TypeOfTour typeOfTour = new TypeOfTour(2,"Обычный");				
		KindOfTour kindOfTour = new KindOfTour(3,"Шоппинг");
		//создаю тестовый тур в БД	
		Tour myTour = new Tour("2015-10-10", "2015-10-15", "Appolon", 5, 50, "(0-10)%", typeOfTour,kindOfTour);
		//itd.addTour("2015-10-10", "2015-10-15", "Appolon", 5, 50, "(0-10)%", typeOfTour,kindOfTour);
		itd.addTour(myTour);
		List<Tour> tour = new ArrayList<Tour>();
		try {
			tour = itd.getAllTours();
		} catch (DaoException e) {
			log.error("TestAddTours(getAllTours) ERROR\n" + e);
		}		
		//тестовый объект тура, по которому идет сравнение из БД
		tt = new Tour("2015-10-10", "2015-10-15", "Appolon", 5,
				50, "(0-10)%", typeOfTour,kindOfTour);//объект, по которому идет сравнение 
		
		for (Tour element : tour) {
			//сравниваю по 2-м полям
			if (tt.getName().equals(element.getName())
					&& (tt.getDateOfBeginning().equals(element
							.getDateOfBeginning()))) {				 
				id_tour = element.getId_tour();
				res++;
			}

		}

	}

	@After
	public void clean() {
				
		//подчищаю базу после добавленной записи						
		itd.deleteTour(id_tour);		
		System.out.println("Rows Affected: ");
		
		
	}

	@Test
	public void test() {
		Assert.assertTrue(res > 0);//если запись добавлена, то тест прошел

	}

}

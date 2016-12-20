package commands;

import java.io.IOException;
import java.util.List;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import service.ITourService;
import serviceImpl.TourService;
import agency.Tour;

/**
 * 
 * Резервирование номера тура в роли заказчика и отображение
 * общей таблицы туров с пагинацией
 *
 */

public class ReservationsCommand extends Command{	
	final Logger log = Logger.getLogger(ReservationsCommand.class);	
	
	private ITourService tourService = null;	
	private static ReservationsCommand inst;

	private ReservationsCommand() {
		this.tourService = (ITourService)new TourService();
	};

	public static synchronized ReservationsCommand getReservationsCommand() {
		if (inst == null) {
			inst = new ReservationsCommand();
		}
		return inst;
	}


	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		try {			
				
		int id_tour = Integer.parseInt(request.getParameter("id_tour"));				
		
		HttpSession session = request.getSession(true);		
		int id_user = (Integer) session.getAttribute("id_user");		
		//добавляю в БД в таблицу связи между заказчиком и туром		
		tourService.getReservationTour(id_tour, id_user);
		
		//обновление пагитнации таблицы туров в роли заказчика после резервирования тура
		int page =(Integer) session.getAttribute("page");		
		int recordsPerPage = (Integer) session.getAttribute("recordsPerPage");
		int noOfPages =(Integer) session.getAttribute("noOfPages");
		int currentPage =(Integer) session.getAttribute("currentPage");
		// в роли заказчика(сущность-бронирование)
		//список для бронирования тура			
		List<Tour> tcs = tourService.getAllKindTour(page,recordsPerPage);
		request.setAttribute("toursReserved", tcs);	
		request.setAttribute("noOfPages", noOfPages);
		request.setAttribute("currentPage", currentPage);	
				
		//вывожу текущего заказчика с выбраным для оплаты туром
		id_user = (Integer) session.getAttribute("id_user");
		List<Tour>tctour = tourService.getUserReservTour(id_user);
		request.setAttribute("tourForClient", tctour);
		//*******************************************************************			
	
		//проверка на соответствие id бронируемого тура с id турами в базе
		//счетчик бронируемых туров
		int tdTourReservation = 0;		
		for(Tour t:tcs){
			if(id_tour==t.getId_tour()){
				tdTourReservation++;				
			}
			
		}
				 
	  
		if(tdTourReservation!=0){
			RequestDispatcher dispatcher = request.getRequestDispatcher("customer.jsp");		
			dispatcher.forward(request, response);
		}
		else{			
			response.sendRedirect("errorReservationsTour.jsp");			
		}
		
		
		} catch (ServletException e) {
			log.error("Error", e);
		} catch (IOException e) {
			log.error("Error redirect", e);
		}		
		
		
	}

}

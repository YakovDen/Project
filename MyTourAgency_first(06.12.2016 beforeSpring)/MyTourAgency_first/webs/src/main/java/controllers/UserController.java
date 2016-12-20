package controllers;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.ITourService;
import serviceImpl.TourService;
import agency.Tour;
import agency.User;
import commands.Command;
import commands.LogoutCommand;
import commands.ReservationsCommand;
import commands.UserTourReservedCommand;


/** класс сервлета для управления кабинетом клиента
 * с использованием паттерна Command 
 * */

public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ITourService tourService = (ITourService) new TourService();       
	
    public UserController() {
        super();
        
    }

    /** параметр из запроса, который показывает, какое действие хочет 
     * выполнить пользователь */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// через сессию вывожу на страницу текущего пользователя
				User user = new User();
				HttpSession session = request.getSession();			
				user = (User) session.getAttribute("user");					
				
				// добавление атрибута к сессии
				request.getSession().setAttribute("calend", Calendar.getInstance());		
				/**
				 * параметр из запроса, который показывает, какое действие хочет
				 * выполнить пользователь
				 */

				String operation = request.getParameter("operation");
				Command com = null;	
				
				// входит в роли заказчика(сущность-бронирование)
				// список для роли заказчика для бронирования тура
				if (operation == null){					
				//пагинация таблицы туров	
					Integer page = 1;
					Integer recordsPerPage = 2;					
					if(request.getParameter("page") != null)
			        page = Integer.parseInt(request.getParameter("page"));	
					List<Tour> total = tourService.getAllTours();
					int noOfRecords = total.size();
					List<Tour> tcs = tourService.getAllKindTour(page,recordsPerPage);
					//расчет количества страниц для в отображаемой таблице
					Integer noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);;
					request.setAttribute("toursReserved", tcs);	
					request.setAttribute("noOfPages", noOfPages);
					request.setAttribute("currentPage", page);			
					
					
					// для вывода в ReservationsCommand
					session.setAttribute("id_user", user.getId_user());
					session.setAttribute("page", page);
					session.setAttribute("recordsPerPage", recordsPerPage);
					session.setAttribute("noOfPages", noOfPages);
					session.setAttribute("currentPage", page);	
					// вывожу текущего заказчика с оплаченным туром
					int id_user = (Integer) session.getAttribute("id_user");
					List<Tour> tctour = tourService.getUserReservTour(id_user);
					request.setAttribute("tourForClient", tctour);
										
					RequestDispatcher dispatcher = request
							.getRequestDispatcher("customer.jsp");
					dispatcher.forward(request, response);

				} else if ("reservationstour".equals(operation)) {
					com = ReservationsCommand.getReservationsCommand();

				} else if ("logout".equals(operation)) {
					com = LogoutCommand.getLogoutCommand();

				}
				else if ("tourcount".equals(operation)) {
					com = UserTourReservedCommand.getUserTourReservedCommand(0);					
				}
				
				else if ("paytour".equals(operation)) {					
					com = UserTourReservedCommand.getUserTourReservedCommand(1);					
				}
				if (com != null) {
					com.execute(request, response);
				}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}

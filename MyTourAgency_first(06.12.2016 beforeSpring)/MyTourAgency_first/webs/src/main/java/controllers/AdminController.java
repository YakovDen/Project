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

import org.apache.log4j.Logger;

import service.ITourService;
import service.IUserService;
import serviceImpl.TourService;
import serviceImpl.UserService;
import agency.Tour;
import agency.User;
import commands.AddTourCommand;
import commands.Command;
import commands.DeleteTourCommand;
import commands.DiscountTourCommand;
import commands.LogoutCommand;

/**
 *  класс сервлета для управления кабинетом администратора(турагента)
 *  с использованием паттерна Command 
 */
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	final Logger log = Logger.getLogger(AdminController.class);
	private ITourService tourService = (ITourService) new TourService();
	private IUserService userService = (IUserService) new UserService();
   
    public AdminController() {
        super();
        
    }

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
				if (operation == null) {
					// список всех туров для роли туроператор
					List<Tour> ts = tourService.getAllTours();				
					request.setAttribute("tours", ts);
					

					// список для роли туроператор, где он может устанавливать
					// скидку для клиентов
					List<User> us = userService.getUserDiscount();
					request.setAttribute("clients", us);
					RequestDispatcher dispatcher = request
							.getRequestDispatcher("touragent.jsp");
					dispatcher.forward(request, response);
					} else if ("addtour".equals(operation)) {
						com = AddTourCommand.getAddTourCommand();

					} else if ("discounttour".equals(operation)) {
						com = DiscountTourCommand.getDiscountTourCommand();

					} else if ("deletetour".equals(operation)) {
						com = DeleteTourCommand.getDeleteTourCommand();

					} else if ("logout".equals(operation)) {
						com = LogoutCommand.getLogoutCommand();

					}

					if (com != null) {
						com.execute(request, response);
					}
				
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}

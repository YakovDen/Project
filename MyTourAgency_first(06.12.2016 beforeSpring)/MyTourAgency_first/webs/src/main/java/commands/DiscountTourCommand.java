package commands;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import agency.Tour;
import agency.User;
import service.ITourService;
import service.IUserService;
import serviceImpl.TourService;
import serviceImpl.UserService;



public class DiscountTourCommand extends Command {
	final Logger log = Logger.getLogger(DiscountTourCommand.class);
	
	private IUserService userService = null;
	private ITourService tourService = null;
	private static DiscountTourCommand inst;

	private DiscountTourCommand() {
		this.userService = (IUserService) new UserService();
		this.tourService = (ITourService)new TourService();
	};

	public static synchronized DiscountTourCommand getDiscountTourCommand() {
		if (inst == null) {
			inst = new DiscountTourCommand();
		}
		return inst;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id_userDiscount = 0;//номер клиента, которому устанавливается скидка
			String id_userString = request.getParameter("id_user");
			String discountBytour = request.getParameter("discountBytour");
			int id = Integer.parseInt(id_userString);
			
			List<User> uss = userService.getTestAllUsers();
			for(User u: uss){
				if(u.getId_user() == id){
					id_userDiscount++;
				} 
			}

			userService.InsertDiscount(id, discountBytour);			
			
			List<Tour> ts = tourService.getAllTours();	
			request.setAttribute("tours", ts);						 
			//список для роли туроператор, где он может устанавливать скидку для клиентов		
			List <User>us = userService.getUserDiscount();
			request.setAttribute("clients", us);			
			//проверка на наличие клиентов совпадений клиентов в базе у ввденным для скидки id клиента			
			if(id_userDiscount!=0){
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("touragent.jsp");
				dispatcher.forward(request, response);				
			}
			else {
				try {
					response.sendRedirect("errorDiscount.jsp");
				} catch (IOException e) {
					log.error("Error redirect", e);
				}
			}

		} catch (ServletException e) {
			log.error("Error", e);
		} catch (IOException e) {
			log.error("Error redirect", e);
		}

	}

}

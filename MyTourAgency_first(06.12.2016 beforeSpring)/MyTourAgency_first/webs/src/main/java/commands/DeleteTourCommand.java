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



public class DeleteTourCommand extends Command {
	final Logger log = Logger.getLogger(DeleteTourCommand.class);
	
	private ITourService tourService = null;
	private IUserService userService = null;	
	private static DeleteTourCommand inst;
	
	private DeleteTourCommand(){
		this.tourService = (ITourService) new TourService();
		this.userService = (IUserService) new UserService();
	}

	public static synchronized DeleteTourCommand getDeleteTourCommand() {
		if (inst == null) {
			inst = new DeleteTourCommand();
		}
		return inst;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id = Integer.parseInt(request.getParameter("id_tour"));
			tourService.deleteTour(id);
			
			List<Tour> ts = tourService.getAllTours();	
			request.setAttribute("tours", ts);						 
			//список для роли туроператор, где он может устанавливать скидку для клиентов		
			List <User>us = userService.getUserDiscount();
			request.setAttribute("clients", us);
			

			RequestDispatcher dispatcher = request
					.getRequestDispatcher("touragent.jsp");
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			log.error("Error", e);
		} catch (IOException e) {
			log.error("Error redirect", e);
		}

	}

}

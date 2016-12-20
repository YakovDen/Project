package commands;

import java.io.IOException;

import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;

import service.ITourService;
import serviceImpl.TourService;
import agency.UserTour;

/**
 * Servlet implementation class UserTourReserved
 */
public class UserTourReservedCommand extends Command {
	final Logger log = Logger.getLogger( UserTourReservedCommand.class);
	
	private ITourService tourService = null;	
	//флаг оплаты(если не взвожу, то метод payTour() не задействован)
	private static int  flagPayTour;
	int id;	
	int idPaid;	
	private static UserTourReservedCommand inst;
	
    public UserTourReservedCommand(int flagPay) {
    	this.tourService = (ITourService)new TourService();
    	
    }

    public static synchronized UserTourReservedCommand getUserTourReservedCommand(int flagPay) {
		if (inst == null) {
			inst = new UserTourReservedCommand(flagPay);
		}flagPayTour=flagPay;
		return inst;
	}
    @Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			//вывожу таблицу с количеством туров, выбранными клиентом
			if (flagPayTour == 0) {
				
				id = Integer.parseInt(request.getParameter("id_user"));
				
				Set<UserTour> usertour = tourService.getUserReservAllTours(id);			
				request.setAttribute("allToursForClient", usertour);
			}
			//устанавливаю/сбрасываю флаг оплаты в БД и на usertour.jsp
			else if(flagPayTour == 1){//это, если нажимаю оплатить
				//id оплаченных туров user-ом для usertour.jsp
				idPaid = Integer.parseInt(request.getParameter("idUT"));							
				id = Integer.parseInt(request.getParameter("id_user"));
				
				boolean isPressed = Boolean.parseBoolean(request.getParameter("isPaid"));
				
				tourService.payTour(idPaid,isPressed);
				Set<UserTour> usertour = tourService.getUserReservAllTours(id);
				request.setAttribute("allToursForClient", usertour);
			}
			
		RequestDispatcher dispatcher = request.getRequestDispatcher("usertours.jsp");		
		dispatcher.forward(request, response);
		} catch (ServletException e) {
			log.error("Error", e);
		} catch (IOException e) {
			log.error("Error redirect", e);
		}
    }
}

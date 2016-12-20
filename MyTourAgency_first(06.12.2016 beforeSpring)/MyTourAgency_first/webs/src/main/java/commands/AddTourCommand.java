package commands;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import agency.KindOfTour;
import agency.Tour;
import agency.TypeOfTour;
import agency.User;
import service.IKindOfTourService;
import service.ITourService;
import service.ITypeOfTourService;
import service.IUserService;
import serviceImpl.KindOfTourService;
import serviceImpl.TourService;
import serviceImpl.TypeOfTourService;
import serviceImpl.UserService;



public class AddTourCommand extends Command {
	final Logger log = Logger.getLogger(AddTourCommand.class);
	
	private ITourService tourService = null;
	private IUserService userService = null;
	private ITypeOfTourService typeoftourService = null;
	private IKindOfTourService kindoftourService = null;
	
	private AddTourCommand(){
		 this.tourService = (ITourService) new TourService();
		 this.userService = (IUserService) new UserService();
		 this.typeoftourService = (ITypeOfTourService) new TypeOfTourService();
		 this.kindoftourService = (IKindOfTourService) new KindOfTourService();
	}
		
	private static AddTourCommand inst;

	public static synchronized AddTourCommand getAddTourCommand() {
		if (inst == null) {
			inst = new AddTourCommand();
		}
		return inst;
	}

	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			int resPrimary = 0;// счетчик туров первоначальных
			int resAfterAdding = 0;// счетчик туров после добавления

			List<Tour> tourPrimary = tourService.getAllTours();
			for (@SuppressWarnings("unused") Tour t : tourPrimary) {
				resPrimary++;
			}

			String dateOfBeginning = request.getParameter("dateOfBeginning");
			String dateEnd = request.getParameter("dateEnd");
			String name = request.getParameter("name");
			String numberOfNightsString = request
					.getParameter("numberOfNights");
			String costString = request.getParameter("cost");
			String discount = request.getParameter("discount");
			String id_typeOfTourString = request.getParameter("id_typeOfTour");	//в БД может быть NULL		
			String id_kindOfTourString = request.getParameter("id_kindOfTour");	//в БД может быть NULL		
			
			
			// проверка на заполненность полей таблицы туры
			if (dateOfBeginning.equalsIgnoreCase("")) {				
				response.sendRedirect("errorInput.jsp");
			} else if (dateEnd.equalsIgnoreCase("")) {				
				response.sendRedirect("errorInput.jsp");
			} else if (name.equalsIgnoreCase("")) {				
				response.sendRedirect("errorInput.jsp");
			} else if (numberOfNightsString.equalsIgnoreCase("")){		
				response.sendRedirect("errorInput.jsp");
			} else if (costString.equalsIgnoreCase("")) {							
				response.sendRedirect("errorInput.jsp");
			} else if (discount.equalsIgnoreCase("")) {
				response.sendRedirect("errorInput.jsp");
			} 

			else { // парсим String поля кол-ва ночей и цену в int
				int numberOfNights = 0;
				int cost = 0;			
				int id_typeOfTour = 0;
				int id_kindOfTour = 0;
				//если в поля для int ввели String, то взвожу флаг
				boolean flag = true;

				try {
					numberOfNights = Integer.parseInt(numberOfNightsString);
					cost = Integer.parseInt(costString);
					id_typeOfTour = Integer.parseInt(id_typeOfTourString);
					id_kindOfTour = Integer.parseInt(id_kindOfTourString);
				} catch (NumberFormatException e) {
					log.error("Одно из чисел введено неверно", e);
					flag = false;
				}

				if (!flag) {
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("errorInput.jsp");
					requestDispatcher.forward(request, response);
					return;
				}
								
				//добавляю тип тура в создаваемый  тур
				TypeOfTour typeOftour = null;				
				typeOftour = typeoftourService.get(id_typeOfTour);				
				
				
				//добавляю вид тура в создаваемый тур
				KindOfTour kindOftour = null;
				kindOftour = kindoftourService.get(id_kindOfTour);
				// добавления тура в БД
				Tour myTour = new Tour(dateOfBeginning, dateEnd, name, numberOfNights, cost, discount,
																					typeOftour, kindOftour);
				tourService.addTour(myTour);

				List<Tour> ts = tourService.getAllTours();
				for (@SuppressWarnings("unused") Tour t : ts) {
					resAfterAdding++;
				}
				request.setAttribute("tours", ts);

				// список для роли туроператор, где он может устанавливать
				// скидку для клиентов
				List<User> us = userService.getUserDiscount();
				request.setAttribute("clients", us);

				if (resAfterAdding > resPrimary) {
					try {
						RequestDispatcher dispatcher = request
								.getRequestDispatcher("touragent.jsp");
						dispatcher.forward(request, response);
					} catch (IOException e) {
						log.error("Error redirect", e);
					}
				} else {
					try {
						response.sendRedirect("errorAddTour.jsp");
					} catch (IOException e) {
						log.error("Error redirect", e);
					}
				}

			}
		} catch (IOException e) {
			log.error("Error redirect", e);
		} catch (ServletException e) {
			log.error("Error", e);
		}

	}

}

package webs.controllers;

import java.util.ArrayList;
import java.util.List;
import by.library.services.exceptions.ServiceException;
import webs.user.AuthUser;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import by.library.entities.Order;
import by.library.services.AdminServices;
import by.library.services.UserServices;


/**
 * Controller for extracting ordered/borrowed books 
 * and searching books in catalog by title
 */
@Controller
@RequestMapping(value = "/books")
public class BookLists {
	
	final Logger log = Logger.getLogger(BookLists.class);
	int pageSize = 2;
	
	@Autowired
    private UserServices uService;
	@Autowired
    private AdminServices aService;
	
	public BookLists(){	
	}
	
	/** 
	 * Method for retrieving all orders in library
	 * 
	 * @param model
	 * @param currentPage - what page user want to watch
	 * @return logical name of view
	 */
	@RequestMapping(value = "/orders/all/{currentPage}", method = RequestMethod.GET)
	public String allOrdered (ModelMap model, @PathVariable int currentPage){
		
		List<Order> orders = new ArrayList<Order>();
		String url = null;
		int totPages = -1;
		
		totPages = countTotPages(0, "allOrders");
		orders = resultForPage(currentPage, 0, "allOrders");
		
		model.addAttribute("orders", orders);
		model.addAttribute("page", currentPage);
		model.addAttribute("totPages", totPages);

		url = "/web/admin/allOrders";
			
		return url;
	}
	
	/** 
	 * Method for retrieving all orders in library
	 * 
	 * @param model
	 * @return logical name of view
	 */
	@RequestMapping(value = "/orders/all", method = RequestMethod.GET)
	public String allOrdered (ModelMap model){
		String url = null;
		List<Order> orders = new ArrayList<Order>();
		int totPages = -1;
	
		totPages = countTotPages(0, "allOrders");
		orders = resultForPage(1, 0, "allOrders");
		
		model.addAttribute("orders", orders);
		model.addAttribute("page", 1);
		model.addAttribute("totPages", totPages);
		
		url = "/web/admin/allOrders";
			
		return url;
	}
	
	
	/**
	 * Method for extracting all books which is being read by readers and not returned in time
	 * 
	 * @param model
	 * @param currentPage - what page user want to watch
	 * @return logical name of view
	 */
	@RequestMapping(value = "/orders/overDate/{currentPage}", method = RequestMethod.GET)
	public String notReturnedInTime (ModelMap model, @PathVariable int currentPage){
		
		List<Order> orders = new ArrayList<Order>();
		String url = null;
		int totPages = -1;
		
		totPages = countTotPages(0, "overDate");
		orders = resultForPage(currentPage, 0, "overDate");
		
		model.addAttribute("orders", orders);
		model.addAttribute("page", currentPage);
		model.addAttribute("totPages", totPages);

		url = "/web/admin/dateOverBook";
			
		return url;
	}
	
	/**
	 * Method for extracting all books which is being read by readers and not returned in time
	 * 
	 * @param model
	 * @return logical name of view
	 */
	@RequestMapping(value = "/orders/overDate", method = RequestMethod.GET)
	public String notReturnedInTime (ModelMap model){
		
		List<Order> orders = new ArrayList<Order>();
		String url = null;
		int totPages = -1;
		
		totPages = countTotPages(0, "overDate");
		orders = resultForPage(1, 0, "overDate");
		
		model.addAttribute("orders", orders);
		model.addAttribute("page", 1);
		model.addAttribute("totPages", totPages);

		url = "/web/admin/dateOverBook";
			
		return url;
	}

	/**
	 * Method for extracting books which ordered or read by reader. 
	 * Result for administrator view. 
	 * 
	 * @param model
	 * @param operation
	 * @param reader_id
	 * @return logical name of view
	 */
	@RequestMapping(value = "/orders_a/{operation}", method = RequestMethod.POST)
	public String ordered_reading_A (ModelMap model,
			@PathVariable String operation, 
			@RequestParam("reader_id") int reader_id){

		List<Order> orders = new ArrayList<Order>();
		String url = null;
		int totPages = -1;
		
		if(operation.equals("inorder")){
			totPages = countTotPages(reader_id, "inorder");
			orders = resultForPage(1, reader_id, "allOrders");
			url = "/web/admin/userOrders";
		}
		
		if(operation.equals("inreading")){
			totPages = countTotPages(reader_id, "inreading");
			orders = resultForPage(1, reader_id, "inreading");
			url = "/web/admin/userReadings";
		}
		
		model.addAttribute("orders", orders);
		model.addAttribute("page", 1);
		model.addAttribute("totPages", totPages);
		model.addAttribute("reader_id", reader_id);
		
		return url;
	}
	
	/**
	 * Method for extracting books which ordered or read by reader. 
	 * Result for administrator view. 
	 * 
	 * @param model
	 * @param currentPage - what page user want to watch
	 * @param operation
	 * @param reader_id
	 * @return logical name of view
	 */
	@RequestMapping(value = "/orders_a/{operation}/{currentPage}/{reader_id}", method = RequestMethod.GET)
	public String ordered_reading_A (ModelMap model, @PathVariable String operation, 
			@PathVariable int currentPage,
			@PathVariable int reader_id){

		List<Order> orders = new ArrayList<Order>();
		String url = null;
		int totPages = -1;
		
		if(operation.equals("inorder")){
			totPages = countTotPages(reader_id, "inorder");
			orders = resultForPage(currentPage, reader_id, "allOrders");
			url = "/web/admin/userOrders";
		}
		
		if(operation.equals("inreading")){
			totPages = countTotPages(reader_id, "inreading");
			orders = resultForPage(currentPage, reader_id, "inreading");
			url = "/web/admin/userReadings";
		}
		
		model.addAttribute("orders", orders);
		model.addAttribute("page", currentPage);
		model.addAttribute("totPages", totPages);
		model.addAttribute("reader_id", reader_id);
		
		return url;
	}
	
	/**
	 * Method for extracting books which ordered or read by reader. 
	 * Result for reader view. 
	 * 
	 * @param model
	 * @param currentPage
	 * @param operation
	 * @return logical name of view
	 */
	@RequestMapping(value = "/orders_u/{operation}/{currentPage}", method = RequestMethod.GET)
	public String ordered_reading_U (ModelMap model, @PathVariable int currentPage,
			@PathVariable String operation){
		
		AuthUser principal = (AuthUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		int reader_id = principal.getId();
		
		List<Order> orders = new ArrayList<Order>();
		String url = null;
		int totPages = -1;
		
		if(operation.equals("inorder")){
			totPages = countTotPages(reader_id, "inorder");
			orders = resultForPage(currentPage, reader_id, "inorder");
			url = "/web/user/orders";
		}
		
		if(operation.equals("inreading")){
			totPages = countTotPages(reader_id, "inreading");
			orders = resultForPage(currentPage, reader_id, "inreading");
			url = "/web/user/readings";
		}
		
		model.addAttribute("orders", orders);
		model.addAttribute("page", currentPage);
		model.addAttribute("totPages", totPages);

		return url;
	}
	
	
	/**
	 * Calculate total number of pages of resulting list
	 * 
	 * @param reader_id
	 * @param operation
	 * @return how many pages result has
	 */
	private int countTotPages (int reader_id, String operation){
		int totPages = -1;
		List<Order> orders = new ArrayList<Order>();
		
		//total number of readings/orders
		
		try{
			if(operation.equals("allOrders")){
				orders = aService.allOrders(0);
			}
			if(operation.equals("inorder")|| operation.equals("inreading")){
				orders = uService.ordersByReaderId(reader_id, operation, 0);
			}
			if(operation.equals("overDate")){
				orders = aService.dateOverOrders(0);
			}
		} catch (ServiceException e) {
			log.error(e);
		}
		
		//total number of pages
		
		if(orders.size()%pageSize == 0){
			totPages = orders.size()/pageSize;
		}
		else{
			totPages = ((orders.size()/pageSize) + 1);
		}
		
		return totPages;
	}
	
	/**
	 * Result for pagination
	 * 
	 * @param currentPage - what page user want to watch
	 * @param reader_id
	 * @param operation - identify criterion of selection
	 * @return list of orders
	 */
	private List<Order> resultForPage(int currentPage, int reader_id, String operation){
		
		List<Order> orders = new ArrayList<Order>();

		try {
			if(operation.equals("allOrders")){
				orders = aService.allOrders(currentPage);
			}
			if(operation.equals("inorder")|| operation.equals("inreading")){
				orders = uService.ordersByReaderId(reader_id, operation, currentPage);
			}
			if(operation.equals("overDate")){
				orders = aService.dateOverOrders(currentPage);
			}
		} catch (ServiceException e) {
			log.error(e);
		}
		
		return orders;
	}

}

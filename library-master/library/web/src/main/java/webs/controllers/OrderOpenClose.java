package webs.controllers;

import javax.servlet.http.HttpSession;
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

import by.library.services.UserServices;

/**
 * This controller controls book open/close order
 */

@Controller
@RequestMapping(value = "/order")
public class OrderOpenClose  {
	
	final Logger log = Logger.getLogger(OrderOpenClose.class);
	String url = null;
	
	public OrderOpenClose(){	
	}
	
	@Autowired
    private UserServices uService;
	
	/**
	 * Method for open order by reader
	 * 
	 * @param session
	 * @param model
	 * @param inventory_id
	 * @param readerId
	 * @param status
	 * @return logical name of view
	 */
	@RequestMapping(value = "/openOrder", method = RequestMethod.POST)
	public String openOrder(HttpSession session, ModelMap model, 
			@RequestParam("inventory_id") int inventory_id,
			@RequestParam("status") String status) {
		
		AuthUser principal = (AuthUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		int reader_id = principal.getId();
		
		url = null;
		try {
			uService.orderBook(inventory_id, reader_id, status);
			url = "/web/success";
		} catch (ServiceException e) {
			log.error(e);
			url = "/web/error";
		}
		return url;
	}
	
	/**
	 * Method for close order by reader
	 * 
	 * @param session
	 * @param model
	 * @param order_id
	 * @return logical name of view
	 */
	@RequestMapping(value = "/closeOrder/{order_id}", method = RequestMethod.GET)
	public String closeOrder(HttpSession session, 
			@PathVariable int order_id) {
		
		url = null;

		try {
			uService.closeOrder(order_id);
			url = "/web/success";
		} catch (ServiceException e) {
			log.error(e);
			url = "/web/error";
		}
		return url;
	}
}

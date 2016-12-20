package webs.controllers;

import javax.servlet.http.HttpSession;
import by.library.services.exceptions.ServiceException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import by.library.services.AdminServices;
import by.library.services.UserServices;

/**
 * Controller for giving and taking book by administrator.
 * It has method for blacklisting user who did not return book in time.
 *  */

@Controller
@RequestMapping(value = "/admin/give_take")
public class GiveTakeBook{
	
	String url = null;
	final Logger log = Logger.getLogger(GiveTakeBook.class);
	public GiveTakeBook(){
	}
	
	@Autowired
    private AdminServices aService;
	@Autowired
    private UserServices uService;
	
	/**
	 * Method for giving book to reader
	 * 
	 * @param session
	 * @param order_id
	 * @param days
	 * @return logical name of view
	 */
	@RequestMapping(value = "/give", method = RequestMethod.POST)
	public String giveBook(@RequestParam("order_id") int order_id,
			@RequestParam("days") int days){
		
		url = null;
		try {
			aService.giveBook(order_id, days);
			url = "/web/success";
		} catch (ServiceException e) {
			log.error(e);
			url = "/web/error";
		}
		return url;
	}
	
	/**
	 * Method for taking book when reader return it
	 * 
	 * @param session
	 * @param order_id
	 * @return logical name of view
	 */
	@RequestMapping(value = "/take/{order_id}", method = RequestMethod.GET)
	public String takeBook(HttpSession session,
			@PathVariable int order_id){
		
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
	
	
	/**
	 * With this method the administrator can assign a user to blacklist
	 * 
	 * @param session
	 * @param reader_id
	 * @param inv_id - ID for book inventory
	 * @return logical name of view
	 */
	@RequestMapping(value = "/blacklist/{reader_id}/{inv_id}", method = RequestMethod.GET)
	public String blackList(HttpSession session,
			@PathVariable int reader_id,
			@PathVariable int inv_id){
		
		url = null;
		try {
			aService.setUserInBlackList(reader_id, inv_id);
			url = "/web/success";
		} catch (ServiceException e) {
			log.error(e);
			url = "/web/error";
		}
		
		return url;
	}

}

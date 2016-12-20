package webs.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import by.library.services.exceptions.ServiceException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import by.library.entities.Book;
import by.library.services.AdminServices;

/** This controller controls the addition and removal of books  */

@Controller
@RequestMapping(value = "admin/add_delete")
public class AddDeleteBook {
	
	String url = null;
	final Logger log = Logger.getLogger(AddDeleteBook.class);
	
	public AddDeleteBook(){
	}
	
	@Autowired
    private AdminServices aService;


	/**
	 * Method for addition book to library with validation parameters from form
	 * 
	 * @param session
	 * @param model
	 * @param book - entity from form
	 * @param br - error check 
	 * @param number
	 * @return logical name of view
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addBook(HttpSession session, ModelMap model, @Valid Book book,
    		BindingResult br,
    		@RequestParam ("number") int number) {
		
		url = null;
				
        if(br.hasErrors()) {
        	url = "/web/admin/add_edit";
        }  
        else if(book != null){
        	try{
        		aService.addBook(book, number);
        		url = "/web/success";
			} catch (ServiceException e) {
				log.error(e);
				url = "/web/error";
			}
        }
		return url;
    }
	
	/**
	 * Method create book entity for form
	 * 
	 * @param model
	 * @return logical name of view
	 */
	@RequestMapping(method = RequestMethod.GET, params = "new")
	public String createBook(ModelMap model){
		model.addAttribute("book", new Book());
		return "/web/admin/add_edit"; 
	}
	
	/**
	 * Method redirect to form for book removal
	 *
	 * @return logical name of view
	 */
	@RequestMapping(method = RequestMethod.GET, params = "del")
	public String preDelete(){
		return "/web/admin/del_edit"; 
	}
			
	/**
	 * Method for removal book inventory
	 * 
	 * @param session
	 * @param inventory_id
	 * @return logical name of view
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(HttpSession session, @RequestParam int inventory_id) {
		
		url = null;

		try {
			aService.deleteBook(inventory_id);
			url = "/web/success";
		} catch (ServiceException e) {
			log.error(e);
			url = "/web/error";
		}
		return url;
    }

}

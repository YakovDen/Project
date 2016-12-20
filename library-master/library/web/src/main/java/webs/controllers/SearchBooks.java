package webs.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import by.library.entities.BookDTO;
import by.library.services.UserServices;
import by.library.services.exceptions.ServiceException;

@Controller
@RequestMapping(value = "/book/search")
public class SearchBooks {
	
	final Logger log = Logger.getLogger(SearchBooks.class);
	int pageSize = 2;
	
	@Autowired
    private UserServices uService;
	
	public SearchBooks(){	
	}
	
	/**
	 * Method for searching book by title or part of it
	 * 
	 * @param session
	 * @param model
	 * @param currentPage
	 * @return logical name of view
	 */
	@RequestMapping(value = "/{currentPage}", method = RequestMethod.GET)
	public String searchPages(HttpSession session, ModelMap model,
			@PathVariable int currentPage){

		List<BookDTO> books = new ArrayList<BookDTO>();
		String url = null;
		int totPages = -1;

		String searching = (String)session.getAttribute("searching");

		if (searching == null || searching.equals("") || searching.equals(" ")){
			url = "redirect:/index";
		}
		else{
			
			try {
				books = uService.searchBookByTitle(searching, 0);
			} catch (ServiceException e) {
				log.error(e);
			}
			
			if(books.size()%pageSize == 0){
				totPages = books.size()/pageSize;
			}
			else{
				totPages = ((books.size()/pageSize) + 1);
			}
			
			try {
				books = uService.searchBookByTitle(searching, currentPage);
			} catch (ServiceException e) {
				log.error(e);
			}
		
			model.addAttribute("searchedBooks", books);
			model.addAttribute("page", currentPage);
			model.addAttribute("totPages", totPages);
			session.setAttribute("searching", searching);
			
			url = "/web/book/search";
		}			
		return url;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String search(HttpSession session, ModelMap model,
			@RequestParam("searching") String searching){
	
		System.out.println("Current page in POST " + 1);
		List<BookDTO> books = new ArrayList<BookDTO>();
		String url = null;
		int totPages = -1;

		if (searching == null || searching.equals("") || searching.equals(" ")){
			url = "redirect:/index";
		}
		else{
			
			try {
				books = uService.searchBookByTitle(searching, 0);
			} catch (ServiceException e) {
				log.error(e);
			}
			
			if(books.size()%pageSize == 0){
				totPages = books.size()/pageSize;
			}
			else{
				totPages = ((books.size()/pageSize) + 1);
			}
			
			try {
				books = uService.searchBookByTitle(searching, 1);
			} catch (ServiceException e) {
				log.error(e);
			}
		
			model.addAttribute("searchedBooks", books);
			model.addAttribute("page", 1);
			model.addAttribute("totPages", totPages);
			session.setAttribute("searching", searching);
			
			url = "/web/book/search";
		}			
		return url;
	}

}

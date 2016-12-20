/**
 * 
 */
package controllers;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



/**
 * Controller for enter to index page and authorization
 *
 */
@Controller
public class Welcome {
	final Logger log = Logger.getLogger(Welcome.class);
	
	
	@RequestMapping(value="/welcome", method = {RequestMethod.POST, RequestMethod.GET})
	public String index(ModelMap model, HttpServletRequest request) {
			return "welcome";
	}
	
	/*@RequestMapping(value="/welcome", method = {RequestMethod.POST, RequestMethod.GET})
	public String adminPage(Model map, HttpServletRequest request) {
		return "welcome";
	}*/
		
	/*@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false)String error){
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error","Invalid username or password");				
		}
		model.setViewName("login");
		return model;
	}*/
	@RequestMapping(value="/login", method = {RequestMethod.POST, RequestMethod.GET})
	public String loginPage(ModelMap model) {
		return "login";
	}
		
	
	
	/*
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response,Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}
	*/
	/*@RequestMapping(value="/logout", method = {RequestMethod.POST, RequestMethod.GET})
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/";
	}*/
	
	/*@RequestMapping(value = "/error-input", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		return "error-input";
	}
	private String getPrincipal(){
		String userName;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}*/
		
}

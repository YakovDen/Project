package webs.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for enter to index page and authorization
 *
 */
@Controller
public class Welcome {
	
	final Logger log = Logger.getLogger(Welcome.class);
	
	@RequestMapping(value = "/")
	public String handleRequest(ModelMap model) {
		return "/web/index";
	}
	
	@RequestMapping(value="/logout", method = {RequestMethod.POST, RequestMethod.GET})
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="/index", method = {RequestMethod.POST, RequestMethod.GET})
	public String index(ModelMap model, HttpServletRequest request) {
			return "/web/index";
	}
	
	@RequestMapping(value="/login_error", method = {RequestMethod.POST, RequestMethod.GET})
	public String accessDeniedPage(ModelMap model, HttpServletRequest request) {
		model.addAttribute("error", "ACCESS DENIED. TRY AGAIN.<br> ДОСТУП ЗАКРЫТ. ПОПРОБУЙТЕ ЕЩЕ РАЗ.");
	    Exception exception = (Exception) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		if(exception != null){
			model.addAttribute("exception", exception.getMessage());
		}
		return "/web/login";
	}
	
	@RequestMapping(value="/login", method = {RequestMethod.POST, RequestMethod.GET})
	public String loginPage(ModelMap model) {
		return "/web/login";
	}

	
	@RequestMapping(value="/admin/index", method = {RequestMethod.POST, RequestMethod.GET})
	public String adminIndex(ModelMap model) {
		return "/web/admin/index";
	}
	
	@RequestMapping(value="/user/index", method = {RequestMethod.POST, RequestMethod.GET})
	public String userIndex(ModelMap model) {
		return "/web/user/index";
	}
	
}

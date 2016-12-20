package filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import agency.User;
import service.IUserService;


/**
 * Controller for enter to index page and authorization *
 */
@Controller
public class LoginLogic extends HttpServlet {
	private static final long serialVersionUID = 1L;  
	final Logger log = Logger.getLogger(LoginLogic.class);
	
	//private IUserService userService = (IUserService) new UserService();
	@Autowired
	IUserService userService;
    public LoginLogic() {
        super();      
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("login");
		//String password = request.getParameter("password");

		// проверка на нахождения в БД пользователя с данным логином и паролем
		//int id = userService.authenticate(username, password);
		int id = userService.authenticate(username);		
		if (id > 0) {

			User user = userService.getUserById(id);

			HttpSession session = request.getSession(true);
			session.setAttribute("user", user);

			// jsp для турагента
			if (user.getRole_id() == 1) {				
				response.sendRedirect("/webs/AdminController");
			} else {// jsp для клиента
				if (user.getRole_id() != 1) {
					response.sendRedirect("/webs/UserController");
				}
			}
		}

		else {
			response.sendRedirect("/webs/error.jsp");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}

package filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import agency.User;
import service.IUserService;
import serviceImpl.UserService;

/** класс, описывающий фильтр для доступа к полномочиям админа-турагента или пользователя-клиента*/

public class LoginLogic extends HttpServlet {
	private static final long serialVersionUID = 1L;      
	
	private IUserService userService = (IUserService) new UserService();
	
    public LoginLogic() {
        super();      
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("login");
		String password = request.getParameter("password");

		// проверка на нахождения в БД пользователя с данным логином и паролем
		int id = userService.authenticate(username, password);
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

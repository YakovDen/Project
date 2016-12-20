package commands;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class LogoutCommand extends Command {	
	final Logger log = Logger.getLogger(LogoutCommand.class);	

	private static LogoutCommand inst;

	private LogoutCommand() {
	};

	public static synchronized LogoutCommand getLogoutCommand() {
		if (inst == null) {
			inst = new LogoutCommand();
		}
		return inst;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			
		HttpSession session = request.getSession();
		//уничтожаю сессию
		if(session != null)
			session.invalidate();

		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");		
		dispatcher.forward(request, response);
		} catch (ServletException e) {
			log.error("Error", e);
		} catch (IOException e) {
			log.error("Error redirect", e);
		}
		
	}

}

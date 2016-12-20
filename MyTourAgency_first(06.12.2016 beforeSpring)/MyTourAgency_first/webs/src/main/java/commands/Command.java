package commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Command {
	abstract public void execute(HttpServletRequest request,HttpServletResponse response);


}

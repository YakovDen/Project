package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

/** filter for access to user account */

public class SetCharFilter implements Filter {
	private FilterConfig filterConfig = null;

	public SetCharFilter() {

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.filterConfig = config;
	}

	final Logger log = Logger.getLogger(SetCharFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain next)
			throws IOException, ServletException {
		// читаем кодировку
		String encoding = request.getCharacterEncoding();
		System.out.println(encoding);
		// если не установлена UTF-8, без учета регистра, то устанавливаем UTF-8
		if (!"UTF-8".equalsIgnoreCase(encoding))
			response.setCharacterEncoding("UTF-8");
		next.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}

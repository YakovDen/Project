package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Servlet Filter implementation class SetCharFilter
 */
public class SetCharFilter implements Filter {
	private FilterConfig filterConfig = null;

    /**
     * Default constructor. 
     */
    public SetCharFilter() {
        
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain next) throws IOException, ServletException {
		//читаем кодировку 
		String encoding = request.getCharacterEncoding();
		System.out.println(encoding);
		//если не установлена UTF-8, без учета регистра, то устанавливаем UTF-8
		if(!"UTF-8".equalsIgnoreCase(encoding))
			response.setCharacterEncoding("UTF-8");
		next.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig config) throws ServletException {
		this.filterConfig = config;
	}


}

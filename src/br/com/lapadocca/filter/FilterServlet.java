package br.com.lapadocca.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class FilterServlet
 */
@WebFilter("/FilterServlet")
public class FilterServlet implements Filter {

	/**
	 * Default constructor.
	 */
	public FilterServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String context = request.getServletContext().getContextPath();
		

		try {
			HttpSession sessao = ((HttpServletRequest) request).getSession();
			String usuario = null;
			
			if (sessao != null) 
			{
				usuario = (String) sessao.getAttribute("login");
			};
			if (usuario == null) {
				((HttpServletResponse) response).sendRedirect("http://localhost:8080/laPadocca-PI//erro.html");
			} else {
				chain.doFilter(request, response);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

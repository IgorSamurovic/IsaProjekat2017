package servlet;

import entity.User;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

public class AuthorizationFilter implements Filter {

	private String loginPage = null;
	private String homePage = null;
	
	public void init(FilterConfig cfg) throws ServletException {
		loginPage = cfg.getInitParameter("login-page");
		homePage = cfg.getInitParameter("home-page");
		if (loginPage == null)
			throw new ServletException("AuthorizationFilter: missing init parameter 'login-page'");
		if (homePage == null)
			throw new ServletException("AuthorizationFilter: missing init parameter 'home-page'");
	}

	public void destroy() {
	}

	@SuppressWarnings("null")
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest)request;

		HttpSession session = req.getSession(false);
		if (session != null) {
		}
		
		String path = req.getRequestURI();
		if (path.indexOf("logout.jsp") != -1) {
			if (session != null) {
				session.setAttribute("user", null);
				session.invalidate();
				session = null;
			}
		}
		
		if (session == null || session.getAttribute("user") == null) {
			if (path.indexOf("/css/") != -1 ||
				path.indexOf("/js/") != -1 ||
				path.indexOf("/api/user/register") != -1 ||
				path.indexOf("/api/user/activate") != -1 ||
				path.indexOf("/api/user/login") != -1 ||
				path.indexOf("registration.jsp") != -1 ||
				path.indexOf("activation.jsp") != -1 ||
				path.indexOf("login.jsp") != -1
				) {
				chain.doFilter(req, response);
				return;
			} else {
				request.getRequestDispatcher(loginPage).forward(request, response);
				return;
			}
		} else {
			if (path.indexOf("/css/") != -1 ||
				path.indexOf("/js/") != -1 ||
				path.indexOf("/api/user/") != -1 ||
				path.indexOf("/api/restaurant/") != -1 ||
				path.indexOf("/api/reservation/") != -1 ||
				path.indexOf("/api/visit/") != -1 ||
				path.indexOf("/api/order/") != -1 ||
				path.indexOf("/api/invitation/") != -1 ||
				path.indexOf("home.jsp") != -1 ||
				path.indexOf("reservation.jsp") != -1 ||
				path.indexOf("invitation.jsp") != -1 ||
				path.indexOf("profile.jsp") != -1 || 
				path.indexOf("order.jsp") != -1
				) {
				chain.doFilter(req, response);
				return;
			} else {
				request.getRequestDispatcher(homePage).forward(request, response);
				return;
			}
		}
	}

}


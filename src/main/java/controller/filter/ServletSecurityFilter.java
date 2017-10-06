package controller.filter;

import entity.users.UserType;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = { "/jsp/admin/*" },
initParams = { 
		@WebInitParam(name = "INDEX_PATH", value = "/index.jsp")
		})
public class ServletSecurityFilter implements Filter {
	private String indexPath;
	
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		indexPath = fConfig.getInitParameter("INDEX_PATH");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		String role = (String) session.getAttribute("role");
		if(role != String.valueOf(UserType.ADMINISTRATOR)){
			resp.sendRedirect(req.getContextPath() + indexPath);
		}
		chain.doFilter(request, response);
	}
	
	@Override
	public void destroy() {
	}
}

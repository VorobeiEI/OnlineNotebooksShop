package controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = { "/jsp/site/*" },initParams = { @WebInitParam(name = "INDEX_PATH", value = "/index.jsp")})
public class PageRedirectFilter implements Filter {
	private String indexPath;
	
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		indexPath = fConfig.getInitParameter("INDEX_PATH");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.sendRedirect(req.getContextPath() + indexPath);
		chain.doFilter(request, response);
	}
	
	@Override
	public void destroy() {
	}
}

package net.itw.wcms.x27.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.utils.SessionUtil;

/**
 * 
 * Description: 安全过滤器(未登录状态访问系统资源，将返回登录页面)
 * 
 * @author Michael 23 Nov 2017 22:44:09
 */
public class SecurityFilter implements Filter {

	List<String> filterUrls = new ArrayList<>();

	public void destroy() {
		filterUrls = null;
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		boolean isFilter = false;
		String url = request.getRequestURI().replace(request.getContextPath(), "");
		for (String u : filterUrls) {
			if (url.startsWith(u)) {
				isFilter = true;
				break;
			}
		}
		if (isFilter) {
			User operator = SessionUtil.getSessionUser(request);
			if (operator == null) {
				response.sendRedirect(request.getContextPath() + "/web/gotoLoginPage");
			}
		}
		chain.doFilter(req, resp);
	}

	public void init(FilterConfig config) throws ServletException {
		String urls = config.getInitParameter("filterUrls");
		if (StringUtils.isNotBlank(urls)) {
			filterUrls = Arrays.asList(urls.split(","));
		}
	}

}

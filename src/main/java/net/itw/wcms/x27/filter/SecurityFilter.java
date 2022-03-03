package net.itw.wcms.x27.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.itw.wcms.toolkit.jdbc.DBUtil;
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
		try {
			User operator = SessionUtil.getSessionUser(request);
			Connection conn = DBUtil.openConnection();
			if (url.startsWith("/")) {
				url = url.replaceAll("^(/+)", "");
			}
			String sql = "select count(*) AS n from x27_resources t where t.structure like '%"+url+"%'";
			Integer count = null;
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt("n");
				break;
			}
			if ( count != null && count > 0) {
				sql = "select DISTINCT r.structure AS urls from x27_resources r, x27_join_role_resource rr, x27_roles role, x27_join_user_role ur, x27_users u where  r.id = rr.RESOURCE_ID and role.id = rr.ROLE_ID and ur.ROLE_ID = role.id and u.id = ur.USER_ID and u.USER_NAME = '"+operator.getUserName()+"' and r.structure is not null\n";
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				List<String> urls = new ArrayList<>();
				while (rs.next()) {
					String u = rs.getString("urls");
					if (StringUtils.isNotBlank(u)) {
						urls.addAll(Arrays.asList(u.split(",")));
					}
				}
				boolean isPassed = false;
				for (String u : urls) {
					if (url.startsWith(u)) {
						isPassed = true;
						break;
					}
				}
				if (!isPassed) {
					response.sendRedirect(request.getContextPath() + "/web/error");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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

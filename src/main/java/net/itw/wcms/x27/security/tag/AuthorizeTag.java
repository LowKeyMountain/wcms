package net.itw.wcms.x27.security.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import net.itw.wcms.toolkit.sql.SqlMap;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.utils.SessionUtil;

@Component
public class AuthorizeTag extends RequestContextAwareTag {

	private static final long serialVersionUID = 1L;

	private String buttonUrl;
	private String currentUser;
	private static SqlMap sqlMap;

	static {
		try {
			sqlMap = SqlMap.load(SqlMap.class.getResourceAsStream("./x27.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public String getButtonUrl() {
		return buttonUrl;
	}

	public void setButtonUrl(String buttonUrl) {
		this.buttonUrl = buttonUrl;
	}

	public String getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}

	@Override
	protected int doStartTagInternal() throws Exception {
		User operator = SessionUtil.getSessionUser((HttpServletRequest) this.pageContext.getRequest());
		if (operator != null) {
			Object[] args = new Object[] { operator.getUserName() };
			JdbcTemplate jdbcTemplate = (JdbcTemplate) this.getRequestContext().getWebApplicationContext()
					.getBean("jdbcTemplate");
			List<String> list = jdbcTemplate.queryForList(sqlMap.getSql("getUserSecurityMap"), args, String.class);
			if (list != null && list.contains(buttonUrl)) {
				return EVAL_BODY_INCLUDE;
			}
		}
		return SKIP_BODY;
	}

}

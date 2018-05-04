package net.itw.wcms.x27.security.tag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.service.IResourceService;
import net.itw.wcms.x27.utils.SessionUtil;

@Component
public class AuthorizeTag extends RequestContextAwareTag {

	private static final long serialVersionUID = 1L;

	private String buttonUrl;
	private String currentUser;

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
			IResourceService iResourceService = (IResourceService) this.getRequestContext().getWebApplicationContext()
					.getBean(IResourceService.class);
			List<String> list = iResourceService.getResourcesByUserName(operator.getUserName());
			if (list != null && list.contains(buttonUrl)) {
				return EVAL_BODY_INCLUDE;
			}
		}
		return SKIP_BODY;
	}
}

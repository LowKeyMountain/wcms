package net.itw.wcms.x27.security.tag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.service.IResourceService;
import net.itw.wcms.x27.utils.SessionUtil;

@Component
public class AuthorizeUtils {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IResourceService iResourceService;

	/**
	 * 权限验证
	 * 
	 * @param request
	 * @param buttonUrl
	 * @return
	 */
	public boolean authorize(HttpServletRequest request, String buttonUrl) {
		if (request != null) {
			User operator = SessionUtil.getSessionUser(request);
			if (operator != null) {
				List<String> list = iResourceService.getResourcesByUserName(operator.getUserName());
				if (list != null && list.contains(buttonUrl)) {
					return true;
				}
			}
		}
		return false;
	}

}

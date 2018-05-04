package net.itw.wcms.toolkit;

import javax.servlet.http.HttpServletRequest;

public class AuthorizeOptions {

	public HttpServletRequest request;

	public static AuthorizeOptions createAuthorizeOptions(HttpServletRequest request) {
		AuthorizeOptions options = new AuthorizeOptions();
		options.request = request;
		return options;
	}

}

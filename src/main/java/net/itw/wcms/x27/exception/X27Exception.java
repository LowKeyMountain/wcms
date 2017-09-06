package net.itw.wcms.x27.exception;

/**
 * 
 * Description: 用户体系异常类
 *
 * @author Michael 5 Sep 2017 14:45:28
 *
 */
public class X27Exception extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public X27Exception(String message) {
		super(message);
	}

	public X27Exception(String message, Throwable cause) {
		super(message, cause);
	}
	
}

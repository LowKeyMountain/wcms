package net.itw.wcms.ship.exception;

/**
 * 
 * Description: 用户体系异常类
 *
 * @author Michael 5 Sep 2017 14:45:28
 *
 */
public class TaskException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TaskException(String message) {
		super(message);
	}

	public TaskException(String message, Throwable cause) {
		super(message, cause);
	}
	
}

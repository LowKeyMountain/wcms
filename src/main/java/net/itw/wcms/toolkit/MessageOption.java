package net.itw.wcms.toolkit;

import java.util.Map;

/**
 * Description: 消息选项（处理方法调用时，返回值信息存储，包括：状态码、提示信息等）
 * 
 * @author Michael 19 Nov 2017 09:12:33
 */
public class MessageOption {

	// 状态码： 0|成功、1|失败
	public Integer code;
	// 提示信息
	public String msg;
	// 返回值
	public Map<String, ?> data;

	public MessageOption(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public boolean isSuccess() {
		if (1 == code) {
			return true;
		}
		return false;
	}
	
}

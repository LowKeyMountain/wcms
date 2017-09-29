package net.itw.wcms.x27.entity;

import java.io.Serializable;

import org.springframework.data.domain.PageRequest;

/**
 * 用户查询SearchModel
 */
public class UserSearchModel extends PageRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer pageNo = 1;
	private Integer pageSize = PrivilegeModelConstant.PageSize;

	// 登录用户名
	private String username;
	// 真实姓名
	private String fullname;

	public UserSearchModel(int page, int size) {
		super(page, size);
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
}

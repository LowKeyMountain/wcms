package net.itw.wcms.x27.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import net.itw.wcms.toolkit.hibernate.Entityable;

/**
 * Description: 用户体系-角色类
 * 
 * @author Michael 18 Oct 2017 14:38:28
 */
@Table(name = "X27_ROLES")
@Entity
public class Role implements Entityable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String roleName;
	private String remark;
	private Date updateDate;
	private String updatePersion;

	private Set<User> users = new HashSet<>();
	private Set<Privilege> privileges = new HashSet<>();

	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "ROLE_NAME")
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@ManyToMany(mappedBy = "roles")
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@JoinTable(name = "X27_JOIN_ROLE_PRIVILEGE", joinColumns = {
			@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") }, inverseJoinColumns = {
					@JoinColumn(name = "PRIVILEGE_ID", referencedColumnName = "ID") })
	@ManyToMany
	public Set<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}
	
	@Column(name="UPDATE_DATE")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	@Column(name="UPDATE_PERSION")
	public String getUpdatePersion() {
		return updatePersion;
	}

	public void setUpdatePersion(String updatePersion) {
		this.updatePersion = updatePersion;
	}
	
}

package net.itw.wcms.x27.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import net.itw.wcms.toolkit.hibernate.Entityable;

/**
 * 
 * Description:用户体系-机构表
 * 
 * @author Michael 31 Aug 2017 14:09:47
 */
@Table(name = "X27_ORGANIZATIONS")
@Entity
public class X27_Organization implements Entityable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String orgName;
	private String orgShortName;
	private String orgCode;
	private String memo;
	private Integer parent;
	private Date createDate;
	
	private Set<X27_User> users = new HashSet<>();
	
	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "ORG_NAME")
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Column(name = "ORG_SHORT_NAME")
	public String getOrgShortName() {
		return orgShortName;
	}

	public void setOrgShortName(String orgShortName) {
		this.orgShortName = orgShortName;
	}

	@Column(name = "ORG_CODE")
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "FK_PARENT_ORG_ID")
	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	@Column(name = "CREATE_DATE")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@ManyToMany(mappedBy="orgs")
	public Set<X27_User> getUsers() {
		return users;
	}

	public void setUsers(Set<X27_User> users) {
		this.users = users;
	}
	
}

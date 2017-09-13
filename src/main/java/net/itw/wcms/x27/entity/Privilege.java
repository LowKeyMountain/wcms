package net.itw.wcms.x27.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 
 * Description: 用户体系-权限类
 * @author Michael 30 Aug 2017 16:57:24
 */
@Table(name="X27_PRIVILEGES")
@Entity
public class X27_Privilege {

	private Integer id;
	private String privilName;
	private String privilValue;
	private String category;
	private String description;
	
	private Set<X27_Role> roles = new HashSet<>();
	
	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="PRIVIL_NAME")
	public String getPrivilName() {
		return privilName;
	}
	
	public void setPrivilName(String privilName) {
		this.privilName = privilName;
	}
	
	@Column(name="PRIVIL_VALUE")
	public String getPrivilValue() {
		return privilValue;
	}

	public void setPrivilValue(String privilValue) {
		this.privilValue = privilValue;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@ManyToMany(mappedBy="privileges")
	public Set<X27_Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<X27_Role> roles) {
		this.roles = roles;
	}
	
}

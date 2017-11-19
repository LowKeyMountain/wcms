package net.itw.wcms.x27.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Description: 用户体系-资源类
 * 
 * @author Michael 20 Nov 2017 23:15:22
 */
@Table(name = "X27_Resources")
@Entity
public class Resource implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private Integer id;

    private String name;

    private String url;

    private String remark;

    private Integer parentId;

    private String structure;

    private Integer sortNo;

    private String moduleFlag;

    private String createPerson;

    private Date createDate;

    private String updatePerson;

    private Date updateDate;
    
    private Set<User> users = new HashSet<>();
    
	@GeneratedValue
	@Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Column(name="PARENT_ID")
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }
    
    @Column(name="SORT_NO")
    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    @Column(name="MODULE_FLAG")
    public String getModuleFlag() {
        return moduleFlag;
    }

    public void setModuleFlag(String moduleFlag) {
        this.moduleFlag = moduleFlag;
    }
    
    @Column(name="CREATE_PERSON")
    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }
    
    @Column(name="CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    @Column(name="UPDATE_PERSON")
    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }
    
	@Column(name = "UPDATE_DATE")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    
	@ManyToMany(mappedBy = "resources")
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
    
}
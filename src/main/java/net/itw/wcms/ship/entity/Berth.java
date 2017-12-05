package net.itw.wcms.ship.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.itw.wcms.toolkit.hibernate.Entityable;

/**
 * 
 * Description: 泊位字典表
 * 
 * @author Michael 30 Nov 2017 22:29:50
 */
@Entity
@Table(name = "tab_berth")
public class Berth implements Entityable {

	private static final long serialVersionUID = 1L;

	private Integer id; // 编号
	private String berthName;
	private Integer status; // 当前状态 （空闲|0、已占用|1）
	
	private String updateUser;
	private Date updateTime;
	private String remarks;
	
	/**泊位状态：空闲*/
	public static Integer BerthStatus_Leisure = 0; 
	/**泊位状态：已占用*/
	public static Integer BerthStatus_Occupied = 1; 
	
	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "update_user")
	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	@Column(name="berth_name")
	public String getBerthName() {
		return berthName;
	}

	public void setBerthName(String berthName) {
		this.berthName = berthName;
	}
	
}

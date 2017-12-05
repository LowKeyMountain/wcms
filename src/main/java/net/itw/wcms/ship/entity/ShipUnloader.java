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
 * Description: 卸船机字典表
 * 
 * @author Michael 30 Nov 2017 22:29:50
 */
@Entity
@Table(name = "tab_ship_unloader")
public class ShipUnloader implements Entityable {

	private static final long serialVersionUID = 1L;

	private Integer id; // 卸船机编号
	private String shipUnloaderName;
	private Integer status; // 当前状态 （未作业|0、作业中|1）

	private String updateUser;
	private Date updateTime;
	private String remarks;

	/** 卸船机状态：未作业 */
	public static Integer ShipUnloaderStatus_Leisure = 0;
	/** 卸船机状态：作业中 */
	public static Integer ShipUnloaderStatus_Occupied = 1;

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

	@Column(name = "ship_unloader_name")
	public String getShipUnloaderName() {
		return shipUnloaderName;
	}

	public void setShipUnloaderName(String shipUnloaderName) {
		this.shipUnloaderName = shipUnloaderName;
	}

}

package net.itw.wcms.ship.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.itw.wcms.toolkit.hibernate.Entityable;

/**
 * 
 * Description:作业船舱表
 * 
 * @author Michael 25 Nov 2017 20:54:44
 */
@Entity
@Table(name = "tab_task_cabin_detail")
public class TaskCabinDetail implements Entityable {

	private static final long serialVersionUID = 1L;

	private Integer id; // 编号
	private String cabinNo; // 船舱编号
	private String cargoType; // 货物种类
	private Double preunloading; // 装载量（单位：吨）
	private Boolean isFinish; // 是否清舱（即，是否完成）
	
	private String loadingPort;// 装货港
	private String moisture;// 水分
	private String quality;// 品质
	private Double stowage;// 配载吨位（单位：吨）

	private String updateUser;
	private Date updateTime;
	private String remarks;
	
	private Task task; // 作业船舶信息

	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "cabin_no", nullable = false)
	public String getCabinNo() {
		return cabinNo;
	}

	public void setCabinNo(String cabinNo) {
		this.cabinNo = cabinNo;
	}

	@Column(name = "cargo_type")
	public String getCargoType() {
		return cargoType;
	}

	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}

	@Column(name = "preunloading")
	public Double getPreunloading() {
		return preunloading;
	}

	public void setPreunloading(Double preunloading) {
		this.preunloading = preunloading;
	}

	@Column(name = "update_user")
	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@Column(name = "update_time")
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

	@ManyToOne(fetch=FetchType.LAZY,cascade=(CascadeType.ALL))
	@JoinColumn(name="task_id")	
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Boolean getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(Boolean isFinish) {
		this.isFinish = isFinish;
	}
	
	@Column(name="loading_port")
	public String getLoadingPort() {
		return loadingPort;
	}

	public void setLoadingPort(String loadingPort) {
		this.loadingPort = loadingPort;
	}
	
	@Column(name="moisture")
	public String getMoisture() {
		return moisture;
	}

	public void setMoisture(String moisture) {
		this.moisture = moisture;
	}
	
	@Column(name="quality")
	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	@Column(name="stowage")
	public Double getStowage() {
		return stowage;
	}

	public void setStowage(Double stowage) {
		this.stowage = stowage;
	}

}
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
 * Description:作业货物信息表
 * 
 * @author Michael 8 Dec 2017 22:49:44
 */
@Entity
@Table(name = "tab_cargo")
public class Cargo implements Entityable {

	private static final long serialVersionUID = 1L;

	private Integer id; // 编号

	private String cargoCategory; // 货物种类
	private String cargoType; // 货物类型
	private String loadingPort;// 装货港
	private String moisture;// 水分
	private String quality;// 品质
	private Float stowage;// 配载吨位（单位：吨）
	private String cargoOwner; // 货主

	private String updateUser;
	private Date updateTime;
	private String remarks;
	
	private Task task; // 作业任务信息
	
	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "cargo_type")
	public String getCargoType() {
		return cargoType;
	}

	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
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

	@Column(name = "loading_port")
	public String getLoadingPort() {
		return loadingPort;
	}

	public void setLoadingPort(String loadingPort) {
		this.loadingPort = loadingPort;
	}

	@Column(name = "moisture")
	public String getMoisture() {
		return moisture;
	}

	public void setMoisture(String moisture) {
		this.moisture = moisture;
	}

	@Column(name = "quality")
	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	@Column(name = "stowage")
	public Float getStowage() {
		return stowage;
	}

	public void setStowage(Float stowage) {
		this.stowage = stowage;
	}

	@Column(name = "cargo_category")
	public String getCargoCategory() {
		return cargoCategory;
	}

	public void setCargoCategory(String cargoCategory) {
		this.cargoCategory = cargoCategory;
	}

	@Column(name = "cargo_owner")
	public String getCargoOwner() {
		return cargoOwner;
	}

	public void setCargoOwner(String cargoOwner) {
		this.cargoOwner = cargoOwner;
	}
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = (CascadeType.ALL))
	@JoinColumn(name = "task_id")
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

}
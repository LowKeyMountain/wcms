package net.itw.wcms.ship.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
 * Description: 码头作业信息详情表
 *
 * @author sparking 2017-11-17 上午11:06:08
 *
 */
@Entity
@Table(name = "wcms_task_detail")
public class TaskDetail implements Entityable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String cabinNo;
	private Double startPosition;
	private Double endPosition;
	private String cargoType;
	private String cargoCategory;

	private String loadingPort;
	private String quality;
	private String cargoProperty;
	private String moisture;
	private Double stowage;
	private String cargoOwner;
	private Double preunloading;
	private Double actualUnloading;
	private Double actualBulk;
	private Double actualPowdery;
	private Double remainder;
	private Double clearance;
	private Date startTime;
	private Date endTime;
	private Double usedTime;
	private String efficiency;
	private String cabinWater;
	private Boolean guarantee;
	private String status;

	private String updateUser;
	private Date updateTime;
	private String remarks;
	
	private Task task;


	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "cabin_no", nullable=false)
	public String getCabinNo() {
		return cabinNo;
	}

	public void setCabinNo(String cabinNo) {
		this.cabinNo = cabinNo;
	}

	@Column(name = "start_position")
	public Double getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(Double startPosition) {
		this.startPosition = startPosition;
	}
	
	@Column(name = "end_position")
	public Double getEndPosition() {
		return endPosition;
	}

	public void setEndPosition(Double endPosition) {
		this.endPosition = endPosition;
	}

	@Column(name = "cargo_type")
	public String getCargoType() {
		return cargoType;
	}

	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}

	@Column(name = "cargo_category")
	public String getCargoCategory() {
		return cargoCategory;
	}

	public void setCargoCategory(String cargoCategory) {
		this.cargoCategory = cargoCategory;
	}

	@Column(name = "loading_port")
	public String getLoadingPort() {
		return loadingPort;
	}

	public void setLoadingPort(String loadingPort) {
		this.loadingPort = loadingPort;
	}

	@Column(name = "quality")
	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	@Column(name = "cargo_property")
	public String getCargoProperty() {
		return cargoProperty;
	}

	public void setCargoProperty(String cargoProperty) {
		this.cargoProperty = cargoProperty;
	}

	@Column(name = "moisture")
	public String getMoisture() {
		return moisture;
	}

	public void setMoisture(String moisture) {
		this.moisture = moisture;
	}

	@Column(name = "stowage")
	public Double getStowage() {
		return stowage;
	}

	public void setStowage(Double stowage) {
		this.stowage = stowage;
	}

	@Column(name = "cargo_owner")
	public String getCargoOwner() {
		return cargoOwner;
	}

	public void setCargoOwner(String cargoOwner) {
		this.cargoOwner = cargoOwner;
	}

	@Column(name = "preunloading")
	public Double getPreunloading() {
		return preunloading;
	}

	public void setPreunloading(Double preunloading) {
		this.preunloading = preunloading;
	}

	@Column(name = "actual_unloading")
	public Double getActualUnloading() {
		return actualUnloading;
	}

	public void setActualUnloading(Double actualUnloading) {
		this.actualUnloading = actualUnloading;
	}

	@Column(name = "actual_bulk")
	public Double getActualBulk() {
		return actualBulk;
	}

	public void setActualBulk(Double actualBulk) {
		this.actualBulk = actualBulk;
	}

	@Column(name = "actual_powdery")
	public Double getActualPowdery() {
		return actualPowdery;
	}

	public void setActualPowdery(Double actualPowdery) {
		this.actualPowdery = actualPowdery;
	}

	@Column(name = "remainder")
	public Double getRemainder() {
		return remainder;
	}

	public void setRemainder(Double remainder) {
		this.remainder = remainder;
	}

	@Column(name = "clearance")
	public Double getClearance() {
		return clearance;
	}

	public void setClearance(Double clearance) {
		this.clearance = clearance;
	}

	@Column(name = "start_time")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "used_time")
	public Double getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Double usedTime) {
		this.usedTime = usedTime;
	}

	@Column(name = "efficiency")
	public String getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(String efficiency) {
		this.efficiency = efficiency;
	}

	@Column(name = "cabin_water")
	public String getCabinWater() {
		return cabinWater;
	}

	public void setCabinWater(String cabinWater) {
		this.cabinWater = cabinWater;
	}

	@Column(name = "guarantee")
	public Boolean getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(Boolean guarantee) {
		this.guarantee = guarantee;
	}

	@Column(name = "status")	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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

}
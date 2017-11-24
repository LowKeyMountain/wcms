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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.itw.wcms.toolkit.hibernate.Entityable;

/**
 * Description: 码头作业信息表
 *
 * @author sparking 2017-11-16 上午11:06:08
 *
 */
@Entity
@Table(name = "wcms_task")
public class Task implements Entityable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer berthId;
	private Date beginTime;
	private Date endTime;
	private Date berthingTime;
	private Date departureTime;
	private String status;
	private String cargoType;
	private Double load;
//	private Boolean wireCable;
//	private Integer wireNum;
	private String updateUser;
	private Date updateTime;
	private String remarks;

	private Set<TaskDetail> taskDetail = new HashSet<>();
	private Ship ship;
	
	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "berth_id", nullable=false)
	public Integer getBerthId() {
		return berthId;
	}

	public void setBerthId(Integer berthId) {
		this.berthId = berthId;
	}

	@Column(name = "load")	
	public Double getLoad() {
		return load;
	}

	public void setLoad(Double load) {
		this.load = load;
	}

	@Column(name = "cargo_type")	
	public String getCargoType() {
		return cargoType;
	}

	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}
	
	@Column(name = "status")	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

/*	@Column(name = "wirecable")
	public Boolean getWireCable() {
		return wireCable;
	}

	public void setWireCable(Boolean wireCable) {
		this.wireCable = wireCable;
	}

	@Column(name = "wire_num")
	public Integer getWireNum() {
		return wireNum;
	}

	public void setWireNum(Integer wireNum) {
		this.wireNum = wireNum;
	}

	@Column(name = "special_cabintype")
	public String getSpecialCabinType() {
		return specialCabinType;
	}

	public void setSpecialCabinType(String specialCabinType) {
		this.specialCabinType = specialCabinType;
	}*/
	
	@Column(name = "begin_time")
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	@Column(name = "end_time")
	@Temporal(TemporalType.TIMESTAMP)	
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "berthing_time")
	@Temporal(TemporalType.TIMESTAMP)	
	public Date getBerthingTime() {
		return berthingTime;
	}

	public void setBerthingTime(Date berthingTime) {
		this.berthingTime = berthingTime;
	}

	@Column(name = "departure_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
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

	@OneToMany(mappedBy="task", cascade=CascadeType.ALL)
	public Set<TaskDetail> getTaskDetail() {
		return taskDetail;
	}

	public void setTaskDetail(Set<TaskDetail> taskDetail) {
		this.taskDetail = taskDetail;
	}

	@ManyToOne(fetch=FetchType.LAZY,cascade=(CascadeType.ALL))
	@JoinColumn(name="ship_id")
	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}
}


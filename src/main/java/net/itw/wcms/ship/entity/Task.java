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
 * 
 * Description: 船舶作业信息表
 * 
 * @author Michael 25 Nov 2017 20:35:38
 */
@Entity
@Table(name = "tab_task")
public class Task implements Entityable {

	private static final long serialVersionUID = 1L;

	private Integer id; // 编号
	private Date berthingTime; // 入港时间
	private Date departureTime; // 离港时间
	private Date beginTime; // 开始卸货时间
	private Date endTime; // 结束卸货时间
	private Integer status; // 作业状态 （已入港|0、 预卸货|1、 卸货中|2、 完成卸船|3、 已离港|4）
	private Double cargoLoad; // 货物总重（单位：吨）

	private String updateUser;
	private Date updateTime;
	private String remarks;

	private Ship ship; // 船舶信息
	private Set<TaskCabinDetail> taskCabinDetails = new HashSet<>(); // 作业船舱信息
	private Set<TaskBerth> taskBerths = new HashSet<>(); // 作业船舶与泊位关系

	/** 船舶状态：已入港|0 */
	public static Integer TaskStatus_HaveEntry = 0;
	/** 船舶状态：预卸货|1 */
	public static Integer TaskStatus_PreDischarge = 1;
	/** 船舶状态：卸货中|2 */
	public static Integer TaskStatus_InDischarge = 2;
	/** 船舶状态：完成卸船|3 */
	public static Integer TaskStatus_Leave = 3;
	/** 船舶状态：已离港|4 */
	public static Integer TaskStatus_Finished = 4;

	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "cargo_load")
	public Double getCargoLoad() {
		return cargoLoad;
	}

	public void setCargoLoad(Double cargoLoad) {
		this.cargoLoad = cargoLoad;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

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

	@OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
	public Set<TaskCabinDetail> getTaskCabinDetails() {
		return taskCabinDetails;
	}

	public void setTaskCabinDetails(Set<TaskCabinDetail> taskCabinDetails) {
		this.taskCabinDetails = taskCabinDetails;
	}

	@OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
	public Set<TaskBerth> getTaskBerths() {
		return taskBerths;
	}

	public void setTaskBerths(Set<TaskBerth> taskBerths) {
		this.taskBerths = taskBerths;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = (CascadeType.ALL))
	@JoinColumn(name = "ship_id")
	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

}

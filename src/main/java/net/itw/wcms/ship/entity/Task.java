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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.itw.wcms.toolkit.hibernate.Entityable;

/**
 * 
 * Description: 作业信息表
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
	private Integer status; // 作业状态 （预靠船舶|0、作业船舶|1、 离港船舶|2）
	private Float cargoLoad; // 货物总重（单位：吨）
	private Integer berth; // 泊位（泊一|1、泊二|2）
	private Float berthingDepth; // 入港吃水（单位：米）
	private Float departureDepth; // 离港吃水（单位：米）
	private Float berthingFreeboardDepth; // 入港干舷高度（单位：米）
	private Float departureFreeboardDepth; // 离港干舷高度（单位：米）

	private String updateUser;
	private Date updateTime;
	private String remarks;

	private Ship ship; // 船舶信息
	private Set<Cabin> cabins = new HashSet<>(); // 船舱信息
	private Set<Cargo> cargos = new HashSet<>(); // 货物信息

	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "cargo_load")
	public Float getCargoLoad() {
		return cargoLoad;
	}

	public void setCargoLoad(Float cargoLoad) {
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
	public Set<Cabin> getCabins() {
		return cabins;
	}

	public void setCabins(Set<Cabin> cabins) {
		this.cabins = cabins;
	}
	
	@OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
	public Set<Cargo> getCargos() {
		return cargos;
	}

	public void setCargos(Set<Cargo> cargos) {
		this.cargos = cargos;
	}

	@OneToOne(fetch = FetchType.LAZY, cascade = (CascadeType.ALL))
	@JoinColumn(name = "ship_id")
	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	@Column(name = "berth")
	public Integer getBerth() {
		return berth;
	}

	public void setBerth(Integer berth) {
		this.berth = berth;
	}

	@Column(name = "berthing_freeboard_depth")
	public Float getBerthingFreeboardDepth() {
		return berthingFreeboardDepth;
	}

	public void setBerthingFreeboardDepth(Float berthingFreeboardDepth) {
		this.berthingFreeboardDepth = berthingFreeboardDepth;
	}

	@Column(name = "departure_freeboard_depth")
	public Float getDepartureFreeboardDepth() {
		return departureFreeboardDepth;
	}

	public void setDepartureFreeboardDepth(Float departureFreeboardDepth) {
		this.departureFreeboardDepth = departureFreeboardDepth;
	}

	@Column(name = "berthing_depth")
	public Float getBerthingDepth() {
		return berthingDepth;
	}

	public void setBerthingDepth(Float berthingDepth) {
		this.berthingDepth = berthingDepth;
	}

	@Column(name = "departure_depth")
	public Float getDepartureDepth() {
		return departureDepth;
	}

	public void setDepartureDepth(Float departureDepth) {
		this.departureDepth = departureDepth;
	}

}

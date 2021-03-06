package net.itw.wcms.ship.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.format.annotation.DateTimeFormat;

import net.itw.wcms.toolkit.hibernate.Entityable;

/**
 * 
 * Description:作业船舱表
 * 
 * @author Michael 8 Dec 2017 22:51:03
 */
@Entity
@Table(name = "tab_cabin")
public class Cabin implements Entityable {

	private static final long serialVersionUID = 1L;

	private Integer id; // 编号
	private Integer cabinNo; // 船舱编号
	private Float startPosition; // 开始位置
	private Float endPosition; // 结束位置
	private Float preunloading; // 装载量（单位：吨）
	private Integer status; // 状态 （卸货|0、清舱|1、完成|2）
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private Date clearTime; //  清舱时间

	private String updateUser;
	private Date updateTime;
	private String remarks;

	private Cargo cargo; // 货物信息

	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "cabin_no", nullable = false)
	public Integer getCabinNo() {
		return cabinNo;
	}

	public void setCabinNo(Integer cabinNo) {
		this.cabinNo = cabinNo;
	}

	@Column(name = "preunloading")
	public Float getPreunloading() {
		return preunloading;
	}

	public void setPreunloading(Float preunloading) {
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

	@ManyToOne(fetch = FetchType.LAZY)//, cascade = {CascadeType.PERSIST}
	@JoinColumn(name = "cargo_id")
	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	@Column(name = "start_position")
	public Float getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(Float startPosition) {
		this.startPosition = startPosition;
	}

	@Column(name = "end_position")
	public Float getEndPosition() {
		return endPosition;
	}

	public void setEndPosition(Float endPosition) {
		this.endPosition = endPosition;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name="clear_time")
	public Date getClearTime() {
		return clearTime;
	}

	public void setClearTime(Date clearTime) {
		this.clearTime = clearTime;
	}
	
}
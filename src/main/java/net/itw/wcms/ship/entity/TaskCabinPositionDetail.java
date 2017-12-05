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
 * Description:作业舱位表
 * 
 * @author Michael 25 Nov 2017 20:54:44
 */
@Entity
@Table(name = "tab_task_cabin_position")
public class TaskCabinPositionDetail implements Entityable {

	private static final long serialVersionUID = 1L;

	private Integer id; // 编号
	private String cabinNo; // 船舱编号
	private Double startPosition; // 开始位置
	private Double endPosition; // 结束位置

	private String updateUser;
	private Date updateTime;
	private String remarks;
	
	private TaskBerth taskBerth;

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
	@JoinColumn(name="task_berth_id")	
	public TaskBerth getTaskBerth() {
		return taskBerth;
	}

	public void setTaskBerth(TaskBerth taskBerth) {
		this.taskBerth = taskBerth;
	}
	

}
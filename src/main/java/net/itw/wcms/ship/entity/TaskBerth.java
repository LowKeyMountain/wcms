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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.itw.wcms.toolkit.hibernate.Entityable;

/**
 * Description: 作业信息-泊位表
 * 
 * 该表描述：作业船舶与泊位关联信息；<br/>
 * 
 * 作业船舶与泊位一对多关系。
 *  
 * @author Michael 25 Nov 2017 20:23:54
 */
@Entity
@Table(name = "tab_task_berth")
public class TaskBerth implements Entityable {

	private static final long serialVersionUID = 1L;

	private Integer id; // 编号
	private Boolean isSetPosition; // 是否设置舱位

	private Task task; // 船舶作业信息
	private Berth berth; // 泊位信息
	private Set<TaskCabinPositionDetail> taskCabinPositionDetails = new HashSet<>(); // 作业舱位信息
	private Set<TaskUnloadShipDetail> taskUnloadShipDetails = new HashSet<>();  // 作业卸船机信息
	
	private String updateUser;
	private Date updateTime;
	private String remarks;

	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	@ManyToOne(fetch = FetchType.LAZY, cascade = (CascadeType.ALL))
	@JoinColumn(name = "task_id")
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@OneToMany(mappedBy = "taskBerth", cascade = CascadeType.ALL)
	public Set<TaskCabinPositionDetail> getTaskCabinPositionDetails() {
		return taskCabinPositionDetails;
	}

	public void setTaskCabinPositionDetails(Set<TaskCabinPositionDetail> taskCabinPositionDetails) {
		this.taskCabinPositionDetails = taskCabinPositionDetails;
	}

	@Column(name = "is_set_position")
	public Boolean getIsSetPosition() {
		return isSetPosition;
	}

	public void setIsSetPosition(Boolean isSetPosition) {
		this.isSetPosition = isSetPosition;
	}
	
	@OneToOne(fetch = FetchType.LAZY, cascade = (CascadeType.ALL))
	@JoinColumn(name = "berth_id")
	public Berth getBerth() {
		return berth;
	}

	public void setBerth(Berth berth) {
		this.berth = berth;
	}
	
	@OneToMany(mappedBy = "taskBerth", cascade = CascadeType.ALL)
	public Set<TaskUnloadShipDetail> getTaskUnloadShipDetails() {
		return taskUnloadShipDetails;
	}

	public void setTaskUnloadShipDetails(Set<TaskUnloadShipDetail> taskUnloadShipDetails) {
		this.taskUnloadShipDetails = taskUnloadShipDetails;
	}
	
}

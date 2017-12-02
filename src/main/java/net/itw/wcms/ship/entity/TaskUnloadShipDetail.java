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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.itw.wcms.toolkit.hibernate.Entityable;

/**
 * Description: 作业信息-卸船信息表
 * 
 * 该表描述：船舶卸船作业时，船舶与卸船机的关联关系信息；<br/>
 * 支持场景：
 * 
 * a. 一条船被一台卸船机作业，完成卸船； b. 一艘船被多台卸船机作业，完成卸货；
 * 
 * @author Michael 25 Nov 2017 20:23:54
 */
@Entity
@Table(name = "tab_task_unload_ship_detail")
public class TaskUnloadShipDetail implements Entityable {

	private static final long serialVersionUID = 1L;

	private Integer id; // 编号
	private Date startTime; // 开始作业时间
	private Date endTime; // 结束作业时间

	private String updateUser;
	private Date updateTime;
	private String remarks;

	private TaskBerth taskBerth; // 作业泊位信息
	private ShipUnloader shipUnloader; // 卸船机信息
	
	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	@JoinColumn(name = "ship_id")
	public TaskBerth getTaskBerth() {
		return taskBerth;
	}

	public void setTaskBerth(TaskBerth taskBerth) {
		this.taskBerth = taskBerth;
	}
	
	@OneToOne(fetch = FetchType.LAZY, cascade = (CascadeType.ALL))
	@JoinColumn(name = "ship_unloader_id")
	public ShipUnloader getShipUnloader() {
		return shipUnloader;
	}

	public void setShipUnloader(ShipUnloader shipUnloader) {
		this.shipUnloader = shipUnloader;
	}
	
}

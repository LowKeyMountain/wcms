package net.itw.wcms.ship.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import net.itw.wcms.toolkit.hibernate.Entityable;


/**
 * Description: 卸船机信息表
 *
 * @author sparking 2018-01-01 上午9:24:14
 *
 */
@Entity
@Table(name = "tab_unloader_all")
public class UnloaderAll implements Entityable {

	private static final long serialVersionUID = 1L;

	private int id;	//主键id
	private String cmsId;	//卸船机编号
	private String operationType;	//操作类型(0|大车位置;1|作业量信息)
	private Date time;	//操作时间
	private Date pushTime;	//操作时间
	private String direction;	//方向(0|正方向;1|反方向)
	private Float unloaderMove;	//移动位置
	private Float oneTask;	//一次抓钩作业量
	
	@Id	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}	

	@Column(name="Cmsid")
	public String getCmsId() {
		return cmsId;
	}

	public void setCmsId(String cmsId) {
		this.cmsId = cmsId;
	}

	@Column(name="operationType")
	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	
	@Column(name="Time")	
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	@Column(name="PushTime")		
	public Date getPushTime() {
		return pushTime;
	}

	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}

	@Column(name="direction")
	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	@Column(name="unloaderMove")	
	public Float getUnloaderMove() {
		return unloaderMove;
	}

	public void setUnloaderMove(Float unloaderMove) {
		this.unloaderMove = unloaderMove;
	}

	@Column(name="oneTask")	
	public Float getOneTask() {
		return oneTask;
	}

	public void setOneTask(Float oneTask) {
		this.oneTask = oneTask;
	}

}

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.itw.wcms.toolkit.hibernate.Entityable;

/**
 * Description: 船舶信息表
 *
 * @author sparking 2017-11-08 下午4:06:08
 *
 */
@Entity
@Table(name = "wcms_ship")
public class Ship implements Entityable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String shipName;
	private String shipEnName;
	private String imoNo;
	private Date buildDate;
	private String length;
	private String breadth;
	private String mouldedDepth;
	private String cabinNum;
	private String hatch;
//	private Double load;
	private String specialCabinType;
//	private String status;
	
//	private Boolean wireCable;
//	private Integer wireNum;
//	private String beginTime;
//	private String endTime;
//	private String berthingTime;
//	private String departureTime;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	private String remarks;

	private Set<Task> task = new HashSet<>();
	
	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "ship_cname")
	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	@Column(name = "ship_ename")
	public String getShipEnName() {
		return shipEnName;
	}

	public void setShipEnName(String shippEnName) {
		this.shipEnName = shippEnName;
	}

	@Column(name = "imo_no")
	public String getImoNo() {
		return imoNo;
	}

	public void setImoNo(String imoNo) {
		this.imoNo = imoNo;
	}

	@Column(name = "build_date")
	public Date getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}
	
	@Column(name = "length")
	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}
	
	@Column(name = "breadth")
	public String getBreadth() {
		return breadth;
	}

	public void setBreadth(String breadth) {
		this.breadth = breadth;
	}

	@Column(name = "moulded_depth")
	public String getMouldedDepth() {
		return mouldedDepth;
	}

	public void setMouldedDepth(String mouldedDepth) {
		this.mouldedDepth = mouldedDepth;
	}
	
	@Column(name = "cabin_num")
	public String getCabinNum() {
		return cabinNum;
	}

	public void setCabinNum(String cabinNum) {
		this.cabinNum = cabinNum;
	}

	@Column(name = "hatch")
	public String getHatch() {
		return hatch;
	}

	public void setHatch(String hatch) {
		this.hatch = hatch;
	}

/*	@Column(name = "load")	
	public Double getLoad() {
		return load;
	}

	public void setLoad(Double load) {
		this.load = load;
	}
	
	@Column(name = "status")	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "wirecable")
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
	}*/

	@Column(name = "special_cabintype")
	public String getSpecialCabinType() {
		return specialCabinType;
	}

	public void setSpecialCabinType(String specialCabinType) {
		this.specialCabinType = specialCabinType;
	}
	
/*	@Column(name = "begin_time")
	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	
	@Column(name = "end_time")
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Column(name = "berthing_time")
	public String getBerthingTime() {
		return berthingTime;
	}

	public void setBerthingTime(String berthingTime) {
		this.berthingTime = berthingTime;
	}

	@Column(name = "departure_time")
	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
*/
	@Column(name = "create_user")	
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Column(name = "create_time")
//    @Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "update_user")
	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	@Column(name = "update_time")
//    @Temporal(TemporalType.TIMESTAMP)
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

	@OneToMany(mappedBy="ship",cascade=(CascadeType.ALL))
	public Set<Task> getTask() {
		return task;
	}

	public void setTask(Set<Task> task) {
		this.task = task;
	}

}


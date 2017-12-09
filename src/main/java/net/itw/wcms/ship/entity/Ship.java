package net.itw.wcms.ship.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import net.itw.wcms.toolkit.hibernate.Entityable;

/**
 * Description: 船舶信息表
 *
 * @author sparking 2017-11-08 下午4:06:08
 *
 */
@Entity
@Table(name = "tab_ship")
public class Ship implements Entityable {

	private static final long serialVersionUID = 1L;

	private Integer id; // 编号
	private String shipName; // 船名
	private String shipEnName; // 船英文名
	private String imoNo; // IMO 编号
	private Date buildDate; // 建造时间
	private Float length; // 船长（单位：米）
	private Float breadth; // 船宽 （单位：米）
	private String mouldedDepth; // 型深 （单位：米）
	private Integer cabinNum; // 船舱数据
	private String hatch; // 舱口

	private Boolean wireCable; // 是否钢丝缆
	private Integer wireNum; // 缆绳根数
	private String specialCabinType; // 特殊舱型

	private String createUser;
	private Date createTime;
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
	public Float getLength() {
		return length;
	}

	public void setLength(Float length) {
		this.length = length;
	}

	@Column(name = "breadth")
	public Float getBreadth() {
		return breadth;
	}

	public void setBreadth(Float breadth) {
		this.breadth = breadth;
	}

	@Column(name = "cabin_num")
	public Integer getCabinNum() {
		return cabinNum;
	}

	public void setCabinNum(Integer cabinNum) {
		this.cabinNum = cabinNum;
	}

	@Column(name = "create_user")
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Column(name = "create_time")
	// @Temporal(TemporalType.TIMESTAMP)
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
	// @Temporal(TemporalType.TIMESTAMP)
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

	@Column(name = "moulded_depth")
	public String getMouldedDepth() {
		return mouldedDepth;
	}

	public void setMouldedDepth(String mouldedDepth) {
		this.mouldedDepth = mouldedDepth;
	}

	@Column(name = "hatch")
	public String getHatch() {
		return hatch;
	}

	public void setHatch(String hatch) {
		this.hatch = hatch;
	}

	@Column(name = "wire_cable")
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

	@Column(name = "special_cabin_type")
	public String getSpecialCabinType() {
		return specialCabinType;
	}

	public void setSpecialCabinType(String specialCabinType) {
		this.specialCabinType = specialCabinType;
	}

}

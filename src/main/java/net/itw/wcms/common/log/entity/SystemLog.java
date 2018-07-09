package net.itw.wcms.common.log.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import net.itw.wcms.toolkit.hibernate.Entityable;

/**
 * 
 * Description:系统日志
 * 
 * @author Michael 7 Jul 2018 18:03:43
 */
@Table(name = "X27_LOGS")
@Entity
public class SystemLog implements Entityable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer bussType;
	private String bussTypeDesc;
	private Long operateType;
	private String operateTypeDesc;
	private Integer operateResult;
	private String moudleCode;
	private String moudleName;
	private String inputUserId;
	private String requestIp;
	private String requestUrl;
	private String serverIp;
	private String uids;
	private String errorMessage;
	private Date operationTime;
	private String logDetails;

	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "Buss_Type")
	public Integer getBussType() {
		return bussType;
	}

	public void setBussType(Integer bussType) {
		this.bussType = bussType;
	}

	@Column(name = "Buss_Type_Desc")
	public String getBussTypeDesc() {
		return bussTypeDesc;
	}

	public void setBussTypeDesc(String bussTypeDesc) {
		this.bussTypeDesc = bussTypeDesc;
	}

	@Column(name = "Operate_Type")
	public Long getOperateType() {
		return operateType;
	}

	public void setOperateType(Long operateType) {
		this.operateType = operateType;
	}

	@Column(name = "Operate_Type_Desc")
	public String getOperateTypeDesc() {
		return operateTypeDesc;
	}

	public void setOperateTypeDesc(String operateTypeDesc) {
		this.operateTypeDesc = operateTypeDesc;
	}

	@Column(name = "Operate_Result")
	public Integer getOperateResult() {
		return operateResult;
	}

	public void setOperateResult(Integer operateResult) {
		this.operateResult = operateResult;
	}

	@Column(name = "Moudle_Code")
	public String getMoudleCode() {
		return moudleCode;
	}

	public void setMoudleCode(String moudleCode) {
		this.moudleCode = moudleCode;
	}

	@Column(name = "Moudle_Name")
	public String getMoudleName() {
		return moudleName;
	}

	public void setMoudleName(String moudleName) {
		this.moudleName = moudleName;
	}

	@Column(name = "Input_User_Id")
	public String getInputUserId() {
		return inputUserId;
	}

	public void setInputUserId(String inputUserId) {
		this.inputUserId = inputUserId;
	}

	@Column(name = "Request_Ip")
	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	@Column(name = "Request_Url")
	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	@Column(name = "Server_Ip")
	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getUids() {
		return uids;
	}

	public void setUids(String uids) {
		this.uids = uids;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	@Column(name = "Error_Message")
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Column(name = "Operation_Time", columnDefinition = "datetime")
	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	@Column(name = "Log_Details", columnDefinition = "text")
	public String getLogDetails() {
		return logDetails;
	}

	public void setLogDetails(String logDetails) {
		this.logDetails = logDetails;
	}

}

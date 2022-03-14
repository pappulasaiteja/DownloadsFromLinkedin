package com.aldrich.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@SuppressWarnings("unqualified-field-access")
public class CrawlingStatus {
	@Id
	private String Id;

	@Field("server_name")
	private String serverName;

	@Field("server_ip_address")
	private String serverIPAddress;

	@Field("fk_batch_id")
	private Long fkBatchId;

	@Field("fk_service_id")
	private Long fkServiceId;

	@Field("fk_company_id")
	private Long fkCompanyId;

	@Field("status")
	private String status;

	@Field("activity_datetime")
	private Date activityDateTime;

	@Field("activity_datetime_temp")
	private Date activityDateTimeTemp;

	@Field("activity_date")
	private String activityDate;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerIPAddress() {
		return serverIPAddress;
	}

	public void setServerIPAddress(String serverIPAddress) {
		this.serverIPAddress = serverIPAddress;
	}

	public Long getFkBatchId() {
		return fkBatchId;
	}

	public void setFkBatchId(Long fkBatchId) {
		this.fkBatchId = fkBatchId;
	}

	public Long getFkServiceId() {
		return fkServiceId;
	}

	public void setFkServiceId(Long fkServiceId) {
		this.fkServiceId = fkServiceId;
	}

	public Long getFkCompanyId() {
		return fkCompanyId;
	}

	public void setFkCompanyId(Long fkCompanyId) {
		this.fkCompanyId = fkCompanyId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getActivityDateTime() {
		return activityDateTime;
	}

	public void setActivityDateTime(Date activityDateTime) {
		this.activityDateTime = activityDateTime;
	}

	public Date getActivityDateTimeTemp() {
		return activityDateTimeTemp;
	}

	public void setActivityDateTimeTemp(Date activityDateTimeTemp) {
		this.activityDateTimeTemp = activityDateTimeTemp;
	}

	public String getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(String activityDate) {
		this.activityDate = activityDate;
	}

}

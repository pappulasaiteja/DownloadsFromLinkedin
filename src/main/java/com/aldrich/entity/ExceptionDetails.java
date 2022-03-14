package com.aldrich.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class ExceptionDetails {
	@Id
	private String Id;

	@Field("fk_company_id")
	private Long fkCompanyId;

	@Field("fk_service_id")
	private Long fkServiceId;

	@Field("method_name")
	private String methodName;

	@Field("line_number")
	private Long lineNumber;

	@Field("exception_message")
	private String exceptionMessage;

	@Field("notes")
	private String notes;
	
	@Field("exception_code")
	private Integer exceptionCode;


	@Field("activity_datetime")
	private Date activityDateTime;

	@Field("activity_datetime_temp")
	private Date activityDateTimeTemp;

	@Field("activity_date")
	private String activityDate;
	
	public Integer getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(Integer exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public String getId() {
		return this.Id;
	}

	public void setId(String id) {
		this.Id = id;
	}

	public Long getFkCompanyId() {
		return this.fkCompanyId;
	}

	public void setFkCompanyId(Long fkCompanyId) {
		this.fkCompanyId = fkCompanyId;
	}

	public Long getFkServiceId() {
		return this.fkServiceId;
	}

	public void setFkServiceId(Long fkServiceId) {
		this.fkServiceId = fkServiceId;
	}

	public String getMethodName() {
		return this.methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Long getLineNumber() {
		return this.lineNumber;
	}

	public void setLineNumber(Long lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getExceptionMessage() {
		return this.exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public Date getActivityDateTime() {
		return this.activityDateTime;
	}

	public void setActivityDateTime(Date activityDateTime) {
		this.activityDateTime = activityDateTime;
	}

	public Date getActivityDateTimeTemp() {
		return this.activityDateTimeTemp;
	}

	public void setActivityDateTimeTemp(Date activityDateTimeTemp) {
		this.activityDateTimeTemp = activityDateTimeTemp;
	}

	public String getActivityDate() {
		return this.activityDate;
	}

	public void setActivityDate(String activityDate) {
		this.activityDate = activityDate;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}

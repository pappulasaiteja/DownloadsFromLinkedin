package com.aldrich.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import com.aldrich.util.PASEConstants;

public class CompanyGrowthInfo {

	@Id
	private String Id;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	@Field("fk_company_id")
	private long companyId;

	@Field("employee_count")
	private long employeeCount;

	@Field("month")
	private int month;

	@Field("year")
	private int year;

	@Field("day")
	private int day;

	@Field("fk_source_id")
	private long sourceId = PASEConstants.LINKEDIN_SOURCE_ID;

	@Field("activity_datetime")
	private Date activity_datetime;

	@Field("activity_datetime_temp")
	private Date activity_datetime_temp;

	@Field("activity_date")
	private String activity_date;

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getEmployeeCount() {
		return employeeCount;
	}

	public void setEmployeeCount(long employeeCount) {
		this.employeeCount = employeeCount;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public long getSourceId() {
		return sourceId;
	}

	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}

	public Date getActivity_datetime() {
		return activity_datetime;
	}

	public void setActivity_datetime(Date activity_datetime) {
		this.activity_datetime = activity_datetime;
	}

	public Date getActivity_datetime_temp() {
		return activity_datetime_temp;
	}

	public void setActivity_datetime_temp(Date activity_datetime_temp) {
		this.activity_datetime_temp = activity_datetime_temp;
	}

	public String getActivity_date() {
		return activity_date;
	}

	public void setActivity_date(String activity_date) {
		this.activity_date = activity_date;
	}

}

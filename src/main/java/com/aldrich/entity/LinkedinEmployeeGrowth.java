package com.aldrich.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;

public class LinkedinEmployeeGrowth 
{
	@Id
	@Getter
	@Setter
	private String Id;

	
	@Field("fk_company_id")
	@Getter
	@Setter
	private long companyId;

	@Field("employee_count")
	@Getter
	@Setter
	private long employeeCount;

	@Field("six_months_change")
	@Getter
	@Setter
	private int sixMonthsChange;

	@Field("one_year_change")
	@Getter
	@Setter
	private int oneYearChange;

	@Field("two_year_change")
	@Getter
	@Setter
	private int twoYearChange;
	
	@Field("month")
	@Getter
	@Setter
	private int month;

	@Field("year")
	@Getter
	@Setter
	private int year;

	@Field("day")
	@Getter
	@Setter
	private int day;

	@Field("fk_source_id")
	@Getter
	@Setter
	private long fkSourceId;

	@Field("activity_datetime")
	@Getter
	@Setter
	private Date activity_datetime;

	@Field("activity_datetime_temp")
	@Getter
	@Setter
	private Date activity_datetime_temp;

	@Field("activity_date")
	@Getter
	@Setter
	private String activity_date;

}

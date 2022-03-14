package com.aldrich.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import com.aldrich.util.PASEConstants;

import lombok.Getter;
import lombok.Setter;

public class JobGrowthInfo 
{
	@Id
	@Getter
	@Setter
	private String Id;

	@Field("fk_company_id")
	@Getter
	@Setter
	private long companyId;

	@Field("function_name")
	@Getter
	@Setter
	private String functionName;

	@Field("no_of_employees")
	@Getter
	@Setter
	private int NoOfEmployees;
	
	@Field("total_headcount_percentage")
	@Getter
	@Setter
	private int totalHeadCountPercentage;
	
	
	@Field("three_month_growth")
	@Getter
	@Setter
	private int threeMonthGrowth;

	@Field("six_month_growth")
	@Getter
	@Setter
	private int sixMonthGrowth;

	@Field("one_year_growth")
	@Getter
	@Setter
	private int oneYearGrowth;


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
	private long sourceId = PASEConstants.LINKEDIN_SOURCE_ID;

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

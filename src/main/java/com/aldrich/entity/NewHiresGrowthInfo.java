package com.aldrich.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import com.aldrich.util.PASEConstants;

import lombok.Getter;
import lombok.Setter;

public class NewHiresGrowthInfo 
{
	@Id
	@Getter
	@Setter
	private String Id;

	@Field("fk_company_id")
	@Getter
	@Setter
	private long companyId;

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
	
	@Field("number_of_senior_hires")
	@Getter
	@Setter
	private int numberOfSeniorHires;
	
	@Field("number_of_other_hires")
	@Getter
	@Setter
	private int numberOfOtherHires;
	
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

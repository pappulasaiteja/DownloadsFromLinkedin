package com.aldrich.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;

public class Jobs {

	@Id
	@Getter
	@Setter
	private String Id;

	

	@Field("fk_company_id")
	@Getter
	@Setter
	private long companyId;
	
	
	@Field("company_Name")
	@Getter
	@Setter
	private String companyName;

	@Field("title")
	@Getter
	@Setter
	private String jobtitle;

	@Field("type")
	@Getter
	@Setter
	private String type;

	@Field("location")
	@Getter
	@Setter
	private String location;

	@Field("city")
	@Getter
	@Setter
	private String city;

	@Field("state")
	@Getter
	@Setter
	private String state;

	@Field("country")
	@Getter
	@Setter
	private String country;

	@Field("description")
	@Getter
	@Setter
	private String description;

	@Field("url")
	@Getter
	@Setter
	private String url;

	@Field("jobkey")
	@Getter
	@Setter
	private String jobkey;

	@Field("expired")
	@Getter
	@Setter
	private String expired;

	@Field("posted_datetime")
	@Getter
	@Setter
	private Date posted_datetime;

	@Field("posted_datetime_temp")
	@Getter
	@Setter
	private Date posted_datetime_temp;

	@Field("posted_date")
	@Getter
	@Setter
	private String posted_date;

	@Field("confidence_score")
	@Getter
	@Setter
	private Double confidence_score;

	@Field("server_response")
	@Getter
	@Setter
	private String server_response;

	@Field("fk_source_id")
	@Getter
	@Setter
	private Long source_id;

	@Field("fk_job_daily_status_id")
	@Getter
	@Setter
	private long job_daily_status_id;

	@Field("trigger")
	@Getter
	@Setter
	private String trigger;

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

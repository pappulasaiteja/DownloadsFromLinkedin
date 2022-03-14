package com.aldrich.entity;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;

public class EmployeeDetails 
{
	@Setter
	@Getter
	@Field("emp_id")
	private Long empId;
	@Setter
	@Getter
	@Field("uuid")
	private String uuid;
	@Setter
	@Getter
	@Field("permalink")
	private String permalink;
	@Setter
	@Getter
	@Field("first_name")
	private String firstName;
	@Setter
	@Getter
	@Field("last_name")
	private String lastName;
	@Setter
	@Getter
	@Field("full_name")
	private String fullName;
	@Setter
	@Getter
	@Field("gender")
	private String gender;
	@Setter
	@Getter
	@Field("also_known_as")
	private String alsoKnownAs;
	@Setter
	@Getter
	@Field("bio")
	private String bio;
	@Setter
	@Getter
	@Field("profile_image_url")
	private String profileImageUrl;
	@Setter
	@Getter
	@Field("born_on")
	private Date bornOn;
	@Setter
	@Getter
	@Field("died_on")
	private Date diedOn;

	@Getter
	@Setter
	@Field("location")
	private String location;

	@Getter
	@Setter
	@Field("city")
	private String city;

	@Getter
	@Setter
	@Field("state")
	private String state;

	@Getter
	@Setter
	@Field("country")
	private String country;

	@Setter
	@Getter
	@Field("role_investor")
	private String roleInvestor;

	@Setter
	@Getter
	@Field("created_at")
	private Date createdAt;

	@Setter
	@Getter
	@Field("updated_at")
	private Date updatedAt;

	@Setter
	@Getter
	@Field("team_type")
	private String teamType;

	@Getter
	@Setter
	@Field("ref_dup_emp_id")
	private long refDupEmpId;

	@Getter
	@Setter
	@Field("fk_source_id")
	private long fkSourceId;

	@Getter
	@Setter
	@Field("connection_count")
	private long connectionCount;

	@Getter
	@Setter
	@Field("rank")
	private long rank;

	@Getter
	@Setter
	@Field("score")
	private Double score;

	@Setter
	@Getter
	@Field("activity_datetime")
	private Date activityDateTime;

	@Setter
	@Getter
	@Field("activity_datetime_temp")
	private Date activityDateTimeTemp;

	@Setter
	@Getter
	@Field("activity_date")
	private String activityDate;

}

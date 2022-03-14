package com.aldrich.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "salesforce_leads_info")
public class NavatarLead {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter
	@Getter
	@Column(name = "ID")
	private long id;

	@Setter
	@Getter
	@Column(name = "pase_id")
	private Integer paseId;
	
	@Setter
	@Getter
	@Column(name = "institution_Owner")
	private String iniOwner;

	@Setter
	@Getter
	@Column(name = "legal_Name")
	private String legalName;

	@Setter
	@Getter
	@Column(name = "website")
	private String website;

	@Setter
	@Getter
	@Column(name = "domain")
	private String domain;

	@Setter
	@Getter
	@Column(name = "deal_stage_code")
	private String dealStage;

	@Setter
	@Getter
	@Column(name = "deal_stage_info")
	private String dealStageDesc;	

	@Setter
	@Getter
	@Column(name = "created_at")
	private Date createdAt;
	
	@Setter
	@Getter
	@Column(name = "updated_at")
	private Date updatedAt;
	
	@Setter
	@Getter
	@Column(name = "salesforce_link")
	private String salesforceLink;
}


package com.aldrich.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="company_type_mapping")
public class CompanyTypeMapping {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Setter
	@Getter
	@Column(name="id")
	private Long id;
	
	@Setter
	@Getter
	@Column(name="fk_company_id")
	private Long fkCompanyId;
	
	@Setter
	@Getter
	@Column(name="fk_company_type_id")
	private Long fkCompanyTypeId;
	
	@Setter
	@Getter
	@Column(name="activity_datetime")
	private Date activityDatetime;
	
	@Setter
	@Getter
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_company_id",referencedColumnName="id",insertable=false,updatable=false)
	private Company company;

}

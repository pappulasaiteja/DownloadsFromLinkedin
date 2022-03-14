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
@Table(name="competitior")
public class Competitor {
	
	
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
	@Column(name="fk_competitior_company_id")
	private Long fkCompetitiorCompanyId;
	
	@Setter
	@Getter
	@Column(name="fk_source_id")
	private Long fkSourceId;
	
	@Setter
	@Getter
	@Column(name="confidence_score")
	private Float confidenceScore;
	
	@Setter
	@Getter
	@Column(name="activity_datetime")
	private Date activityDatetime;
	

	@Setter
	@Getter
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_source_id",referencedColumnName="id",insertable=false,updatable=false)
	private CompanyTypeMapping CompanyTypeMapping;
	
}

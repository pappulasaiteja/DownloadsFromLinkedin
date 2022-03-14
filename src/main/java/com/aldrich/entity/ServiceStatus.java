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
@Table(name = "service_status_info")
public class ServiceStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter
	@Getter
	@Column(name = "id")
	private Long id;

	@Setter
	@Getter
	@Column(name = "server_name")
	private String serverName;

	@Setter
	@Getter
	@Column(name = "server_ip_address")
	private String serverIPAddress;

	@Setter
	@Getter
	@Column(name = "fk_batch_id")
	private Long fkBatchId;

	@Setter
	@Getter
	@Column(name = "fk_service_id")
	private Long fkServiceId;

	@Setter
	@Getter
	@Column(name = "start_datetime")
	private Date startDateTime;

	@Setter
	@Getter
	@Column(name = "end_datetime")
	private Date endDateTime;

	@Setter
	@Getter
	@Column(name = "duration")
	private String duration;

	@Setter
	@Getter
	@Column(name = "status")
	private String status;

	@Setter
	@Getter
	@Column(name = "activity_datetime")
	private Date activity_Datetime;
}

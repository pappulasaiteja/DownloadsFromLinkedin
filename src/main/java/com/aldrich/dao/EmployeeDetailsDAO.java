package com.aldrich.dao;

import java.util.List;

import com.aldrich.entity.EmployeeDetails;



public interface EmployeeDetailsDAO {
	public void save(EmployeeDetails employeeDetails);

	public List<EmployeeDetails> checkForExistance(String cbPermalink);

	public Long getId();

}

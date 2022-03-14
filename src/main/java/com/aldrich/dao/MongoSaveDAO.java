package com.aldrich.dao;

import com.aldrich.entity.EmployeeDetails;
import com.aldrich.entity.Jobs;

public interface MongoSaveDAO 
{
	public void save(EmployeeDetails employeeDetails);
	
	public void saveJobs(Jobs employeeDetails);
	
	public Jobs checkExistence(Jobs jobs);

}

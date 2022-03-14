package com.aldrich.test;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aldrich.config.BeanConfigurator;
import com.aldrich.dao.MongoSaveDAO;
import com.aldrich.entity.EmployeeDetails;
import com.aldrich.service.LinkedinDecisionMakersService;
import com.aldrich.service.LinkedinEmployeeService;
import com.aldrich.service.LinkedinInsightsService;
import com.aldrich.service.LinkedinJobsService;
import com.aldrich.service.LinkedinService;
import com.aldrich.service.TracxnService;
import com.aldrich.service.impl.LinkedinEmployeeServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=BeanConfigurator.class)
//@Ignore
public class LinkedinTest 
{
	
	@Autowired
	LinkedinService LinkedinService;
	
	@Autowired
	LinkedinEmployeeServiceImpl linkedinEmployeeServiceImpl;
	
	@Autowired
	LinkedinJobsService linkedinJobsService;
	
	@Autowired
	LinkedinEmployeeService linkedinEmployeeService;
	
	@Autowired
	TracxnService tracxnService;
	
	@Autowired
	MongoSaveDAO MongoSaveDAO;

	//@Ignore
	@Test
	public void runServiceTest()
	{
		try
		{
			linkedinEmployeeServiceImpl.runService();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	

	@Ignore
	@Test
	public void runLinkedinServiceTest()
	{
		try
		{
			linkedinJobsService.runService();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
		@Ignore
		@Test
		public void runService()
		{
			try
			{
				linkedinEmployeeService.runService();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		
		@Ignore
		@Test
		public void runServiceMongo()
		{
			try
			{
				EmployeeDetails employeeDetails=new EmployeeDetails();
				employeeDetails.setFirstName("Ashok");
				employeeDetails.setLastName("Kumar");
				this.MongoSaveDAO.save(employeeDetails);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
}

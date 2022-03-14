package com.aldrich.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.aldrich.dao.MongoSaveDAO;
import com.aldrich.entity.EmployeeDetails;
import com.aldrich.entity.Jobs;

@Repository
@Qualifier("MongoSaveDAO")
public class MongoSaveDAOImpl implements MongoSaveDAO 
{
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public void save(EmployeeDetails employeeDetails) 
	{
		this.mongoTemplate.insert(employeeDetails,"employee_info");
		
		// TODO Auto-generated method stub

	}
	
	
	@Override
	public void saveJobs(Jobs employeeDetails) 
	{
		this.mongoTemplate.insert(employeeDetails,"jobs_info2");
		
		// TODO Auto-generated method stub

	}


	@Override
	public Jobs checkExistence(Jobs jobs) 
	{
		Criteria criteria = new Criteria().andOperator(
				Criteria.where("fk_company_id").is(jobs.getCompanyId()),
				Criteria.where("title").is(jobs.getJobtitle()),
				Criteria.where("url").is(jobs.getUrl()));

		Query query = new Query(criteria);

		return this.mongoTemplate.findOne(query, Jobs.class,"jobs_info2");
	}

}

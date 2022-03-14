package com.aldrich.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.aldrich.dao.LinkedinInsightsDAO;
import com.aldrich.entity.CompanyGrowthInfo;
import com.aldrich.entity.FunctionGrowthInfo;
import com.aldrich.entity.JobGrowthInfo;
import com.aldrich.entity.LinkedinEmployeeGrowth;
import com.aldrich.entity.NewHiresGrowthInfo;
@Repository
@Qualifier("LinkedinInsightsDAO")
public class LinkedinInsightsDAOImpl implements LinkedinInsightsDAO
{
	
	@SuppressWarnings("nls")
	private static final String EMPLOYEE_GROWTH_FROM_LINKEDIN_INSIGHTS = "employee_growth_from_linkedin";
	private static final String LINKEDIN_PREMIUMINSIGHTS_COMPANY_GROWTH_DATA_COLLECTION = "company_growth_info";
	private static final String LINKEDIN_PREMIUMINSIGHTS_FUNCTION_GROWTH_DATA_COLLECTION="function_growth_info";
	private static final String LINKEDIN_PREMIUMINSIGHTS_NEW_HIRES_GROWTH_DATA_COLLECTION="new_hires_growth_info";
	private static final String LINKEDIN_PREMIUMINSIGHTS_JOBS_GROWTH_DATA_COLLECTION="job_growth_info";


	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public boolean save(LinkedinEmployeeGrowth employeeGrowth) 
	{
		boolean flag=false;
		try
		{
			this.mongoTemplate.insert(employeeGrowth, EMPLOYEE_GROWTH_FROM_LINKEDIN_INSIGHTS);
			flag=true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}


	@Override
	public boolean saveCompanyGrowthInfo(CompanyGrowthInfo employeeGrowth) 
	{
		boolean flag=false;
		try
		{
			this.mongoTemplate.insert(employeeGrowth, LINKEDIN_PREMIUMINSIGHTS_COMPANY_GROWTH_DATA_COLLECTION);
			flag=true;
		}
		catch(Exception e)
		{
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}


	@Override
	public boolean saveFunctionGrowthInfo(FunctionGrowthInfo employeeGrowth) 
	{
		boolean flag=false;
		try
		{
			this.mongoTemplate.insert(employeeGrowth, LINKEDIN_PREMIUMINSIGHTS_FUNCTION_GROWTH_DATA_COLLECTION);
			flag=true;
		}
		catch(Exception e)
		{
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}



	@SuppressWarnings("nls")
	@Override
	public LinkedinEmployeeGrowth checkEmployeeGrowthInfoExistance(LinkedinEmployeeGrowth companyGrowthInfo) 
	{
		Query query =null;
		try
		{
			Criteria criteria = new Criteria().andOperator(
					Criteria.where("fk_company_id").is(companyGrowthInfo.getCompanyId()),
					Criteria.where("month").is(companyGrowthInfo.getMonth()),
					Criteria.where("year").is(companyGrowthInfo.getYear()));

			query = new Query(criteria);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return this.mongoTemplate.findOne(query, LinkedinEmployeeGrowth.class,
				EMPLOYEE_GROWTH_FROM_LINKEDIN_INSIGHTS);
	}


	@Override
	public CompanyGrowthInfo checkCompanyGrowthInfoExistance(CompanyGrowthInfo companyGrowthInfo) {
		Criteria criteria = new Criteria().andOperator(
				Criteria.where("fk_company_id").is(companyGrowthInfo.getCompanyId()),
				Criteria.where("month").is(companyGrowthInfo.getMonth()),
				Criteria.where("year").is(companyGrowthInfo.getYear()));

		Query query = new Query(criteria);

		return this.mongoTemplate.findOne(query, CompanyGrowthInfo.class,
				LINKEDIN_PREMIUMINSIGHTS_COMPANY_GROWTH_DATA_COLLECTION);
	}

	@Override
	public FunctionGrowthInfo checkFunctionGrowthInfoExistance(FunctionGrowthInfo functionGrowthInfo) 
	{
		FunctionGrowthInfo funcGrowthInfo=null;
		try
		{
			Criteria criteria = new Criteria().andOperator(
					Criteria.where("fk_company_id").is(functionGrowthInfo.getCompanyId()),
					Criteria.where("month").is(functionGrowthInfo.getMonth()),
					Criteria.where("function_name").is(functionGrowthInfo.getFunctionName()),
					Criteria.where("year").is(functionGrowthInfo.getYear()));

			Query query = new Query(criteria);

			funcGrowthInfo= this.mongoTemplate.findOne(query, FunctionGrowthInfo.class,
					LINKEDIN_PREMIUMINSIGHTS_FUNCTION_GROWTH_DATA_COLLECTION);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return funcGrowthInfo;
	}

	@Override
	public NewHiresGrowthInfo checkNewHiresGrowthInfoExistance(NewHiresGrowthInfo newHiresGrowthInfo) 
	{
		NewHiresGrowthInfo hireInfo=null;
		try
		{
			Criteria criteria = new Criteria().andOperator(
					Criteria.where("fk_company_id").is(newHiresGrowthInfo.getCompanyId()),
					Criteria.where("month").is(newHiresGrowthInfo.getMonth()),
					Criteria.where("year").is(newHiresGrowthInfo.getYear()));

			Query query = new Query(criteria);

			hireInfo=this.mongoTemplate.findOne(query, NewHiresGrowthInfo.class,
					LINKEDIN_PREMIUMINSIGHTS_NEW_HIRES_GROWTH_DATA_COLLECTION);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return hireInfo;

	}

	@Override
	public boolean saveNewHireGrowthInfo(NewHiresGrowthInfo newHireGrowthInfo) 
	{
		boolean flag=false;
		try
		{
			this.mongoTemplate.insert(newHireGrowthInfo, LINKEDIN_PREMIUMINSIGHTS_NEW_HIRES_GROWTH_DATA_COLLECTION);
			flag=true;
		}
		catch(Exception e)
		{
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public JobGrowthInfo checkJobInfoExistance(JobGrowthInfo jobGrowthInfo) 
	{
		Criteria criteria = new Criteria().andOperator(
				Criteria.where("fk_company_id").is(jobGrowthInfo.getCompanyId()),
				Criteria.where("month").is(jobGrowthInfo.getMonth()),
				Criteria.where("function_name").is(jobGrowthInfo.getFunctionName()),
				Criteria.where("year").is(jobGrowthInfo.getYear()));

		Query query = new Query(criteria);

		return this.mongoTemplate.findOne(query, JobGrowthInfo.class,
				LINKEDIN_PREMIUMINSIGHTS_JOBS_GROWTH_DATA_COLLECTION);
	}

	@Override
	public boolean saveJobInfo(JobGrowthInfo jobGrowthInfo) {
		boolean flag=false;
		try
		{
			this.mongoTemplate.insert(jobGrowthInfo, LINKEDIN_PREMIUMINSIGHTS_JOBS_GROWTH_DATA_COLLECTION);
			flag=true;
		}
		catch(Exception e)
		{
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<CompanyGrowthInfo> getEmployeeCount(Long companyId) 
	{
		Query query = null;
		List<CompanyGrowthInfo> companyGrowthInfo=null;
		try
		{
			Criteria criteria = new Criteria().andOperator(
					Criteria.where("fk_company_id").is(companyId));

			query = new Query(criteria);
			companyGrowthInfo=this.mongoTemplate.find(query, CompanyGrowthInfo.class,
					LINKEDIN_PREMIUMINSIGHTS_COMPANY_GROWTH_DATA_COLLECTION);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return companyGrowthInfo;
	}
	
	
	@Override
	public boolean updateEmployeeGrowth(LinkedinEmployeeGrowth employeeGrowth) 
	{
		try
		{
			Criteria criteria = new Criteria().andOperator(
					Criteria.where("fk_company_id").is(employeeGrowth.getCompanyId()),
					Criteria.where("month").is(employeeGrowth.getMonth()),
					Criteria.where("year").is(employeeGrowth.getYear()));
			Query query = new Query(criteria);
			Update update = new Update();
			update.set("employee_count", employeeGrowth.getEmployeeCount());
			update.set("six_months_change", employeeGrowth.getSixMonthsChange());
			update.set("one_year_change", employeeGrowth.getOneYearChange());
			update.set("two_year_change", employeeGrowth.getTwoYearChange());
			update.set("day", employeeGrowth.getDay());
			update.set("activity_date", employeeGrowth.getActivity_date());
			this.mongoTemplate.updateMulti(query, update, EMPLOYEE_GROWTH_FROM_LINKEDIN_INSIGHTS);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}


	@Override
	public boolean updateEmployeeCount(CompanyGrowthInfo companyGrowthInfo) 
	{
		try
		{
			Criteria criteria = new Criteria().andOperator(
					Criteria.where("fk_company_id").is(companyGrowthInfo.getCompanyId()),
					Criteria.where("month").is(companyGrowthInfo.getMonth()),
					Criteria.where("year").is(companyGrowthInfo.getYear()));
			Query query = new Query(criteria);
			Update update = new Update();
			update.set("employee_count", companyGrowthInfo.getEmployeeCount());
			
			update.set("day", companyGrowthInfo.getDay());
			update.set("activity_date", companyGrowthInfo.getActivity_date());
			this.mongoTemplate.updateMulti(query, update, LINKEDIN_PREMIUMINSIGHTS_COMPANY_GROWTH_DATA_COLLECTION);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	

}

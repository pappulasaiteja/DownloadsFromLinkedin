package com.aldrich.dao;

import java.util.List;

import com.aldrich.entity.CompanyGrowthInfo;
import com.aldrich.entity.FunctionGrowthInfo;
import com.aldrich.entity.JobGrowthInfo;
import com.aldrich.entity.LinkedinEmployeeGrowth;
import com.aldrich.entity.NewHiresGrowthInfo;

public interface LinkedinInsightsDAO 
{
	public boolean save(LinkedinEmployeeGrowth employeeGrowth);
	
	public LinkedinEmployeeGrowth checkEmployeeGrowthInfoExistance(LinkedinEmployeeGrowth companyGrowthInfo);
	
	public CompanyGrowthInfo checkCompanyGrowthInfoExistance(CompanyGrowthInfo companyGrowthInfo);
	
	public boolean saveCompanyGrowthInfo(CompanyGrowthInfo employeeGrowth);
	
	public FunctionGrowthInfo checkFunctionGrowthInfoExistance(FunctionGrowthInfo functionGrowthInfo);
	
	public boolean saveFunctionGrowthInfo(FunctionGrowthInfo employeeGrowth);
	
	public NewHiresGrowthInfo checkNewHiresGrowthInfoExistance(NewHiresGrowthInfo newHiresGrowthInfo);
	
	public boolean saveNewHireGrowthInfo(NewHiresGrowthInfo newHireGrowthInfo);
	
	public JobGrowthInfo checkJobInfoExistance(JobGrowthInfo jobGrowthInfo);
	
	public boolean saveJobInfo(JobGrowthInfo jobGrowthInfo);
	
	public  List<CompanyGrowthInfo> getEmployeeCount(Long companyId);
	
	public boolean updateEmployeeGrowth(LinkedinEmployeeGrowth employeeGrowth);
	
	public boolean updateEmployeeCount(CompanyGrowthInfo companyGrowthInfo);

}

package com.aldrich.service;

import java.io.File;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.aldrich.entity.CompanyGrowthInfo;
import com.aldrich.entity.FunctionGrowthInfo;
import com.aldrich.entity.JobGrowthInfo;
import com.aldrich.entity.LinkedinEmployeeGrowth;
import com.aldrich.entity.NewHiresGrowthInfo;

public interface LinkedinInsightsService 
{
	public boolean runService();
	
	public Document parseHtmlFile(String response,Long companyId);
	
	public boolean getInsigthsEmployeeGrowth(Element element,long companyId);
	
	public boolean saveEmployeeGrowth(LinkedinEmployeeGrowth employeeGrowth);
	
	public List<CompanyGrowthInfo> getCompanyGrowthInfo(Element element,long companyId);
	
	public boolean saveCompanyGrowth(List<CompanyGrowthInfo> companyGrowthInfo);
	
	public CompanyGrowthInfo checkCompanyGrowthInfoExistance(CompanyGrowthInfo companyGrowthInfo, long companyId);
	
	public boolean getEmployeeCount(Document document,long companyId);
	
	public List<FunctionGrowthInfo> getEmployeeFunctionHeadCount(Element element,long companyId);
	
	public boolean saveFunctionGrowth(List<FunctionGrowthInfo> functionGrowthInfo);
	
	public FunctionGrowthInfo checkFunctionGrowthInfoExistance(FunctionGrowthInfo functionGrowthInfo, long companyId);
	
	public List<NewHiresGrowthInfo> getNewHireGrowthInfo(Element element,long companyId);
	
	public boolean saveNewHireGrowth(List<NewHiresGrowthInfo> newHiresGrowthInfo);
	
	public NewHiresGrowthInfo checkNewHireGrowthInfoExistance(NewHiresGrowthInfo newHiresGrowthInfo, long companyId);
	
	public List<JobGrowthInfo> getJobGrowthInfo(Element element,long companyId);
	
	public boolean saveJobGrowthInfo(List<JobGrowthInfo> jobGrowthInfo);
	
	public JobGrowthInfo checkJobGrowthInfoExistance(JobGrowthInfo jobGrowthInfo, long companyId);
}

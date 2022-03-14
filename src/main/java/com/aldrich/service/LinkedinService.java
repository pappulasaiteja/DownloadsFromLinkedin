package com.aldrich.service;

import java.util.List;

import org.json.JSONObject;
import org.openqa.selenium.WebDriver;

import com.aldrich.BO.LinkedinInputBO;
import com.aldrich.entity.NavatarLead;
import com.aldrich.pase.vo.CompanyDomainVO;



public interface LinkedinService 
{
	

	//......................................Selenium methods.............................................................
	public void runService();
	public List<LinkedinInputBO> fetchExceldata();
	public WebDriver setChromeDriver();
	public WebDriver loginLinkedinWebsite(WebDriver driver);
	public WebDriver getCompanyPage(WebDriver driver,NavatarLead lead);
	public WebDriver getOwlerPage(WebDriver driver,String link);
	public boolean competitorDataMapping(JSONObject resp,CompanyDomainVO vo);
	public Long getcopetitorCompanyId(JSONObject resp);
	public boolean saveCompanyTypeMappingInfo(long competitorCompanyId);
	public boolean saveCompanyProfileInfo(JSONObject resp,long competitorCompanyId,CompanyDomainVO vo);
	public long checkForCategoryDetails(String categoryName);

	
}
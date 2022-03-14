package com.aldrich.service.impl;

import java.util.List;

import org.json.JSONObject;
import org.openqa.selenium.WebDriver;

import com.aldrich.BO.LinkedinInputBO;
import com.aldrich.entity.NavatarLead;
import com.aldrich.pase.vo.CompanyDomainVO;
import com.aldrich.service.LinkedinService;

public class LinkedinTwitterTestImpl implements LinkedinService 
{
	

	@Override
	public void runService() 
	{
		
		// TODO Auto-generated method stub

	}

	@Override
	public List<LinkedinInputBO> fetchExceldata() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebDriver setChromeDriver() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebDriver loginLinkedinWebsite(WebDriver driver) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebDriver getCompanyPage(WebDriver driver, NavatarLead lead) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebDriver getOwlerPage(WebDriver driver, String link) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean competitorDataMapping(JSONObject resp, CompanyDomainVO vo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Long getcopetitorCompanyId(JSONObject resp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean saveCompanyTypeMappingInfo(long competitorCompanyId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveCompanyProfileInfo(JSONObject resp, long competitorCompanyId, CompanyDomainVO vo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long checkForCategoryDetails(String categoryName) {
		// TODO Auto-generated method stub
		return 0;
	}

}

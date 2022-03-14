package com.aldrich.service;

import java.io.File;

import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;

import com.aldrich.entity.EmployeeDetails;

public interface LinkedinDecisionMakersService 
{
	public void runService();
	public WebDriver setChromeDriver();
	public WebDriver loginLinkedinWebsite(WebDriver driver);
	public WebDriver getCompanyPage(WebDriver driver,String linkedinURL);
	public WebDriver getEmployeePage(WebDriver driver,String linkedinURL);
	public Long saveEmployeeDetails(EmployeeDetails employeeDetails);
	public Document parseHtmlFile(File file);

}

package com.aldrich.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public interface LeadFromSalesNavigatorService {

	public WebDriver setChromeDriver();

	public WebDriver getSpecificPage(WebDriver driver, String url);

	public WebDriver loginLinkedinWebsite(WebDriver driver);

	public void scrollBottom(WebDriver driver);

	public boolean isElementPresent(WebDriver driver, By selector);

	public String runService();

	//public void processCompanyPages(WebDriver companyPage);

	public void processCompanyPages(WebDriver companyPage,String range);

}

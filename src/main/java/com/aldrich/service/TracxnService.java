package com.aldrich.service;

import org.openqa.selenium.WebDriver;

public interface TracxnService 
{
	public void runService();
	public WebDriver setChromeDriver();
	public WebDriver loginLinkedinWebsite(WebDriver driver);
	public WebDriver getCompanyPage(WebDriver driver);
	//public WebDriver getOwlerPage(WebDriver driver,String link);

}

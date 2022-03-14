package com.aldrich.service;

import org.openqa.selenium.WebDriver;

public interface LinkedinEmployeeService 
{
	//......................................Selenium methods.............................................................
		public void runService();
		public WebDriver setChromeDriver();
		public WebDriver getCompanyPage(WebDriver driver,String linkedinURL);
		WebDriver loginLinkedinWebsite(WebDriver driver);

		

}

package com.aldrich.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aldrich.BO.LinkedinInputBO;
import com.aldrich.dao.SocialMediaLinkDAO;
import com.aldrich.service.LinkedinEmployeeService;


@Service
public class LinkedinEmployeeServiceImpl implements LinkedinEmployeeService 
{

	@Autowired
	SocialMediaLinkDAO socialMediaLinkDAO;


	@Override
	public void runService() 
	{
		String resp="";
		//BufferedWriter  bufferedWriter =null;
		WebDriver chromeDriver=null;
		WebDriver loginDriver=null;
		WebDriver companyPage=null;
		int scrollLimit=5;
		List<LinkedinInputBO> linkedinBOList=null;
		//File filePath=null;
		try
		{
			linkedinBOList=this.socialMediaLinkDAO.getLinkedinLinks();
			if(linkedinBOList!=null && !linkedinBOList.isEmpty())
			{
				chromeDriver=this.setChromeDriver();
				if(chromeDriver!=null)
				{
					loginDriver=this.loginLinkedinWebsite(chromeDriver);
					if(loginDriver!=null)
					{
						for(LinkedinInputBO linkedinBO:linkedinBOList)
						{
							try
							{
								companyPage=this.getCompanyPage(loginDriver,linkedinBO.getLink());
								if(companyPage!=null)
								{
									WebElement element = companyPage.findElement(By.cssSelector("dd.org-page-details__definition-text>a>span"));
									Actions actions = new Actions(companyPage);
									System.out.println(element.getText());
									actions.moveToElement(element).click().build().perform();	
								}

							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}


	@Override
	public WebDriver setChromeDriver() 
	{	
		WebDriver driver = null;
		ChromeOptions options =null;
		try
		{
			options = new ChromeOptions();
			/*options.addArguments("--headless");
			options.addArguments("--disable-gpu");
			options.addArguments("--allow-insecure-localhost");
			options.addArguments("--allow-running-insecure-content");
			options.addArguments("--ignore-certificate-errors");
			options.addArguments("--no-sandbox");*/
			//options.addArguments("--start-maximized");
			options.addArguments("--window-size=2000,6000");
			/*options.setCapability("acceptSslCerts", true);
			options.setCapability("acceptInsecureCerts", true);	*/
			System.setProperty("webdriver.chrome.driver","C:\\sai\\chrome driver\\driver\\chromedriver.exe");
			driver = new ChromeDriver(options);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return driver;
	}

	@Override
	public WebDriver loginLinkedinWebsite(WebDriver driver) 
	{
		String loginUrl = "https://www.linkedin.com/login";				
		try
		{
			driver.get(loginUrl);
			driver.findElement(By.cssSelector("input[name='session_key']")).sendKeys("omprakashornold@gmail.com");	
			driver.findElement(By.cssSelector("input[name='session_password']")).sendKeys("Cash789@@");			
			driver.findElement(By.cssSelector("button[aria-label='Sign in']")).click();	
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);   
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return driver;
	}



	@Override
	public WebDriver getCompanyPage(WebDriver driver,String linkedinURL)
	{
		//String page="";
		String loginUrl = linkedinURL+"/about";			
		try
		{
			driver.navigate().to(loginUrl);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return driver;
	}




}

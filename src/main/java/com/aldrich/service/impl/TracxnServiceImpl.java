package com.aldrich.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import com.aldrich.entity.NavatarLead;
import com.aldrich.service.TracxnService;
@Service
public class TracxnServiceImpl implements TracxnService {

	@Override
	public void runService() 
	{

		String resp="";
		BufferedWriter  bufferedWriter =null;
		WebDriver chromeDriver=null;
		WebDriver loginDriver=null;
		WebDriver companyPage=null;
		//List<NavatarLead> navatarLeadList=null;
		try
		{
			chromeDriver=this.setChromeDriver();
			if(chromeDriver!=null)
			{
				loginDriver=this.loginLinkedinWebsite(chromeDriver);
				if(loginDriver!=null)
				{
					Thread.sleep(10000);
					
					/*resp=loginDriver.getPageSource();
					File file1=new File("C:\\Users\\akumar\\Desktop\\tracxn.html");
					FileWriter writer1 = new FileWriter(file1);
					writer1.write(resp);
					writer1.close();*/
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
			//options.addArguments("--headless");
			options.addArguments("--disable-gpu");
			options.addArguments("--allow-insecure-localhost");
			options.addArguments("--allow-running-insecure-content");
			options.addArguments("--ignore-certificate-errors");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-notifications");
			//options.addArguments("--start-maximized");
			options.addArguments("--window-size=2000,6000");
			options.setCapability("acceptSslCerts", true);
			options.setCapability("acceptInsecureCerts", true);	
			System.setProperty("webdriver.chrome.driver","C:\\Users\\akumar\\Desktop\\chromedriver_win32 (3)\\chromedriver.exe");
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
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
		String loginUrl = "https://tracxn.com/a/s/query/t/companiescovered/t/all/table#%7CpracticeArea%3DGeo%2520-%2520US%2520Tech%7CfeedName%3DEnterprise%2520Tech%2520-%2520US%2CSaaS%2520-%2520US%2CHealthTech%2520-%2520US%2CFinTech%2520-%2520US%2CHiTech%2520-%2520US%2CArtificial%2520Intelligence%2520-%2520US%2CEdTech%2520-%2520US%7CnumberOfFundingRounds%5Emin%3D0%5Emax%3D0%7CcompanyStage%3DUnfunded%2CBootstrapped%2520Medium%7Ccountry%3DNorth%2520America%2CUS%2520%2526%2520Canada%2CUnited%2520States%7Csort%3Drelevance%7Corder%3DDEFAULT%7CtableKeys%3Dindex%2Cname%2Cdescription%2CtotalMoneyRaised%2Clocation%2CfoundedYear%2Cstage%2ClatestEmployeeCount%2CpracticeArea%2CfeedName%2CnumberOfFundingRounds%2Ccountry%2Ccity%2Cstate";				
		try
		{
			driver.navigate().to(loginUrl);
			/*Thread.sleep(10000);
			driver.findElement(By.cssSelector("input[class='form-control']")).sendKeys("mnazeer@aldrichcap.com");
			Thread.sleep(10000);
			driver.findElement(By.cssSelector("button[id='loginEmailButton']")).click();
			Thread.sleep(10000);
			driver.findElement(By.id("password")).sendKeys("Dteam@2019");	
			Thread.sleep(10000);
			driver.findElement(By.cssSelector("button[id='loginEmailButton']")).click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);  */ 
			Thread.sleep(10000);
			WebElement element=driver.findElement(By.xpath("//div[@class='login-container']//div[@class='form-group cssform login-container__animation-appear-done login-container__animation-enter-done']//form//input[@type='email']"));
			if(element.isDisplayed())
			{
				element.sendKeys("mnazeer@aldrichcap.com");
				WebElement element1=driver.findElement(By.xpath("//div[@class='login-container']//div[@class='form-group cssform login-container__animation-appear-done login-container__animation-enter-done']//form//button[@id='loginEmailButton']"));
				if(element1.isDisplayed())
				{
					element1.click();
					WebElement element2=driver.findElement(By.xpath("//div[@class='login-container']//input[@type='password']"));
					if(element2.isDisplayed())
					{
						element2.sendKeys("Dteam@2019");
						WebElement element3=driver.findElement(By.xpath("//div[@class='login-container']//button[@id='loginButton']"));
						if(element3.isDisplayed())
						{
							element3.click();
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return driver;

	}

	@Override
	public WebDriver getCompanyPage(WebDriver driver) 
	{


		return null;
	}

}

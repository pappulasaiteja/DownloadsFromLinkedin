package com.aldrich.service.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class LinkedinProducts {

	public static void main(String[] args) {
		try {
			WebDriver companyPage=null;
			WebDriver jobsPage=null;
			WebDriver loginDriver=null;
			WebDriver chromeDriver = null;
			String url="https://www.linkedin.com/company/mangoapps-inc/";
			String res=null;
			chromeDriver=setChromeDriver();
			if(chromeDriver!=null)
			{
				loginDriver=loginLinkedinWebsite(chromeDriver);
				if(loginDriver!=null)
				{
					if (loginDriver != null) {
						companyPage=getCompanyPage(loginDriver,url);

						Thread.sleep(5000);

						JavascriptExecutor jse = (JavascriptExecutor) companyPage;
						//Below code will scroll the web page till end
						jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");

						Thread.sleep(5000);
						res=companyPage.getPageSource();
						if(!res.equals(""))
						{		
							Writer writer;
							try {	
								writer = new FileWriter(
										"C:\\Users\\Omprakash\\Desktop\\linkedin_240\\new\\example.html");
								BufferedWriter bufferedWriter = new BufferedWriter(writer);
								bufferedWriter.write(res);
								bufferedWriter.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static WebDriver setChromeDriver() {
		WebDriver driver = null;
		ChromeOptions options=null;
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
			System.setProperty("webdriver.chrome.driver","C:\\sai\\driver\\driver_22.07\\chromedriver.exe");

			driver = new ChromeDriver(options);
			driver.manage().window();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return driver;
	}

	public static WebDriver loginLinkedinWebsite(WebDriver driver) {
		String loginUrl = "https://www.linkedin.com/login";				
		try
		{
			driver.get(loginUrl);
			driver.findElement(By.cssSelector("input[name='session_key']")).sendKeys("rachaajay83@gmail.com");	
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

	public static WebDriver getCompanyPage(WebDriver driver,String linkedinURL)
	{
		int scrollLimit = 10;
		if (linkedinURL.contains("?"))
			linkedinURL = linkedinURL.substring(0, linkedinURL.indexOf("?"));
		// String page="";
		// String loginUrl =
		// "https://www.linkedin.com/company/"+linkedinURL+"/insights/";
		//String loginUrl = "https://www.linkedin.com/company/" + linkedinURL + "/products/";
		String loginUrl = linkedinURL+"/products";
		// String loginUrl = linkedinURL + "/people/";
		try {
			driver.navigate().to(loginUrl);
			for (int i = scrollLimit; i >= 0; i--) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				// Wait to load the scrolled page
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return driver;
	}

	public static boolean isElementPresent(WebDriver driver ,By selector)
	{
		return driver.findElements(selector).size()>0;
	}

	public static WebDriver getLoginDriver() {
		WebDriver chromeDriver = null;
		WebDriver loginDriver = null;

		try {
			chromeDriver = setChromeDriver();
			if (chromeDriver != null) {
				loginDriver = loginLinkedinWebsite(chromeDriver);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginDriver;
	}

}

package com.aldrich.service.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Cruchbase {

	public static void main(String[] args) throws InterruptedException  {

		WebDriver companyPage=null;

		WebDriver chromeDriver = null;
		chromeDriver=setChromeDriver();
		WebDriver loginDriver = null;
		if(chromeDriver!=null)
		{
			loginDriver=loginLinkedinWebsite(chromeDriver);
			if(loginDriver!=null)
			{
				List<String> words=new ArrayList<String>();	


				words.add("https://www.crunchbase.com/hub/venture-debt-investors/hub_overview_investor/top?tab=top_orgs");
				for (String string : words) 
				{ 
					try {
						companyPage=getCompanyPage(chromeDriver,string);
						JavascriptExecutor jse = (JavascriptExecutor) companyPage;
						//Below code will scroll the web page till end
						jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
						for (int i = 5; i >= 0; i--) 
						{
							try
							{
								JavascriptExecutor js = (JavascriptExecutor) companyPage;
								js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

								// Wait to load the scrolled page
								Thread.sleep(1000);
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
						}
						
						try (FileWriter writer = new FileWriter(
								"C:\\sai\\satyam\\gregslist\\atlanta_19.05\\cruchbase.html", true);
								BufferedWriter bw = new BufferedWriter(writer)) {				
							bw.write(companyPage.getPageSource());
							bw.write("\n");


						} catch (IOException ex) {
							System.err.format("IOException: %s%n", ex);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
	}
	public static WebDriver getCompanyPage(WebDriver driver,String word)
	{	

		try
		{
			Thread.sleep(4000);
			driver.navigate().to(word);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return driver;
	}
	public static WebDriver loginLinkedinWebsite(WebDriver driver) 
	{
		String loginUrl = "https://www.crunchbase.com/logout";				
		try
		{
			driver.get(loginUrl);
			driver.findElement(By.cssSelector("input[type='email']")).sendKeys("mnazeer@aldrichcap.com");	
			driver.findElement(By.cssSelector("input[type='password']")).sendKeys("AcP_987654@");					
			driver.findElement(By.cssSelector("button[type='submit']")).click();	
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);   
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return driver;
	}
	public static WebDriver setChromeDriver() 
	{	
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
			System.setProperty("webdriver.chrome.driver","C:\\sai\\driver\\new_19.05\\chromedriver.exe");

			driver = new ChromeDriver(options);
			driver.manage().window();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return driver;
	}

	public static boolean isElementPresent(WebDriver driver ,By selector)
	{
		return driver.findElements(selector).size()>0;
	}

	public static void scrollBottom(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		//Below code will scroll the web page till end
		jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public static void scrollByVisibility(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		WebElement el = driver.findElement(By.linkText("About Us"));	
		jse.executeScript("arguments[0].scrollIntoView();", el);
	}

	public static void scrollByPixels(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		// Below code will scroll up the page by  900 pixel vertical	
		jse.executeScript("window.scrollBy(0,200)");
	}
}

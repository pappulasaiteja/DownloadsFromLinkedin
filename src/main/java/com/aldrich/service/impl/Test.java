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
import org.openqa.selenium.support.ui.WebDriverWait;

public class Test {


	public static void main(String[] args) throws InterruptedException  {

		WebDriver companyPage=null;

		WebDriver chromeDriver = null;
		WebDriver loginDriver = null;
		WebDriverWait wait=null;
		String res=null;

		chromeDriver=setChromeDriver();
		if(chromeDriver!=null)
		{
			
				List<String> words=new ArrayList<String>();	
		

				words.add("https://angel.co/company/ginkgo-bioworks");


				for (String string : words) 
				{ 
					


						companyPage=getCompanyPage(chromeDriver,string);
						
						

						JavascriptExecutor jse = (JavascriptExecutor) companyPage;
						//Below code will scroll the web page till end
						jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
						

						try (FileWriter writer = new FileWriter(
								"C:\\Users\\Omprakash\\Desktop\\angel\\angel1.html", true);
								BufferedWriter bw = new BufferedWriter(writer)) {				
							bw.write(companyPage.getPageSource());
							bw.write("\n");

						} catch (IOException ex) {
							System.err.format("IOException: %s%n", ex);
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
		String loginUrl = "https://www.linkedin.com/login";				
		try
		{
			driver.get(loginUrl);
			driver.findElement(By.cssSelector("input[name='session_key']")).sendKeys("sathyamputtala2005@gmail.com");	
			driver.findElement(By.cssSelector("input[name='session_password']")).sendKeys("s@tyam007");					
			driver.findElement(By.cssSelector("button[aria-label='Sign in']")).click();	
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
			System.setProperty("webdriver.chrome.driver","C:\\sai\\driver\\chromedriver.exe");

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

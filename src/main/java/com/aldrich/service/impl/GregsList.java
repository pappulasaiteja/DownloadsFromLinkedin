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

public class GregsList {
	public static void main(String[] args) throws InterruptedException  {

		WebDriver companyPage=null;

		WebDriver chromeDriver = null;
		WebDriver loginDriver = null;
		WebDriverWait wait=null;
		String res=null;
		int count=1;

		chromeDriver=setChromeDriver();
		if(chromeDriver!=null)
		{

			List<String> words=new ArrayList<String>();	


			words.add("https://gregslist.com/toronto/search/?filtered=true&paged=1");
			words.add("https://gregslist.com/toronto/search/?filtered=true&paged=2");
			words.add("https://gregslist.com/toronto/search/?filtered=true&paged=3");
		    words.add("https://gregslist.com/toronto/search/?filtered=true&paged=4");
			//words.add("https://gregslist.com/salt-lake-city/search/?filtered=true&paged=5");
			//words.add("https://gregslist.com/salt-lake-city/search/?filtered=true&paged=6");
			//words.add("https://gregslist.com/dallas/search/?filtered=true&paged=7");
			//words.add("https://gregslist.com/dallas/search/?filtered=true&paged=8");
			//words.add("https://gregslist.com/dallas/search/?filtered=true&paged=9");
			//words.add("https://gregslist.com/austin/search/?filtered=true&paged=10");
			
			for (String string : words) 
			{ 
				try {
					companyPage=getCompanyPage(chromeDriver,string);
					JavascriptExecutor jse = (JavascriptExecutor) companyPage;
					//Below code will scroll the web page till end
					jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
					

					try (FileWriter writer = new FileWriter(
							"C:\\sai\\satyam\\gregslist\\atlanta_01.02.22\\to_"+count+".html", true);
							BufferedWriter bw = new BufferedWriter(writer)) {				
						bw.write(companyPage.getPageSource());
						bw.write("\n");
						count++;

					} catch (IOException ex) {
						System.err.format("IOException: %s%n", ex);
					}
				} catch (Exception e) {
					e.printStackTrace();
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
			System.setProperty("webdriver.chrome.driver","C:\\sai\\driver\\new_11.26\\chromedriver.exe");

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

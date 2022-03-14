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

public class SalesTest {
	public static void main(String[] args) throws InterruptedException {
		WebDriver companyPage=null;
		WebDriver jobsPage=null;
		WebDriver loginDriver=null;
		WebDriver chromeDriver = null;
		String res=null;
		String companyName=null;
		chromeDriver=setChromeDriver();
		int count=1;
		int j=176;
		if(chromeDriver!=null)
		{
			loginDriver=loginLinkedinWebsite(chromeDriver);
			if(loginDriver!=null)
			{	
				List<String> words=new ArrayList<String>();	


				words.add("https://www.linkedin.com/sales/search/company?companySize=D&geoIncluded=104738515&industryIncluded=96&page=8&preserveScrollPosition=false&searchSessionId=ix444zbaSFmTZbNzKVG39w%3D%3D&spellingCorrectionEnabled=false");


				for (String string : words) 
				{ 
					companyPage=getCompanyPage(loginDriver,string);

					Thread.sleep(3000);

					By inputArea1 = By.xpath("//a[@class='ember-view result-lockup__icon-link']");
					Thread.sleep(5000);

					for (int i =300; i <=5000; i=i+300) {
						JavascriptExecutor jse = (JavascriptExecutor) companyPage;
						//Below code will scroll the web page till end
						jse.executeScript("window.scrollTo(0,"+i+")");
						Thread.sleep(1000);
					}

					List<WebElement> myElements = companyPage.findElements(inputArea1);
					List<String> list=new ArrayList<>();
					for(WebElement e : myElements) {
						System.out.println(count+". "+e.getAttribute("href"));
						list.add(e.getAttribute("href"));
						count++;
					}
					if (list!=null) {
						for (String string2 : list) {
							companyPage=getCompanyPage(loginDriver,string2);
							Thread.sleep(2000);

							JavascriptExecutor jse = (JavascriptExecutor) companyPage;
							//Below code will scroll the web page till end
							jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");

							Thread.sleep(2000);

							try (FileWriter writer = new FileWriter(
									"C:\\Users\\Omprakash\\Desktop\\sales\\"+j+".html", true);
									BufferedWriter bw = new BufferedWriter(writer)) {				
								bw.write(companyPage.getPageSource());
								bw.write("\n");

							} catch (IOException ex) {
								System.err.format("IOException: %s%n", ex);
							}
							j++;
						}
						
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

	public static WebDriver loginLinkedinWebsite(WebDriver driver) {
		String loginUrl = "https://www.linkedin.com/login";				
		try
		{
			driver.get(loginUrl);
			driver.findElement(By.cssSelector("input[name='session_key']")).sendKeys("rimam@aldrichcap.com");	
			driver.findElement(By.cssSelector("input[name='session_password']")).sendKeys("p@serock$");			
			driver.findElement(By.cssSelector("button[aria-label='Sign in']")).click();	
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);   
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

	public static WebDriver getLoginDriver() {
		WebDriver chromeDriver = null;
		WebDriver loginDriver = null;

		try {
			chromeDriver = setChromeDriver();
			if (chromeDriver != null) {
				loginDriver =loginLinkedinWebsite(chromeDriver);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginDriver;
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

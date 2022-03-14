package com.aldrich.service.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class AMPA {

	public static void main(String[] args) {
		int count=2;
		String url="https://mms.fpma.com/members/directory/search_bootstrap.php?org_id=FPMA";
		WebDriver companyPage=null;

		WebDriver chromeDriver = null;

		chromeDriver=setChromeDriver();
		if(chromeDriver!=null)
		{
			try {
				companyPage=getCompanyPage(chromeDriver,url);
				By inputArea2 = By.xpath("//input[@id='search_button']");
				if (isElementPresent(companyPage, inputArea2)) {
					WebElement element2 = companyPage.findElement(inputArea2);
					element2.click();
					Thread.sleep(5000);
					try (FileWriter writer = new FileWriter(
							"C:\\Users\\Omprakash\\Desktop\\APMA_selenium\\1.html", true);
							BufferedWriter bw = new BufferedWriter(writer)) {				
						bw.write(companyPage.getPageSource());
						bw.write("\n");

					} catch (IOException ex) {
						System.err.format("IOException: %s%n", ex);
					}
					
					for (int i = 2; i <=38; i++) {
						try {
							By inputArea3 = By.xpath("//div[@data-buttonnum='B"+i+"']");
							if (isElementPresent(companyPage, inputArea2)) {
								WebElement element3 = companyPage.findElement(inputArea3);
								element3.click();
								Thread.sleep(5000);
								try (FileWriter writer = new FileWriter(
										"C:\\Users\\Omprakash\\Desktop\\APMA_selenium\\"+count+".html", true);
										BufferedWriter bw = new BufferedWriter(writer)) {				
									bw.write(companyPage.getPageSource());
									bw.write("\n");
									count++;
								} catch (IOException ex) {
									System.err.format("IOException: %s%n", ex);
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				}
			} catch (Exception e) {
				// TODO: handle exception
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
			System.setProperty("webdriver.chrome.driver","C:\\sai\\driver\\new_10.04\\chromedriver.exe");

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

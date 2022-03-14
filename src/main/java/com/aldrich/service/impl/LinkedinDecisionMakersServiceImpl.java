package com.aldrich.service.impl;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aldrich.dao.EmployeeDetailsDAO;
import com.aldrich.entity.EmployeeDetails;
import com.aldrich.service.LinkedinDecisionMakersService;
@Service
public class LinkedinDecisionMakersServiceImpl implements LinkedinDecisionMakersService 
{
	@Autowired
	EmployeeDetailsDAO employeeDetailsDAO;

	SimpleDateFormat activityDateSdf = new SimpleDateFormat("yyyy-MM-dd");



	@Override
	public void runService() 
	{
		//String resp="";
		//BufferedWriter  bufferedWriter =null;
		WebDriver chromeDriver=null;
		WebDriver loginDriver=null;
		//WebDriver companyPage=null;
		//int scrollLimit=5;
		//List<LinkedinInputBO> linkedinBOList=null;
		File filePath=null;
		//String result = "";
		try
		{
			/*linkedinBOList=this.socialMediaLinkDAO.getLinkedinLinks();
			if(linkedinBOList!=null && !linkedinBOList.isEmpty())
			{*/
			chromeDriver=this.setChromeDriver();
			if(chromeDriver!=null)
			{
				loginDriver=this.loginLinkedinWebsite(chromeDriver);
				if(loginDriver!=null)
				{
					/*for(LinkedinInputBO linkedinBO:linkedinBOList)
						{*/
					File folder = new File("C:\\Users\\akumar\\Desktop\\Desktop Data\\Linkedin\\SalesNavigator\\Test SalesNavigator\\");
					File [] files = folder.listFiles();
					for(File file:files)
					{
						try
						{
							filePath=new File(file.getPath());
							if(filePath!=null)
							{
								Document doc=this.parseHtmlFile(filePath);
								if(doc!=null)
								{
									Elements ele=doc.select("li[class=pv5 ph2 search-results__result-item]");
									if(ele!=null && !ele.isEmpty())
									{
										for (Element elements : ele) 
										{
											try
											{
												String result ="";
												Elements dlEle1=elements.select("article").select("dl");
												String id=dlEle1.select("dt").select("a").attr("href");
												String name=dlEle1.select("dt").select("a").text();
												if(!id.equals(""))
												{
													WebDriver empPage=this.getEmployeePage(loginDriver, id);
													if(empPage!=null)
													{
														String response=empPage.getPageSource();
														if(!response.equals(""))
														{
															Document document=Jsoup.parse(response);
															if(document!=null)
															{
																Elements ele1=document.select("div[class=profile-topcard-person-entity__content min-width inline-block]");
																String location=ele1.select("div[class=profile-topcard__location-data inline Sans-14px-black-60% mr5]").text();
																String connections=ele1.select("div[class=profile-topcard__connections-data type-total inline Sans-14px-black-60% mr5]").text();
																By inputArea2 = By.xpath("//li-icon[@type='ellipsis-horizontal-icon']");
																List <WebElement> myIput2 = empPage.findElements(inputArea2);
																if(myIput2!=null && !myIput2.isEmpty())
																{
																	WebElement element1 = empPage.findElement(By.xpath("//li-icon[@type='ellipsis-horizontal-icon']"));
																	//element1.click();
																	Actions action = new Actions(empPage);
																	action.moveToElement(element1).click().build().perform();
																	By inputArea1 = By.xpath("//artdeco-dropdown-item[@data-control-name='copy_linkedin']");
																	List <WebElement> myIput1 = empPage.findElements(inputArea1);
																	if(!myIput1.isEmpty())
																	{
																		WebElement element2 = empPage.findElement(By.xpath("//artdeco-dropdown-item[@data-control-name='copy_linkedin']"));
																		//element2.click();
																		Actions actions = new Actions(empPage);
																		actions.moveToElement(element2).click().build().perform();
																	}
																	Toolkit toolkit = Toolkit.getDefaultToolkit();
																	Clipboard clipboard = toolkit.getSystemClipboard();
																	result = (String) clipboard.getData(DataFlavor.stringFlavor);
																}
																	EmployeeDetails employeeDetails=new EmployeeDetails();
																	if(!result.equals(""))
																	{
																		employeeDetails.setPermalink(result.replace("https://www.linkedin.com/in/", ""));
																	}
																	else
																	{
																		employeeDetails.setPermalink(id);
																	}
																	employeeDetails.setFullName(name);
																	if(!connections.equals(""))
																	{
																		connections=connections.replace("+", "").replace("connections", "");
																		employeeDetails.setConnectionCount(Long.parseLong(connections));
																	}
																	employeeDetails.setLocation(location);
																	employeeDetails.setActivityDateTime(new Date());
																	final String activityDateStr = this.activityDateSdf
																			.format(employeeDetails.getActivityDateTime());
																	try {
																		employeeDetails.setActivityDateTimeTemp(
																				this.activityDateSdf.parse(activityDateStr));
																	} catch (ParseException e) {
																		// error
																	}
																	Long empId = this.saveEmployeeDetails(employeeDetails);
																	System.out.println(empId);
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
									}
								}
							}


						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
				}
			}
			//}
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
			//options.addArguments("--start-maximized");
			options.addArguments("--window-size=2000,6000");
			options.setCapability("acceptSslCerts", true);
			options.setCapability("acceptInsecureCerts", true);	
			System.setProperty("webdriver.chrome.driver","C:\\Users\\akumar\\Desktop\\chromedriver_win32\\chromedriver.exe");
			driver = new ChromeDriver(options);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return driver;
	}



	@Override
	public Document parseHtmlFile(File file) 
	{
		Document doc=null;
		long companyId=0l;
		try
		{
			companyId=Long.parseLong(file.getPath().replace("C:\\Users\\akumar\\Desktop\\Desktop Data\\Linkedin\\SalesNavigator\\Test SalesNavigator\\", "").replace(".html", ""));
			doc=Jsoup.parse(file, "UTF-8");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return doc;
	}

	@Override
	public WebDriver loginLinkedinWebsite(WebDriver driver) 
	{
		String loginUrl = "https://www.linkedin.com/login";				
		try
		{
			driver.get(loginUrl);
			driver.findElement(By.cssSelector("input[name='session_key']")).sendKeys("Sameer@cascadesresearch.com");	
			driver.findElement(By.cssSelector("input[name='session_password']")).sendKeys("Cascades2468");			
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
		String loginUrl = "https://www.linkedin.com/sales/search/people/list/employees-for-account/"+linkedinURL+"?&seniority=6%2C7%2C8";				
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



	@Override
	public WebDriver getEmployeePage(WebDriver driver,String linkedinURL)
	{
		//String page="";
		int scrollLimit=10;
		String loginUrl = "https://www.linkedin.com"+linkedinURL;				
		try
		{
			driver.navigate().to(loginUrl);
			By inputArea = By.xpath("//button[@class='profile-section__experience-expansion button-tertiary-small']");
			List <WebElement> myIput = driver.findElements(inputArea);
			if(!myIput.isEmpty())
			{
				WebElement element = driver.findElement(By.xpath("//button[@class='profile-section__experience-expansion button-tertiary-small']"));
				Actions actions = new Actions(driver);
				actions.moveToElement(element).click().build().perform();
			}
			for (int i = scrollLimit; i >= 0; i--) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

				// Wait to load the scrolled page
				Thread.sleep(1000);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return driver;
	}


	@Override
	public Long saveEmployeeDetails(EmployeeDetails employeeDetails) {
		Long id = (long) 0;
		try {
			List<EmployeeDetails> employees = this.employeeDetailsDAO.checkForExistance(employeeDetails.getFullName());
			if (employees == null || employees.isEmpty()) {
				Long maxID = this.employeeDetailsDAO.getId();
				employeeDetails.setEmpId(maxID + 1);
				this.employeeDetailsDAO.save(employeeDetails);
				id = employeeDetails.getEmpId();
			}

		} catch (Exception e) {
			id = (long) 0;
			e.printStackTrace();
		}
		return id;

	}

}

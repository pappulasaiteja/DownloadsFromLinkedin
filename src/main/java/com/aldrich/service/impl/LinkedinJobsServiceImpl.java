package com.aldrich.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DateUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aldrich.BO.LinkedinInputBO;
import com.aldrich.dao.MongoSaveDAO;
import com.aldrich.dao.SocialMediaLinkDAO;
import com.aldrich.entity.Jobs;
import com.aldrich.service.LinkedinJobsService;
@Service
public class LinkedinJobsServiceImpl implements LinkedinJobsService 
{
	@Autowired
	SocialMediaLinkDAO socialMediaLinkDAO;

	@Autowired
	MongoSaveDAO mongoSaveDAO;

	@Override
	public void runService() 
	{
		WebDriver chromeDriver=null;
		WebDriver loginDriver=null;
		WebDriver companyPage=null;
		List<LinkedinInputBO> linkedinBOList=null;
		//WebDriver page=null;
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
								/*if(linkedinBO.getPaseID().equals("4613"))
								{*/
								Thread.sleep(10000);
								companyPage=this.getCompanyPage(loginDriver,linkedinBO.getLinkedinURL());
								if(companyPage!=null)
								{
									Document doc=Jsoup.parse(companyPage.getPageSource());
									if(doc!=null)
									{
										Elements ulEle=doc.select("ul[class=jobs-search-results__list artdeco-list]");
										if(ulEle!=null && !ulEle.isEmpty())
										{
											Elements liEle=ulEle.select("li");
											if(liEle!=null && !liEle.isEmpty())
											{
												for(Element ele:liEle)
												{
													int postDate=0;
													String location="";
													String title="";
													String url="";
													String postedDate="";
													try
													{
														location=ele.select("span[class=job-card-search__location artdeco-entity-lockup__caption ember-view]").text();
														title=ele.select("h3[class=job-card-search__title job-card-search__title--full artdeco-entity-lockup__title ember-view]").text().replace("Promoted", "").trim();
														if(title.equals(""))
														{
															title=ele.select("h3[class=job-card-search__title artdeco-entity-lockup__title ember-view]").text().replace("Promoted", "").trim();
														}
														url=ele.select("a[data-control-name=A_jobssearch_job_result_click]").attr("href");
														if(!url.equals(""))
														{
															Elements ulEle1=ele.select("ul[class=job-card-search__footer mt1 t-12 t-black--light list-style-none]");
															Elements liEle1=ulEle1.select("li[class=job-card-search__footer-item]");
															Elements timeEle=liEle1.select("time");
															postedDate=timeEle.attr("datetime");
															if(!postedDate.equals(""))
															{
																System.out.println(postedDate);
																Jobs jobs=new Jobs();
																jobs.setJobtitle(title);
																jobs.setLocation(location);
																jobs.setUrl("https://www.linkedin.com"+url);
																String jobKey=jobs.getUrl().trim().substring(jobs.getUrl().indexOf("refId="),jobs.getUrl().indexOf("&trk"));
																//jobKey=jobKey.substring(0,jobKey.indexOf("&trk"));
																jobs.setJobkey(jobKey);
																jobs.setPosted_date(postedDate);
																jobs.setCompanyId(Long.parseLong(linkedinBO.getPaseID()));
																Jobs checkExistence=this.mongoSaveDAO.checkExistence(jobs);
																if(checkExistence==null)
																	this.mongoSaveDAO.saveJobs(jobs);
															}
															else
															{
																companyPage.get("https://www.linkedin.com"+url);
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
																Document doc1=Jsoup.parse(companyPage.getPageSource());
																if(doc1!=null)
																{
																	if(title.equals(""))
																	{
																		title=doc1.select("h1[class=jobs-top-card__job-title t-24]").text();
																	}
																	Elements pEle=doc1.select("p[class=mt1 full-width flex-grow-1 t-14 t-black--light]");
																	if(pEle!=null && !pEle.isEmpty())
																	{
																		String spanText=pEle.select("span[class=a11y-text]").text();
																		if(!spanText.equals(""))
																		{
																			if(spanText.contains("Posted Date"))
																			{
																				postedDate=doc1.select("p[class=mt1 full-width flex-grow-1 t-14 t-black--light]").text();
																				postedDate=postedDate.replace("Posted Date", "").replace("Posted", "");
																				if(postedDate.contains("ago"))
																				{
																					postedDate=postedDate.substring(0,postedDate.indexOf("ago"));
																				}
																				if(postedDate.trim().contains("week"))
																				{
																					postedDate=postedDate.replace("weeks", "").replace("week", "");
																					postDate=Integer.parseInt(postedDate.trim());
																					postDate= postDate*7;
																					//System.out.println(postDate*7);
																					//System.out.println(postedDate);
																				}
																				else if(postedDate.trim().contains("day"))
																				{
																					postedDate=postedDate.replace("days", "").replace("day", "");
																					postDate=Integer.parseInt(postedDate.trim());
																					postDate= postDate*1;
																					//System.out.println(postDate*1);
																				}
																				else if(postedDate.trim().contains("month"))
																				{
																					postedDate=postedDate.replace("months", "").replace("month", "");
																					postDate=Integer.parseInt(postedDate.trim());
																					postDate=postDate*30;
																					//System.out.println(postDate*30);
																				}
																			}
																		}
																	}
																}
																Date yourDate = DateUtils.addDays(new Date(), -(postDate));
																String finalPostDate=toddMMyy(yourDate);
																Jobs jobs=new Jobs();
																jobs.setJobtitle(title);
																jobs.setLocation(location);
																jobs.setUrl("https://www.linkedin.com"+url);
																String jobKey=jobs.getUrl().trim().substring(jobs.getUrl().indexOf("refId="),jobs.getUrl().indexOf("&trk"));
																//jobKey=jobKey.substring(0,jobKey.indexOf("&trk"));
																jobs.setJobkey(jobKey);
																jobs.setPosted_date(finalPostDate);
																jobs.setCompanyId(Long.parseLong(linkedinBO.getPaseID()));
																Jobs checkExistence=this.mongoSaveDAO.checkExistence(jobs);
																if(checkExistence==null)
																	this.mongoSaveDAO.saveJobs(jobs);
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
								//}
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



	public WebDriver getCompanyPage(WebDriver driver,String linkedinUniqueName)
	{
		int scrollLimit=5;
		try
		{
			driver.navigate().to("https://www.linkedin.com/company/"+linkedinUniqueName+"/about/");
			for (int i = scrollLimit; i >= 0; i--) 
			{
				try
				{
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

					// Wait to load the scrolled page
					Thread.sleep(1000);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			Document doc=Jsoup.parse(driver.getPageSource());
			String uniqueId=doc.select("a[data-control-name=topcard_see_all_employees]").attr("href");
			if(!uniqueId.equals(""))
			{
				uniqueId=uniqueId.replace("search/results/people/?facetCurrentCompany=", "").replace("%5B%22", "").replace("%22%5D", "").replace("/", "");	
			}
			if(!uniqueId.equals(""))
			{
				driver.navigate().to("https://www.linkedin.com/jobs/search/?f_C="+uniqueId+"&locationId=OTHERS.worldwide");
				for (int i = scrollLimit; i >= 0; i--) 
				{
					try
					{
						WebElement scroll = driver.findElement(By.xpath("//div[@class='jobs-search-results jobs-search-results--is-two-pane']"));
						scroll.sendKeys(Keys.PAGE_DOWN);
						Thread.sleep(1000);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}


				/*WebElement element =driver.findElement(By.xpath("//div[@class='jobs-search-results jobs-search-results--is-two-pane']"));	
				Actions actions = new Actions((WebDriver) element);
				actions.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();*/
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return driver;
	}

	public  String toddMMyy(Date day){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String date = formatter.format(day);
		return date;
	}

}

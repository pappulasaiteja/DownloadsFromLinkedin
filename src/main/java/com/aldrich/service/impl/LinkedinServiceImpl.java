package com.aldrich.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
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
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aldrich.BO.LinkedinInputBO;
import com.aldrich.dao.SocialMediaLinkDAO;
import com.aldrich.entity.Category;
import com.aldrich.entity.Company;
import com.aldrich.entity.CompanyProfile;
import com.aldrich.entity.CompanyTypeMapping;
import com.aldrich.entity.Competitor;
import com.aldrich.entity.NavatarLead;
import com.aldrich.pase.vo.CompanyDomainVO;
import com.aldrich.service.LinkedinService;
import com.aldrich.util.PASEConstants;


@Service
public class LinkedinServiceImpl implements LinkedinService
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
		//List<NavatarLead> navatarLeadList=null;
		List<CompanyDomainVO> companyList=null;
		try
		{
			companyList=this.socialMediaLinkDAO.getCompanyDomains();
			if(!companyList.isEmpty())
			{
				chromeDriver=this.setChromeDriver();
				if(chromeDriver!=null)
				{
					loginDriver=this.loginLinkedinWebsite(chromeDriver);
					if(loginDriver!=null)
					{
						for(CompanyDomainVO domain:companyList)
						{
							try
							{
								Thread.sleep(10000);
								resp=loginDriver.getPageSource();
								if(!resp.equals(""))
								{
									Thread.sleep(10000);
									loginDriver.findElement(By.cssSelector("input[class='search-bar-input rounded input withIcon']")).sendKeys(domain.getDomain().trim());
									Thread.sleep(10000);
									String res=loginDriver.getPageSource();
									if(!res.equals(""))
									{
										Document doc=Jsoup.parse(res);
										if(doc!=null)
										{
											Elements ele=doc.select("div[class=search-list-wrapper]");
											if(!ele.isEmpty())
											{
												Elements ul=ele.select("ul[class=list-group search-list]");
												if(!ul.isEmpty())
												{
													Elements div=ul.select("div");
													System.out.println(div.size());
													for(Element divEle:div)
													{
														try
														{
															if(divEle.hasAttr("data-link"))
															{
																String link=divEle.attr("data-link");
																if(!link.equals(""))
																{
																	companyPage=this.getOwlerPage(loginDriver, link);
																	if(companyPage!=null)
																	{
																		Thread.sleep(10000);
																		String pageSource=companyPage.getPageSource();
																		pageSource=pageSource.substring(pageSource.trim().indexOf(" __NEXT_DATA__ = "),pageSource.trim().indexOf(" module={}"));
																		pageSource=pageSource.replace(" __NEXT_DATA__ = ", "");
																		if(!pageSource.equals(""))
																		{
																			File file=new File("C:\\Users\\akumar\\Desktop\\BI2.txt");	
																			FileWriter writer = new FileWriter(file);
																			writer.write(pageSource);
																			writer.close();
																			JSONObject json=new JSONObject(pageSource);
																			//System.out.println(json);
																			if(json.length()>0)
																			{
																				if(json.has("props"))
																				{
																					JSONObject propsJson=json.getJSONObject("props");
																					//System.out.println(propsJson);
																					if(propsJson.length()>0)
																					{
																						if(propsJson.has("pageProps"))
																						{
																							JSONObject pagePropsJson=propsJson.getJSONObject("pageProps");
																							if(pagePropsJson.length()>0)
																							{
																								if(pagePropsJson.has("initialState"))
																								{
																									JSONObject initialStateJson=pagePropsJson.getJSONObject("initialState");
																									//System.out.println(initialStateJson);
																									if(initialStateJson.length()>0)
																									{
																										if(initialStateJson.has("domainName"))
																										{
																											String domainName=initialStateJson.get("domainName").toString();
																											if(domainName.trim().toLowerCase().equals(domain.getDomain().trim().toLowerCase()))
																											{
																												if(initialStateJson.has("cg"))
																												{
																													Object obj=initialStateJson.get("cg");
																													if(obj instanceof JSONArray)
																													{
																														JSONArray array=(JSONArray)obj;
																														if(array.length()>0)
																														{
																															for(int i=0;i<array.length();i++)
																															{
																																try
																																{
																																	JSONObject cgJson=array.getJSONObject(i);
																																	//System.out.println(cgJson);
																																	if(cgJson.length()>0)
																																	{
																																		if(cgJson.has("companyBasicInfo"))
																																		{
																																			JSONObject companyBasicInfoJson=cgJson.getJSONObject("companyBasicInfo");
																																			if(companyBasicInfoJson.length()>0)
																																			{
																																				if(companyBasicInfoJson.has("cpLink"))
																																				{
																																					String cpLink=companyBasicInfoJson.getString("cpLink");
																																					companyPage=this.getOwlerPage(loginDriver, cpLink);
																																					if(companyPage!=null)
																																					{
																																						Thread.sleep(10000);
																																						String pageSource1=companyPage.getPageSource();
																																						pageSource1=pageSource1.substring(pageSource1.trim().indexOf(" __NEXT_DATA__ = "),pageSource1.trim().indexOf(" module={}"));
																																						pageSource1=pageSource1.replace(" __NEXT_DATA__ = ", "");
																																						if(!pageSource1.equals(""))
																																						{
																																							JSONObject pageSourceJson=new JSONObject(pageSource1);
																																							if(pageSourceJson.length()>0)
																																							{
																																								if(pageSourceJson.has("props"))
																																								{
																																									JSONObject propsJsonObj=pageSourceJson.getJSONObject("props");
																																									if(propsJsonObj.length()>0)
																																									{
																																										if(propsJsonObj.has("pageProps"))
																																										{
																																											JSONObject pagePropsJsonObj=propsJsonObj.getJSONObject("pageProps");
																																											if(pagePropsJsonObj.length()>0)
																																											{
																																												if(pagePropsJsonObj.has("initialState"))
																																												{
																																													JSONObject initialJson=pagePropsJsonObj.getJSONObject("initialState");
																																													if(initialJson.length()>0)
																																													{
																																														this.competitorDataMapping(initialJson,domain);
																																													}
																																												}
																																											}
																																										}
																																									}
																																								}
																																							}
																																						}
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
																												}
																											}
																										}
																									}
																								}
																							}
																						}
																					}
																				}
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
		String loginUrl = "https://www.owler.com/login";				
		try
		{
			driver.get(loginUrl);
			Thread.sleep(10000);
			driver.findElement(By.cssSelector("input[name='email']")).sendKeys("sputtala@cascadesresearch.com");
			Thread.sleep(10000);
			driver.findElement(By.cssSelector("button[class='modal-button button regular fill orange']")).click();
			Thread.sleep(10000);
			driver.findElement(By.id("password")).sendKeys("0wLer&543");	
			Thread.sleep(10000);
			driver.findElement(By.cssSelector("button[class='modal-button button regular fill orange']")).click();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);   
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return driver;
	}



	@Override
	public WebDriver getCompanyPage(WebDriver driver,NavatarLead lead)
	{
		String pid="";
		String objectId="";
		try
		{
			driver.navigate().to(lead.getSalesforceLink());
			Thread.sleep(10000);
			objectId=lead.getSalesforceLink().trim().replace("https://aldrichcapitalpartners.my.salesforce.com/", "");
			if(driver!=null)
			{
				//driver.navigate().to("https://sf.pitchbook.com/search.html?crmType=0&objectType=account&objectName=engage2serve.com&objectId=0011U00000gULPfQAO&windowMode=popUp");
				By inputArea1 = By.xpath("//div[@class='tabset slds-tabs_card uiTabset--base uiTabset--default uiTabset--dense uiTabset flexipageTabset']//ul//li[@class='tabs__item uiTabItem']//a[@title='Pitchbooks']");
				List <WebElement> myIput1 = driver.findElements(inputArea1);
				if(!myIput1.isEmpty())
				{


					WebElement element2 = driver.findElement(By.xpath("//div[@class='tabset slds-tabs_card uiTabset--base uiTabset--default uiTabset--dense uiTabset flexipageTabset']//ul//li[@class='tabs__item uiTabItem']//a[@title='Pitchbooks']"));
					//element2.click();
					Actions actions = new Actions(driver);
					actions.moveToElement(element2).click().build().perform();

					Thread.sleep(10000);

					driver.navigate().to("https://sf.pitchbook.com/search.html?crmType=0&objectType=account&objectName="+lead.getDomain()+"&objectId="+objectId+"&windowMode=popUp");

					Thread.sleep(10000);


					String resp=driver.getPageSource();
					if(!resp.equals(""))
					{
						Document doc=Jsoup.parse(resp);
						if(doc!=null)
						{
							Elements table=doc.select("table[class=data-table]");
							if(!table.isEmpty())
							{
								Elements trEle=table.select("tbody tr td");
								if(!trEle.isEmpty())
								{
									pid=trEle.get(1).text();
									//System.out.println(pid);
								}
							}
						}
					}

					By inputArea2 = By.xpath("//table[@class='data-table']//td//div[@class='report_link link_action']");
					List <WebElement> myIput2 = driver.findElements(inputArea2);
					if(!myIput2.isEmpty())
					{
						WebElement element3 = driver.findElement(By.xpath("//table[@class='data-table']//td//div[@class='report_link link_action']"));
						//element2.click();
						Actions actions1 = new Actions(driver);
						actions1.moveToElement(element3).click().build().perform();


						Thread.sleep(10000);

						/*By inputArea3 = By.xpath("//div[@class='tabset slds-tabs_card uiTabset--base uiTabset--default uiTabset--dense uiTabset flexipageTabset']//ul//li[@class='tabs__item uiTabItem']//a[@title='Pitchbooks']");
						List <WebElement> myIput3 = driver.findElements(inputArea3);
						if(!myIput3.isEmpty())
						{
							WebElement element4 = driver.findElement(By.xpath("//div[@class='tabset slds-tabs_card uiTabset--base uiTabset--default uiTabset--dense uiTabset flexipageTabset']//ul//li[@class='tabs__item uiTabItem']//a[@title='Pitchbooks']"));
							//element2.click();
							Actions actions3 = new Actions(driver);
							actions3.moveToElement(element4).click().build().perform();*/


						Thread.sleep(10000);

						if(!pid.equals(""))
						{
							driver.navigate().to("https://sf.pitchbook.com/accountSyncForm.html?crmType=0&pbid="+pid+"&accountid="+objectId+"&windowMode=popUp");

							Thread.sleep(10000);

							By inputArea4 = By.xpath("//div[@style='float: right; padding: 20px 23px 10px 0;']//div[@class='pr-big-button sync_button']");
							List <WebElement> myIput4 = driver.findElements(inputArea4);

							if(!myIput4.isEmpty())
							{
								WebElement element5 = driver.findElement(By.xpath("//div[@style='float: right; padding: 20px 23px 10px 0;']//div[@class='pr-big-button sync_button']"));
								//element2.click();
								Actions actions4 = new Actions(driver);
								actions4.moveToElement(element5).click().build().perform();
							}
						}

						//}

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



	public void waitForLoad(WebDriver driver) {
		ExpectedCondition<Boolean> pageLoadCondition = new
				ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 250);
		wait.until(pageLoadCondition);
	}







	@Override
	public List<LinkedinInputBO> fetchExceldata() 
	{
		LinkedinInputBO patientDataBo	= null;
		List<LinkedinInputBO> patientDataList=null;
		InputStream fis=null;
		try
		{
			patientDataList=new ArrayList<LinkedinInputBO>();
			fis = new FileInputStream(new File("C:\\Users\\akumar\\Desktop\\UnfundedListSet1(1000).xlsx"));
			if(fis!=null)
			{
				XSSFWorkbook workbook = new XSSFWorkbook(fis);
				XSSFSheet spreadsheet = workbook.getSheetAt(0);
				int rowSize = spreadsheet.getLastRowNum();
				if (rowSize > 0)
				{
					XSSFRow headerRow = spreadsheet.getRow(0);
					String header1 = headerRow.getCell(0).getStringCellValue();
					String header2 = headerRow.getCell(1).getStringCellValue();
					if (header1.equals("PASE ID") && header2.equals("LinkedIn from GCSE"))
					{
						Iterator<Row> rowIterator = spreadsheet.iterator();
						int i = 0;
						for (rowIterator = spreadsheet.iterator(); rowIterator.hasNext();)
						{
							i++;
							String paseID = "";
							String accountName="";
							String website="";
							String linkedinURL="";
							try
							{
								Row row = rowIterator.next();
								if (i == 1)
									continue;

								patientDataBo=new LinkedinInputBO();

								if (row.getCell(0) != null && row.getCell(0).getCellType() != Cell.CELL_TYPE_BLANK && row.getCell(0).getCellType()==row.getCell(0).CELL_TYPE_NUMERIC)
									paseID=String.valueOf(row.getCell(0).getNumericCellValue()).replace(".0", "");
								if(row.getCell(1) != null && row.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK && row.getCell(1).getCellType()==row.getCell(1).CELL_TYPE_NUMERIC)
								{
									linkedinURL=String.valueOf(row.getCell(1).getNumericCellValue()).replace(".0", "");
								}
								else if(row.getCell(1) != null && row.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK && row.getCell(1).getCellType()==row.getCell(1).CELL_TYPE_STRING)
								{
									linkedinURL=row.getCell(1).getStringCellValue();
								}
								patientDataBo.setPaseID(paseID);
								patientDataBo.setAccountName(accountName);
								patientDataBo.setWebsite(website);
								patientDataBo.setLinkedinURL(linkedinURL);
								patientDataList.add(patientDataBo);
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
		return patientDataList;
	}


	@Override
	public WebDriver getOwlerPage(WebDriver driver, String link) 
	{
		int scrollLimit=10;
		try
		{
			driver.navigate().to(link);
			for (int k = scrollLimit; k >= 0; k--) {
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
	public boolean competitorDataMapping(JSONObject resp, CompanyDomainVO vo) 
	{
		boolean processed = false;
		Long competitorCompanyId=0l;
		try
		{
			System.out.println(resp);
			competitorCompanyId=this.getcopetitorCompanyId(resp);

			// save the partner type into company type mapping table
			this.saveCompanyTypeMappingInfo(competitorCompanyId);

			// save company profile
			this.saveCompanyProfileInfo(resp, competitorCompanyId, vo);
			
			//save competitor data
			this.savecompetitor(competitorCompanyId,vo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return processed;
	}


	@Override
	public Long getcopetitorCompanyId(JSONObject resp) 
	{
		String domain="";
		String companyName="";
		String url="";
		Long competitorCompanyId=0l;
		try
		{
			if(resp.has("domainName"))
				domain=resp.getString("domainName");
			if(resp.has("companyName"))
				companyName=resp.getString("companyName");
			if(resp.has("website"))
				url=resp.getString("website");
			List<Long> companyDtls =this.socialMediaLinkDAO.checkForExistanceByDomain(domain);
			if (companyDtls != null && companyDtls.size() > 0) 
			{
				competitorCompanyId = companyDtls.get(0);
			}
			else 
			{
				Company company = new Company();
				company.setName(companyName);
				company.setUrl(url);
				company.setDomain(domain);
				company.setRelevant(PASEConstants.RELEVANT_YES_NOT_TRACKING);
				competitorCompanyId = (Long) this.socialMediaLinkDAO.save(company);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return competitorCompanyId;
	}


	@SuppressWarnings("nls")
	@Override
	public boolean saveCompanyTypeMappingInfo(long competitorCompanyId) {
		boolean processed = false;
		try {
			List<CompanyTypeMapping> companyTypeDtls = this.socialMediaLinkDAO.checkForExistanceByCompanyIdAndType(
					competitorCompanyId, PASEConstants.COMPETITOR__COMPANY_TYPE_ID);
			if (companyTypeDtls == null || companyTypeDtls.size() == 0) {
				CompanyTypeMapping ctMapping = new CompanyTypeMapping();
				ctMapping.setFkCompanyId(competitorCompanyId);
				ctMapping.setFkCompanyTypeId(PASEConstants.COMPETITOR__COMPANY_TYPE_ID);
				this.socialMediaLinkDAO.saveCompanyTypeMapping(ctMapping);
				processed = true;
			} else {
				processed = true;
			}
		} catch (Exception e) {
			processed = false;
			e.printStackTrace();
		}
		return processed;
	}


	@Override
	public boolean saveCompanyProfileInfo(JSONObject resp, long competitorCompanyId, CompanyDomainVO vo) 
	{
		boolean processed=false;
		String employeeSize="";
		String employeeCount="";
		String revenue1="";
		Double revenue=0.0;
		String location="";
		String city="";
		String state="";
		String foundedYear="";
		String street1Address="";
		String street2Address="";
		String zipCode="";
		String sector="";

		try
		{
			System.out.println(resp);
			if(resp.has("employeeCount"))
				employeeCount=resp.get("employeeCount").toString();
			if(resp.has("employeeCount"))
				employeeSize=resp.get("employeeCount").toString();
			if(resp.has("city"))
				city=resp.get("city").toString();
			if(resp.has("state"))
				state=resp.get("state").toString();
			if(resp.has("founded"))
				foundedYear=resp.get("founded").toString();
			if(resp.has("street1Address"))
				street1Address=resp.get("street1Address").toString();
			if(resp.has("street2Address"))
				street2Address=resp.get("street2Address").toString();
			if(resp.has("zipcode"))
				zipCode=resp.get("zipcode").toString();
			if(resp.has("revenue"))
				revenue1=resp.get("revenue").toString();
			if(!revenue1.equals(""))
				revenue=Double.parseDouble(revenue1); 
			revenue=revenue*0.000001;

			location=street1Address+","+street2Address;
			if(resp.has("industrySectors"))
				sector=resp.get("industrySectors").toString();



			List<CompanyProfile> companyProfileDtls = this.socialMediaLinkDAO.checkForExistanceByCompanyId(competitorCompanyId);
			if (companyProfileDtls == null || companyProfileDtls.size() == 0) 
			{
				CompanyProfile companyProfile = new CompanyProfile();
				if(!employeeCount.equals(""))
					companyProfile.setEmpCount(Integer.parseInt(employeeCount));
				if(!employeeCount.equals(""))
					companyProfile.setEmployeeSize(employeeSize);
				if(!city.equals(""))
					companyProfile.setCity(city);
				if(!state.equals(""))
					companyProfile.setState(state);
				if(!foundedYear.equals(""))
					companyProfile.setFoundedYear(Integer.parseInt(foundedYear));
				if(!location.equals(""))
					companyProfile.setLocation(location);
				if(!zipCode.equals(""))
					companyProfile.setZipCode(zipCode);
				String value = String.format ("%.2f", revenue);
				companyProfile.setRevenue(value);
				long categoryId = this.checkForCategoryDetails(sector.trim());
				companyProfile.setFkCategoryId(categoryId);
				companyProfile.setFkCompanyId(competitorCompanyId);
				companyProfile.setFkDataSourceId(PASEConstants.DATA_SOURCE_OTHERS);
				companyProfile.setFkUserId(PASEConstants.DEFAULT_USER_ID);
				this.socialMediaLinkDAO.saveCompanyProfile(companyProfile);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return processed;
	}
	
	
	@Override
	public long checkForCategoryDetails(String categoryName) {
		//LOGGER.info("start - checkForCategoryDetails of PartnerIndexServiceImpl");
		long categoryId = 25L;
		try {
			String lowerCatName = categoryName.toLowerCase();
			lowerCatName = lowerCatName.replace(" ", "-");
			List<Category> categoriesList = this.socialMediaLinkDAO.checkForExistanceCategory(lowerCatName);
			if (categoriesList != null && categoriesList.size() > 0)
				categoryId = categoriesList.get(0).getId();
			else {
				Category category = new Category();
				Long defaultParentId = 0L;
				category.setParentId(defaultParentId);
				category.setName(categoryName);
				category.setCode(lowerCatName);
				categoryId = (Long) this.socialMediaLinkDAO.saveCategory(category);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//LOGGER.info("End - checkForCategoryDetails of PartnerIndexServiceImpl");
		return categoryId;
	}
	
	public boolean savecompetitor(long competitiorId,CompanyDomainVO vo)
	{
		boolean processed=false;
		Competitor comp=null;
		try
		{
			comp=new Competitor();
			comp.setFkCompetitiorCompanyId(competitiorId);
			comp.setFkCompanyId(vo.getCompanyId());
			comp.setConfidenceScore(PASEConstants.CRUCNH_BASE_DEFAULT_CONFIDENCE_SCORE);
			comp.setFkSourceId(PASEConstants.OWLER_SOURCE_ID);
			List<Competitor> compList=this.socialMediaLinkDAO.checkExistenceCompetitior(comp);
			if(compList!=null && compList.size()==0)
				this.socialMediaLinkDAO.saveCompetitior(comp);
			
		}
		catch(Exception e)
		{
			processed=false;
			e.printStackTrace();
		}
		return processed;
	}


}

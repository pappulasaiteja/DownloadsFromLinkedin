package com.aldrich.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import com.aldrich.service.LeadFromSalesNavigatorService;
import com.csvreader.CsvWriter;

import io.github.bonigarcia.wdm.WebDriverManager;

@Service
public class LeadFromSalesNavigatorServiceImpl implements LeadFromSalesNavigatorService{


	@Override
	public String runService() {
		WebDriver specificPage=null;
		WebDriver chromeDriver = null;
		WebDriver loginDriver = null;
		String output=null;
		String resp = null;
		String json=null;
		String linkedin_url = "";
		String employeeCountRange = "";
		chromeDriver=setChromeDriver();
		loginDriver=loginLinkedinWebsite(chromeDriver);
		if(chromeDriver!=null)
		{
			loginDriver=loginLinkedinWebsite(chromeDriver);
			try {
				for (int i = 1; i <=3; i++) {
					try {
						String url="https://www.linkedin.com/sales/search/company?companySize=C%2CD%2CE&geoIncluded=103644278&industryIncluded=4&keywords=Franchise&page="+i;
						specificPage=getSpecificPage(loginDriver,url);
						Thread.sleep(1000);
						Document document=null;
						try {
							if (specificPage != null) {
								scrollPage(specificPage, 50);			
								resp = specificPage.getPageSource();;
								Thread.sleep(1000);
								if (!resp.equals("")) {
									document=Jsoup.parse(resp);
									json=getJSONStringFromDocument(document);
									JSONObject jsonObject = new JSONObject(json);
									List<JSONObject> innerElementsJsonObject = getInnerJSONArrayJSONObjectList(jsonObject,
											"elements");
									if (innerElementsJsonObject != null && innerElementsJsonObject.size() > 0) {
										for (JSONObject innerJsonObject : innerElementsJsonObject) {

											employeeCountRange = getSpecificFeildFromJSONObject(innerJsonObject,
													"employeeCountRange");
											String orgin_linkedin_url = getSpecificFeildFromJSONObject(innerJsonObject,
													"entityUrn");
											String linkedin_company_id = orgin_linkedin_url.replace("urn:li:fs_salesCompany:",
													"");

											linkedin_url = "https://www.linkedin.com/sales/company/" + linkedin_company_id;
											specificPage=getSpecificPage(loginDriver,linkedin_url);
											Thread.sleep(1000);
											processCompanyPages(specificPage,employeeCountRange);

										}
									}
								}
								//processCompanyPages(specificPage);
							}
						}catch(Exception e){
							e.printStackTrace();
						}
						//processCompanyPages(specificPage,i);
					} catch (Exception e) {
						System.out.println("page not found");
					}
				}


			} catch (Exception e) {
				System.out.println("page not found");
			}

		}

		chromeDriver.close();
		chromeDriver.quit();
		return output;
	}


	@Override
	public void processCompanyPages(WebDriver companyPage,String employeeCountRange){
		String resp = null;
		String json=null;
		Document document=null;
		int count=1;
		JSONObject dataObject2 = null;
		JSONObject dataObject5 =null;
		JSONObject dataObject4 = null;
		JSONObject dataObject3 = null;

		String companyName = "";
		String companyUrl = "";
		String description = "";
		String industry = "";
		String type="";
		String yearFounded="";
		String location = "";
		String city="";
		String state="";
		String country="";
		String postalCode="";
		String estimatedMinRevenue = "";
		String estimatedMaxRevenue = "";
		String reportedRevenue = "";
		String linkedinUrl = "";
		String uniqueId = "";
		String specialties = "";
		String keywordType = "";

		try {
			if (companyPage != null) {
				scrollPage(companyPage, 50);			
				resp = companyPage.getPageSource();
				Thread.sleep(1000);
				if (!resp.equals("")) {
					document=Jsoup.parse(resp);
					json=getJSONStringFromDocumentNew(document);

					JSONObject jsonObject = new JSONObject(json);
					JSONObject dataObject1 = jsonObject.getJSONObject("headquarters");
					if (jsonObject.has("revenueRange")) {
						dataObject2 = jsonObject.getJSONObject("revenueRange");
						if (dataObject2.has("estimatedMinRevenue")) {
							dataObject3 = dataObject2.getJSONObject("estimatedMinRevenue");
						}
						if (dataObject2.has("estimatedMaxRevenue")) {
							dataObject4 = dataObject2.getJSONObject("estimatedMaxRevenue");
						}
						if (dataObject2.has("reportedRevenue")) {
							dataObject5 = dataObject2.getJSONObject("reportedRevenue");
						}
					}
					try {
						companyName = getSpecificFeildFromJSONObject(jsonObject, "name");
						companyUrl = getSpecificFeildFromJSONObject(jsonObject, "website");
						description = getSpecificFeildFromJSONObject(jsonObject, "description");
						industry = getSpecificFeildFromJSONObject(jsonObject, "industry");
						type = getSpecificFeildFromJSONObject(jsonObject, "type");
						yearFounded = getSpecificFeildFromJSONObject(jsonObject, "yearFounded");
						location = getSpecificFeildFromJSONObject(jsonObject, "location");
						linkedinUrl = getSpecificFeildFromJSONObject(jsonObject, "flagshipCompanyUrl");
						uniqueId = getSpecificFeildFromJSONObject(jsonObject, "entityUrn")
								.replace("urn:li:fs_salesCompany:", "");
						country = getSpecificFeildFromJSONObject(dataObject1, "country");
						state = getSpecificFeildFromJSONObject(dataObject1, "geographicArea");
						city = getSpecificFeildFromJSONObject(dataObject1, "city");
						postalCode = getSpecificFeildFromJSONObject(dataObject1, "postalCode");
						if (dataObject3!=null) {
							estimatedMinRevenue = getSpecificFeildFromJSONObject(dataObject3, "amount");
						}
						if (dataObject3!=null) {
							estimatedMaxRevenue = getSpecificFeildFromJSONObject(dataObject4, "amount");
						}
						if (dataObject3!=null) {
							reportedRevenue = getSpecificFeildFromJSONObject(dataObject5, "amount");
						}
						System.out.println(reportedRevenue);

						specialties = getInnerJSONArrayJSONObjectDataList(jsonObject, "specialties");
						System.out.println(specialties);

						keywordType = "Accounts Payable";

						/*String outputFile = "C:\\sai\\satyam\\linkedin\\sales_nav\\data\\data_16.12(1).csv";
						// before we open the file check to see if it already exists
						boolean alreadyExists = new File(outputFile).exists();

						try {
							// use FileWriter constructor that specifies open for appending
							CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');

							// if the file didn't already exist then we need to write out the header line
							if (!alreadyExists)
							{
								csvOutput.write("companyName");
								csvOutput.write("companyUrl");
								csvOutput.write("description");
								csvOutput.write("industry");
								csvOutput.write("type");
								csvOutput.write("yearFounded");
								csvOutput.write("location");
								csvOutput.write("city");
								csvOutput.write("state");
								csvOutput.write("country");
								csvOutput.write("postalCode");
								csvOutput.write("linkedinUrl");
								csvOutput.write("uniqueId");
								csvOutput.write("estimatedMinRevenue");
								csvOutput.write("estimatedMaxRevenue");
								csvOutput.write("reportedRevenue");
								csvOutput.write("employeeCountRange");
								csvOutput.write("specialties");
								csvOutput.write("keywordType");

								csvOutput.endRecord();
							}

							csvOutput.write(companyName);
							csvOutput.write(companyUrl);
							csvOutput.write(description);
							csvOutput.write(industry);
							csvOutput.write(type);
							csvOutput.write(yearFounded);
							csvOutput.write(location);
							csvOutput.write(city);
							csvOutput.write(state);
							csvOutput.write(country);
							csvOutput.write(postalCode);
							csvOutput.write(linkedinUrl);
							csvOutput.write(uniqueId);
							csvOutput.write(estimatedMinRevenue);
							csvOutput.write(estimatedMaxRevenue);
							csvOutput.write(reportedRevenue);
							csvOutput.write(employeeCountRange);
							csvOutput.write(specialties);
							csvOutput.write(keywordType);


							csvOutput.endRecord();


							csvOutput.close();
						}catch(Exception e){
							e.printStackTrace();
						}*/
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
	}

	public void scrollPage(WebDriver companyPage, int scrollLimit) {

		try {
			for (int k = scrollLimit; k >= 0; k--) {
				JavascriptExecutor js = (JavascriptExecutor) companyPage;
				js.executeScript("window.scrollBy(0,100)", "");


			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public   WebDriver setChromeDriver() 
	{	
		WebDriver driver = null;
		try
		{
			ChromeOptions  options = new ChromeOptions();
			options.addArguments("--disable-gpu", 

					"--window-size=1920,1200",
					"--ignore-certificate-errors",
					"--disable-extensions",
					"--no-sandbox",
					"--disable-dev-shm-usage");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(options);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return driver;
	}



	public static String getJSONStringFromDocument(Document documnet) {
		String json_string=null;
		if(documnet!=null){
			Elements code_elements=documnet.getElementsByTag("code");
			if(code_elements!=null&& code_elements.size()>0){
				for (Element element : code_elements) {
					try {
						if (element.hasAttr("style") && element.hasAttr("id")) {
							if (element.attr("style").equals("display: none")
									&& element.attr("id").startsWith("bpr-guid-")) {
								String pageContent = element.text();
								if(pageContent.startsWith("{\"metadata\"")){
									json_string=pageContent;
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}
		return json_string;
	}
	public static String getJSONStringFromDocumentNew(Document documnet) {
		String json_string=null;
		if(documnet!=null){
			Elements code_elements=documnet.getElementsByTag("code");
			if(code_elements!=null&& code_elements.size()>0){
				for (Element element : code_elements) {
					try {
						if (element.hasAttr("style") && element.hasAttr("id")) {
							if (element.attr("style").equals("display: none")
									&& element.attr("id").startsWith("bpr-guid-")) {
								String pageContent = element.text();
								if(pageContent.startsWith("{\"description\"")){
									json_string=pageContent;
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}
		return json_string;
	}

	public static JSONObject getSpecificJSONObject(JSONObject jsonObject, String propertyName) {
		JSONObject innerJsonObject = null;
		try {
			if (isValid(jsonObject, propertyName)) {
				innerJsonObject = (JSONObject) jsonObject.get(propertyName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return innerJsonObject;
	}

	public static String getSpecificFeildFromJSONObject(JSONObject jsonObject, String key) {
		String propertyName = "";
		try {
			if (isValid(jsonObject, key)) {
				propertyName = jsonObject.get(key).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return propertyName;
	}


	@Override
	public boolean isElementPresent(WebDriver driver ,By selector)
	{
		return driver.findElements(selector).size()>0;
	}

	public static List<JSONObject> getInnerJSONArrayJSONObjectList(JSONObject jsonObject, String propertyName) {
		List<JSONObject> innerJSONObjectList = new ArrayList<JSONObject>();
		if (isValid(jsonObject, propertyName)) {
			JSONArray propertyNameArray = jsonObject.getJSONArray(propertyName);
			if (propertyNameArray != null) {
				int arraySize = propertyNameArray.length();
				if (arraySize > 0) {
					for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
						try {
							JSONObject innerJsonObject = propertyNameArray.getJSONObject(arrayIndex);
							innerJSONObjectList.add(innerJsonObject);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return innerJSONObjectList;
	}

	public static String getInnerJSONArrayJSONObjectDataList(JSONObject jsonObject, String propertyName) {
		String data=null;
		List<String> innerJSONObjectList = new ArrayList<>();
		if (isValid(jsonObject, propertyName)) {
			JSONArray propertyNameArray = jsonObject.getJSONArray(propertyName);
			if (propertyNameArray != null) {
				int arraySize = propertyNameArray.length();
				if (arraySize > 0) {
					for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
						try {
							//JSONObject innerJsonObject = propertyNameArray.getJSONObject(arrayIndex);
							String str = propertyNameArray.getString(arrayIndex);
							innerJSONObjectList.add(str);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					data=innerJSONObjectList.stream().collect(Collectors.joining(","));
					//System.out.println(str);
				}
			}
		}
		return data;
	}

	public static boolean isValid(JSONObject jsonObject, String propertyName) {
		boolean valid = false;
		try {
			if (propertyName != null) {
				if (jsonObject.has(propertyName) && !jsonObject.get(propertyName).toString().equalsIgnoreCase("null")
						&& !jsonObject.get(propertyName).toString().equalsIgnoreCase(""))
					valid = true;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			valid = false;
		}
		return valid;
	}


	@Override
	public void scrollBottom(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		//Below code will scroll the web page till end
		jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");

	}


	@Override
	public  WebDriver loginLinkedinWebsite(WebDriver driver) 
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
	public   WebDriver getSpecificPage(WebDriver driver,String url)
	{	
		try{
			driver.navigate().to(url);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return driver;
	}

}

package com.aldrich.service.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aldrich.BO.LinkedinInputBO;
import com.aldrich.dao.LinkedinInsightsDAO;
import com.aldrich.dao.ServiceStatusDAO;
import com.aldrich.dao.SocialMediaLinkDAO;
import com.aldrich.entity.CompanyGrowthInfo;
import com.aldrich.entity.CrawlingStatus;
import com.aldrich.entity.FunctionGrowthInfo;
import com.aldrich.entity.JobGrowthInfo;
import com.aldrich.entity.LinkedinEmployeeGrowth;
import com.aldrich.entity.NewHiresGrowthInfo;
import com.aldrich.entity.ServiceStatus;
import com.aldrich.pase.vo.ExceptionVO;
import com.aldrich.service.LinkedinInsightsService;
import com.aldrich.service.PaseGenericService;
import com.aldrich.util.ExceptionUtil;
import com.aldrich.util.MonthConversion;
import com.aldrich.util.PASEConstants;
@Service
public class LinkedinInsightsServiceImpl implements LinkedinInsightsService 
{
	//private static final Logger LOGGER = Logger.getLogger(LinkedinInsightsServiceImpl.class);

	@Autowired
	PaseGenericService paseGenericService;

	@Autowired
	private ServiceStatusDAO serviceStatusDAO;

	@Autowired
	private LinkedinInsightsDAO linkedinInsightsDAO;

	@Autowired
	private SocialMediaLinkDAO socialMediaLinkDAO;

	@Override
	public boolean runService() 
	{

		String resp = "";
		boolean status = false;
		// File filePath=null;
		BufferedWriter bufferedWriter = null;
		Document document = null;
		Elements tableEle = null;
		Element growthTableEle = null;
		Element employeeGraphTableEle = null;
		Element newHireGraphTableEle = null;
		Element companyFunctionalGrowthTableEle = null;
		Element companyFunctionalJobGrowthTableEle = null;
		long companyId = 0l;
		List<FunctionGrowthInfo> functionGrowthInfoList = null;
		List<CompanyGrowthInfo> companyGrowthInfoList = null;
		List<NewHiresGrowthInfo> newHireGrowthInfoList = null;
		List<JobGrowthInfo> jobGrowthInfoList = null;
		List<LinkedinInputBO> linkedinBOList = null;
		WebDriver chromeDriver = null;
		WebDriver loginDriver = null;
		WebDriver companyPage = null;
		try {
			Date serviceStartDateTime = new Date();
			ServiceStatus serviceStatusInfo = new ServiceStatus();
			serviceStatusInfo.setServerName(PASEConstants.DEV_SERVER_NAME);
			serviceStatusInfo.setServerIPAddress(PASEConstants.DEV_SERVER_IP_ADDRESS);
			serviceStatusInfo.setFkServiceId(PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID);
			serviceStatusInfo.setStartDateTime(serviceStartDateTime);
			serviceStatusInfo.setStatus("In Progress");
			final long serviceStatusId = (Long) this.serviceStatusDAO.save(serviceStatusInfo);

			try {
				int count = 0;
				linkedinBOList = this.socialMediaLinkDAO.getLinkedinLinks();
				if (linkedinBOList != null && !linkedinBOList.isEmpty()) {
					chromeDriver = this.setChromeDriver();
					if (chromeDriver != null) {
						loginDriver = this.loginLinkedinWebsite(chromeDriver);
						if (loginDriver != null) {
							for (LinkedinInputBO linkedinBO : linkedinBOList) {
								/*
								 * if(linkedinBO.getPaseID().equals("301")) {
								 */
								try {
									System.out.println(count + "-" + linkedinBO.getLink());
									count = count + 1;
									Thread.sleep(10000);
									companyPage = this.getCompanyPage(loginDriver, linkedinBO.getLink());
									if (companyPage != null) {
										Thread.sleep(10000);
										resp = companyPage.getPageSource();
										if (!resp.equals("")) {
											if (!resp.trim().toLowerCase().contains("oops!") || !resp.trim()
													.toLowerCase().contains("this page is not available")|| !resp.trim().toLowerCase().contains("page not found") ) {
												CrawlingStatus crawlingStatusInfo = this.paseGenericService
														.saveDailyCrawlingStatus(Long.parseLong(linkedinBO.getPaseID()),
																PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID,
																serviceStatusId);
												companyId = Long.parseLong(linkedinBO.getPaseID());
												document = this.parseHtmlFile(resp, companyId);
												if (document != null) {
													tableEle = document.select("table");
													if (tableEle != null) {
														if (tableEle.size() == 0) {
															status = this.getEmployeeCount(document, companyId);
															if (status == true) {
																this.paseGenericService
																		.updateDailyCrawlingStatus(crawlingStatusInfo);
															}
														} else if (tableEle.size() <= 6) {
															for (int i = 0; i < tableEle.size(); i++) {
																try {
																	if (i == 0) {
																		growthTableEle = (Element) tableEle.get(i);
																		if (growthTableEle != null) {
																			this.getInsigthsEmployeeGrowth(
																					growthTableEle, companyId);
																		}
																	} else if (i == 1) {
																		employeeGraphTableEle = (Element) tableEle
																				.get(i);
																		if (employeeGraphTableEle != null) {
																			companyGrowthInfoList = this
																					.getCompanyGrowthInfo(
																							employeeGraphTableEle,
																							companyId);
																			if (companyGrowthInfoList != null
																					&& !companyGrowthInfoList
																							.isEmpty()) {
																				this.saveCompanyGrowth(
																						companyGrowthInfoList);
																			}

																		}
																	} else if (i == 3) {
																		companyFunctionalGrowthTableEle = (Element) tableEle
																				.get(i);
																		if (companyFunctionalGrowthTableEle != null) {
																			functionGrowthInfoList = this
																					.getEmployeeFunctionHeadCount(
																							companyFunctionalGrowthTableEle,
																							companyId);
																			if (functionGrowthInfoList != null
																					&& !functionGrowthInfoList
																							.isEmpty()) {
																				this.saveFunctionGrowth(
																						functionGrowthInfoList);
																			}
																		}
																	} else if (i == 5) {
																		newHireGraphTableEle = (Element) tableEle
																				.get(i);
																		if (newHireGraphTableEle != null) {
																			newHireGrowthInfoList = this
																					.getNewHireGrowthInfo(
																							newHireGraphTableEle,
																							companyId);
																			if (newHireGrowthInfoList != null
																					&& !newHireGrowthInfoList
																							.isEmpty()) {
																				this.saveNewHireGrowth(
																						newHireGrowthInfoList);
																			}
																			this.paseGenericService
																					.updateDailyCrawlingStatus(
																							crawlingStatusInfo);
																		}
																	}
																} catch (Exception e) {
																	// LOGGER.info("Exception
																	// -
																	// runService
																	// of
																	// LinkedinInsightsServiceImpl");
																	final long lineNumber = Thread.currentThread()
																			.getStackTrace()[2].getLineNumber();
																	this.paseGenericService
																			.saveException(new ExceptionVO(companyId,
																					PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID,
																					"runService", lineNumber,
																					e.getMessage(), "",
																					ExceptionUtil
																							.determineExceptionCodeByException(
																									e)));
																}
															}
														} else if (tableEle.size() > 6) {
															for (int i = 0; i < tableEle.size(); i++) {
																try {
																	if (i == 0) {
																		growthTableEle = (Element) tableEle.get(i);
																		if (growthTableEle != null) {
																			this.getInsigthsEmployeeGrowth(
																					growthTableEle, companyId);
																		}
																	} else if (i == 1) {
																		employeeGraphTableEle = (Element) tableEle
																				.get(i);
																		if (employeeGraphTableEle != null) {
																			companyGrowthInfoList = this
																					.getCompanyGrowthInfo(
																							employeeGraphTableEle,
																							companyId);
																			if (companyGrowthInfoList != null
																					&& !companyGrowthInfoList
																							.isEmpty()) {
																				this.saveCompanyGrowth(
																						companyGrowthInfoList);
																			}
																		}
																	} else if (i == 3) {
																		companyFunctionalGrowthTableEle = (Element) tableEle
																				.get(i);
																		if (companyFunctionalGrowthTableEle != null) {
																			functionGrowthInfoList = this
																					.getEmployeeFunctionHeadCount(
																							companyFunctionalGrowthTableEle,
																							companyId);
																			if (functionGrowthInfoList != null
																					&& !functionGrowthInfoList
																							.isEmpty()) {
																				this.saveFunctionGrowth(
																						functionGrowthInfoList);
																			}
																		}
																	} else if (i == 5) {
																		newHireGraphTableEle = (Element) tableEle
																				.get(i);
																		if (newHireGraphTableEle != null) {
																			newHireGrowthInfoList = this
																					.getNewHireGrowthInfo(
																							newHireGraphTableEle,
																							companyId);
																			if (newHireGrowthInfoList != null
																					&& !newHireGrowthInfoList
																							.isEmpty()) {
																				this.saveNewHireGrowth(
																						newHireGrowthInfoList);
																			}
																		}
																	} else if (i == 7) {
																		companyFunctionalJobGrowthTableEle = (Element) tableEle
																				.get(i);
																		if (companyFunctionalJobGrowthTableEle != null) {
																			jobGrowthInfoList = this.getJobGrowthInfo(
																					companyFunctionalJobGrowthTableEle,
																					companyId);
																			if (jobGrowthInfoList != null
																					&& !jobGrowthInfoList.isEmpty()) {
																				this.saveJobGrowthInfo(
																						jobGrowthInfoList);
																			}
																			this.paseGenericService
																					.updateDailyCrawlingStatus(
																							crawlingStatusInfo);
																		}
																	}

																} catch (Exception e) {
																	// LOGGER.info("Exception
																	// -
																	// runService
																	// of
																	// LinkedinInsightsServiceImpl");
																	final long lineNumber = Thread.currentThread()
																			.getStackTrace()[2].getLineNumber();
																	this.paseGenericService
																			.saveException(new ExceptionVO(companyId,
																					PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID,
																					"runService", lineNumber,
																					e.getMessage(), "",
																					ExceptionUtil
																							.determineExceptionCodeByException(
																									e)));
																}
															}
														}
													}
												}
												Writer writer = new FileWriter("C:\\Linkedin Insights 2020\\March\\03.19.2020-tracxn\\"
														+ linkedinBO.getPaseID() + ".html");
												bufferedWriter = new BufferedWriter(writer);
												bufferedWriter.write(resp);
												bufferedWriter.close();
											}
										}
									}
								} catch (Exception e) {
									// LOGGER.info("Exception - runService of
									// LinkedinInsightsServiceImpl");
									final long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
									this.paseGenericService.saveException(new ExceptionVO(0l,
											PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID,
											"runService", lineNumber, e.getMessage(), "",
											ExceptionUtil.determineExceptionCodeByException(e)));
								}
								// }
							}
						}
					}
				}
			} catch (Exception e) {
				// LOGGER.info("Exception - runService of
				// LinkedinInsightsServiceImpl");
				final long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
				this.paseGenericService.saveException(new ExceptionVO(0l,
						PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID, "runService",
						lineNumber, e.getMessage(), "", ExceptionUtil.determineExceptionCodeByException(e)));
			}
			final Date serviceEndDateTime = new Date();
			String duration = this.paseGenericService.getDurationForService(serviceStartDateTime, serviceEndDateTime);
			serviceStatusInfo.setEndDateTime(serviceEndDateTime);
			serviceStatusInfo.setDuration(duration);
			serviceStatusInfo.setStatus("Completed");
			this.serviceStatusDAO.updateServiceDetails(serviceStatusInfo, serviceStatusId);
		} catch (Exception e) {
			// LOGGER.info("Exception - runService of
			// LinkedinInsightsServiceImpl");
			final long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			this.paseGenericService.saveException(new ExceptionVO(0l,
					PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID, "runService", lineNumber,
					e.getMessage(), "", ExceptionUtil.determineExceptionCodeByException(e)));
		}
		return status;
	
	}


	@Override
	public Document parseHtmlFile(String resp,Long companyId) 
	{
		Document doc=null;
		try
		{
			//companyId=Long.parseLong(file.getPath().replace(PASEConstants.folder1, "").replace(".html", ""));
			doc=Jsoup.parse(resp, "UTF-8");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//LOGGER.info("Exception - parseHtmlFile of LinkedinInsightsServiceImpl");
			final long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			this.paseGenericService.saveException(
					new ExceptionVO(companyId, PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID, "parseHtmlFile", lineNumber,
							e.getMessage(), "", ExceptionUtil.determineExceptionCodeByException(e)));
		}
		return doc;
	}


	@Override
	public boolean getInsigthsEmployeeGrowth(Element element,long companyId) 
	{
		boolean flag=false;
		Elements trTdEle=null;
		long employeeCount=0l;
		int sixMonthPercentage=0;
		int oneYearPercentage=0;
		int twoYearPercentage=0;
		LinkedinEmployeeGrowth employeeGrowth=null;
		try
		{
			final DateFormat utcFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
			utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			utcFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			final SimpleDateFormat activityDateSdf = new SimpleDateFormat("yyyy-MM-dd");
			final String activityDateStr = activityDateSdf.format(new Date());

			trTdEle=element.select("tr td");
			if(trTdEle!=null && !trTdEle.isEmpty())
			{
				employeeCount=Long.parseLong(trTdEle.get(0).text().replace(",", ""));
				String sixMonthsGrowth=trTdEle.get(1).text();
				if(sixMonthsGrowth.contains("No change"))
				{
					sixMonthsGrowth=sixMonthsGrowth.substring(0,sixMonthsGrowth.lastIndexOf("%"));

				}else
				{
					sixMonthsGrowth=sixMonthsGrowth.substring(sixMonthsGrowth.indexOf("%")+1,sixMonthsGrowth.lastIndexOf("%"));
				}
				String oneYearGrowth=trTdEle.get(2).text();
				if(oneYearGrowth.contains("No change"))
				{
					oneYearGrowth=oneYearGrowth.substring(0,oneYearGrowth.lastIndexOf("%"));
				}
				else
				{
					oneYearGrowth=oneYearGrowth.substring(oneYearGrowth.indexOf("%")+1,oneYearGrowth.lastIndexOf("%"));
				}
				String twoYearGrowth=trTdEle.get(3).text();
				if(twoYearGrowth.contains("No change"))
				{
					twoYearGrowth=twoYearGrowth.substring(0,twoYearGrowth.lastIndexOf("%"));
				}
				else
				{
					twoYearGrowth=twoYearGrowth.substring(twoYearGrowth.indexOf("%")+1,twoYearGrowth.lastIndexOf("%"));
				}
				String[] yearMonth=activityDateStr.split("-");
				int year=Integer.parseInt(yearMonth[0].toString());
				int month=Integer.parseInt(yearMonth[1].toString());
				int day=Integer.parseInt(yearMonth[2].toString());
				employeeGrowth=new LinkedinEmployeeGrowth();
				sixMonthPercentage=Integer.parseInt(sixMonthsGrowth.replace(",", "").replace("+", "").trim());
				oneYearPercentage=Integer.parseInt(oneYearGrowth.replace(",", "").replace("+", "").trim());
				twoYearPercentage=Integer.parseInt(twoYearGrowth.replace(",", "").replace("+", "").trim());
				employeeGrowth.setCompanyId(companyId);
				employeeGrowth.setEmployeeCount(employeeCount);
				employeeGrowth.setSixMonthsChange(sixMonthPercentage);
				employeeGrowth.setOneYearChange(oneYearPercentage);
				employeeGrowth.setTwoYearChange(twoYearPercentage);
				employeeGrowth.setYear(year);
				employeeGrowth.setMonth(month);
				employeeGrowth.setDay(day);
				employeeGrowth.setActivity_datetime(new Date());
				employeeGrowth.setActivity_date(activityDateStr);
				employeeGrowth.setFkSourceId(PASEConstants.LINKEDIN_SOURCE_ID);
				employeeGrowth.setActivity_datetime_temp(activityDateSdf.parse(activityDateStr));
				if(employeeGrowth!=null)
					this.saveEmployeeGrowth(employeeGrowth);
			}
		}
		catch(Exception e)
		{
			//LOGGER.info("Exception - getInsigthsEmployeeGrowth of LinkedinInsightsServiceImpl");
			final long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			this.paseGenericService.saveException(
					new ExceptionVO(companyId, PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID, "getInsigthsEmployeeGrowth", lineNumber,
							e.getMessage(), "", ExceptionUtil.determineExceptionCodeByException(e)));
		}
		return flag;
	}


	@Override
	public boolean saveEmployeeGrowth(LinkedinEmployeeGrowth employeeGrowth) 
	{
		boolean flag=false;
		LinkedinEmployeeGrowth employeeGrowthInfo=null;
		try
		{
			employeeGrowthInfo=this.linkedinInsightsDAO.checkEmployeeGrowthInfoExistance(employeeGrowth);
			if(employeeGrowthInfo==null)
				flag=this.linkedinInsightsDAO.save(employeeGrowth);
			else
				this.linkedinInsightsDAO.updateEmployeeGrowth(employeeGrowth);
		}
		catch(Exception e)
		{
			//LOGGER.info("Exception - saveEmployeeGrowth of LinkedinInsightsServiceImpl");
			final long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			this.paseGenericService.saveException(
					new ExceptionVO(0l, PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID, "saveEmployeeGrowth", lineNumber,
							e.getMessage(), "", ExceptionUtil.determineExceptionCodeByException(e)));
		}
		return flag;
	}


	@Override
	public List<CompanyGrowthInfo> getCompanyGrowthInfo(Element element, long companyId) 
	{
		Elements trEle=null;
		Element childTr=null;
		Elements tdEle=null;
		String date="";
		String[] yearMonth=null;
		int month=0;
		int year=0;
		long empCount=0l;
		List<CompanyGrowthInfo> companyGrowthInfoList=null;
		CompanyGrowthInfo companyGrowthInfo=null;
		try
		{

			final DateFormat utcFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
			utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			utcFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			final SimpleDateFormat activityDateSdf = new SimpleDateFormat("yyyy-MM-dd");
			final String activityDateStr = activityDateSdf.format(new Date());

			companyGrowthInfoList=new ArrayList<>();
			trEle=element.select("tbody tr");
			if(trEle!=null)
			{
				for(int i=0;i<trEle.size();i++)
				{
					try
					{
						childTr=trEle.get(i);
						if(childTr!=null)
						{
							tdEle=childTr.select("td");
							if(tdEle!=null)
							{
								date=tdEle.get(0).text();
								if(!date.equals(""))
								{
									yearMonth=date.split(" ");
									String month1=yearMonth[0];
									if(!month1.equals(""))
									{
										MonthConversion conversion=new MonthConversion();
										month=conversion.StringToInt(month1);
									}
									year=Integer.parseInt(yearMonth[1]); 
								}
								String[] day1=activityDateStr.split("-");
								int day=Integer.parseInt(day1[2].toString());
								empCount= Long.parseLong(tdEle.get(1).text().replace(",", ""));
								companyGrowthInfo=new CompanyGrowthInfo();
								companyGrowthInfo.setCompanyId(companyId);
								companyGrowthInfo.setEmployeeCount(empCount);
								companyGrowthInfo.setMonth(month);
								companyGrowthInfo.setYear(year);
								companyGrowthInfo.setDay(day);
								companyGrowthInfo.setActivity_datetime(new Date());
								companyGrowthInfo.setActivity_date(activityDateStr);
								//companyGrowthInfo.setFkSourceId(PASEConstants.LINKEDIN_SOURCE_ID);
								companyGrowthInfo.setActivity_datetime_temp(activityDateSdf.parse(activityDateStr));
								companyGrowthInfoList.add(companyGrowthInfo);

							}
						}
					}
					catch(Exception e)
					{
						//LOGGER.info("Exception - getCompanyGrowthInfo of LinkedinInsightsServiceImpl");
						final long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
						this.paseGenericService.saveException(
								new ExceptionVO(companyId, PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID, "getCompanyGrowthInfo", lineNumber,
										e.getMessage(), "", ExceptionUtil.determineExceptionCodeByException(e)));
					}
				}
			}
		}
		catch(Exception e)
		{
			//LOGGER.info("Exception - getCompanyGrowthInfo of LinkedinInsightsServiceImpl");
			final long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			this.paseGenericService.saveException(
					new ExceptionVO(companyId, PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID, "getCompanyGrowthInfo", lineNumber,
							e.getMessage(), "", ExceptionUtil.determineExceptionCodeByException(e)));
		}
		return companyGrowthInfoList;
	}


	@Override
	public boolean saveCompanyGrowth(List<CompanyGrowthInfo> companyGrowthInfo) 
	{
		try
		{
			for(CompanyGrowthInfo companyGrowth:companyGrowthInfo)
			{
				try
				{
					CompanyGrowthInfo CompanyGrowthInfo2=this.checkCompanyGrowthInfoExistance(companyGrowth, companyGrowth.getCompanyId());
					if(CompanyGrowthInfo2==null)
						this.linkedinInsightsDAO.saveCompanyGrowthInfo(companyGrowth);
					else
						this.linkedinInsightsDAO.updateEmployeeCount(companyGrowth);

				}
				catch(Exception e)				
				{
					//LOGGER.info("Exception - saveCompanyGrowth of LinkedinInsightsServiceImpl");
					final long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
					this.paseGenericService.saveException(
							new ExceptionVO(companyGrowthInfo.get(0).getCompanyId(), PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID, "saveCompanyGrowth", lineNumber,
									e.getMessage(), "", ExceptionUtil.determineExceptionCodeByException(e)));
				}
			}
		}
		catch(Exception e)
		{
			//LOGGER.info("Exception - saveCompanyGrowth of LinkedinInsightsServiceImpl");
			final long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			this.paseGenericService.saveException(
					new ExceptionVO(companyGrowthInfo.get(0).getCompanyId(), PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID, "saveCompanyGrowth", lineNumber,
							e.getMessage(), "", ExceptionUtil.determineExceptionCodeByException(e)));
		}

		return false;
	}



	@Override
	public CompanyGrowthInfo checkCompanyGrowthInfoExistance(CompanyGrowthInfo companyGrowthInfo, long companyId) {

		//LOGGER.info("Start - checkCompanyGrowthInfoExistance of LinkedinInsightsServiceImpl");

		try {

			companyGrowthInfo = this.linkedinInsightsDAO
					.checkCompanyGrowthInfoExistance(companyGrowthInfo);

			//LOGGER.info("End - checkCompanyGrowthInfoExistance of LinkedinInsightsServiceImpl");

		} catch (Exception excp) {
			companyGrowthInfo = null;
			//LOGGER.error("Exception - checkCompanyGrowthInfoExistance of LinkedinInsightsServiceImpl");
			final long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			this.paseGenericService.saveException(new ExceptionVO(companyId,
					PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID, "checkCompanyGrowthInfoExistance",
					lineNumber, excp.getMessage(), "", ExceptionUtil.determineExceptionCodeByException(excp)));

		}
		return companyGrowthInfo;
	}


	@Override
	public boolean getEmployeeCount(Document document,long companyId) 
	{
		boolean flag=false;
		long empCount=0l;
		String empCount1="";
		Elements divEle=null;
		CompanyGrowthInfo companyGrowthInfo=null;
		try
		{

			final DateFormat utcFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
			utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			utcFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			final SimpleDateFormat activityDateSdf = new SimpleDateFormat("yyyy-MM-dd");
			final String activityDateStr = activityDateSdf.format(new Date());

			divEle=document.select("div[class=mt2]");
			if(divEle!=null)
			{
				empCount1=divEle.text();
				if(!empCount1.equals(""))
				{
					empCount1=empCount1.toLowerCase().trim().replace("see all", "").replace("employees on linkedin", "").replace("see ", "").replace("employee on linkedin", "");
					String[] yearMonth=activityDateStr.split("-");
					int year=Integer.parseInt(yearMonth[0].toString());
					int month=Integer.parseInt(yearMonth[1].toString());
					int day=Integer.parseInt(yearMonth[2].toString());
					empCount= Long.parseLong(empCount1.trim().replace(",", ""));
					companyGrowthInfo=new CompanyGrowthInfo();
					companyGrowthInfo.setCompanyId(companyId);
					companyGrowthInfo.setEmployeeCount(empCount);
					companyGrowthInfo.setMonth(month);
					companyGrowthInfo.setYear(year);
					companyGrowthInfo.setDay(day);
					companyGrowthInfo.setActivity_datetime(new Date());
					companyGrowthInfo.setActivity_date(activityDateStr);
					//companyGrowthInfo.setFkSourceId(PASEConstants.LINKEDIN_SOURCE_ID);
					companyGrowthInfo.setActivity_datetime_temp(activityDateSdf.parse(activityDateStr));
					CompanyGrowthInfo CompanyGrowthInfo2=this.checkCompanyGrowthInfoExistance(companyGrowthInfo, companyGrowthInfo.getCompanyId());
					if(CompanyGrowthInfo2==null)
						flag=this.linkedinInsightsDAO.saveCompanyGrowthInfo(companyGrowthInfo);

				}
			}
		}
		catch(Exception e)
		{
			flag=false;
			//LOGGER.info("Exception - getEmployeeCount of LinkedinInsightsServiceImpl");
			final long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			this.paseGenericService.saveException(
					new ExceptionVO(companyId, PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID, "getEmployeeCount", lineNumber,
							e.getMessage(), "", ExceptionUtil.determineExceptionCodeByException(e)));
		}

		return flag;
	}


	@Override
	public List<FunctionGrowthInfo> getEmployeeFunctionHeadCount(Element element, long companyId) 
	{
		int employeeCount=0;
		int sixMonthGrowth=0;
		int oneYearGrowth=0;
		int totalHeadCountGrowth=0;
		Elements tbodyTrEle=null;
		Elements tdEle=null;
		FunctionGrowthInfo functionGrowthInfo=null;
		List<FunctionGrowthInfo> functionGrowthInfoList=null;
		try
		{
			functionGrowthInfoList=new ArrayList<>();

			final DateFormat utcFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
			utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			utcFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			final SimpleDateFormat activityDateSdf = new SimpleDateFormat("yyyy-MM-dd");
			final String activityDateStr = activityDateSdf.format(new Date());

			tbodyTrEle=element.select("tbody tr");
			if(tbodyTrEle!=null)
			{
				for(int i=1;i<tbodyTrEle.size();i++)
				{
					try
					{
						tdEle=tbodyTrEle.get(i).select("td");
						if(tdEle!=null)
						{
							String functionName=tdEle.get(0).text();
							String numberOfEmployees=tdEle.get(1).text();
							String totalHeadCountPercent=tdEle.get(2).text();

							if(!numberOfEmployees.equals(""))
								employeeCount=Integer.parseInt(numberOfEmployees.replace(",", ""));
							if(!totalHeadCountPercent.equals(""))
								totalHeadCountGrowth=Integer.parseInt(totalHeadCountPercent.replace("%", "").trim());
							String sixmonth=tdEle.get(3).text();
							if(!sixmonth.equals(""))
							{
								if(sixmonth.contains("No change"))
								{
									sixmonth=sixmonth.substring(0,sixmonth.lastIndexOf("%"));

								}else
								{
									sixmonth=sixmonth.substring(sixmonth.indexOf("%")+1,sixmonth.lastIndexOf("%"));
								}
							}
							String oneYear=tdEle.get(4).text();
							if(!oneYear.equals(""))
							{
								if(oneYear.contains("No change"))
								{
									oneYear=oneYear.substring(0,oneYear.lastIndexOf("%"));
								}
								else
								{
									oneYear=oneYear.substring(oneYear.indexOf("%")+1,oneYear.lastIndexOf("%"));
								}
							}
							sixMonthGrowth=Integer.parseInt(sixmonth.trim());
							oneYearGrowth=Integer.parseInt(oneYear.trim());

							functionGrowthInfo=new FunctionGrowthInfo();
							if(!functionName.equals(""))
								functionGrowthInfo.setFunctionName(functionName);
							functionGrowthInfo.setCompanyId(companyId);
							functionGrowthInfo.setSixMonthGrowth(sixMonthGrowth);
							functionGrowthInfo.setOneYearGrowth(oneYearGrowth);
							functionGrowthInfo.setNoOfEmployees(employeeCount);
							functionGrowthInfo.setTotalHeadCountPercentage(totalHeadCountGrowth);
							String[] yearMonth=activityDateStr.split("-");
							int year=Integer.parseInt(yearMonth[0].toString());
							int month=Integer.parseInt(yearMonth[1].toString());
							int day=Integer.parseInt(yearMonth[2].toString());
							functionGrowthInfo.setMonth(month);
							functionGrowthInfo.setYear(year);
							functionGrowthInfo.setDay(day);
							functionGrowthInfo.setActivity_datetime(new Date());
							functionGrowthInfo.setActivity_date(activityDateStr);
							//companyGrowthInfo.setFkSourceId(PASEConstants.LINKEDIN_SOURCE_ID);
							functionGrowthInfo.setActivity_datetime_temp(activityDateSdf.parse(activityDateStr));
							functionGrowthInfoList.add(functionGrowthInfo);
						}
					}
					catch(Exception e)
					{
						//LOGGER.info("Exception - getEmployeeFunctionHeadCount of LinkedinInsightsServiceImpl");
						final long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
						this.paseGenericService.saveException(
								new ExceptionVO(companyId, PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID, "getEmployeeFunctionHeadCount", lineNumber,
										e.getMessage(), "", ExceptionUtil.determineExceptionCodeByException(e)));
					}
				}
			}
		}
		catch(Exception e)
		{
			//LOGGER.info("Exception - getEmployeeFunctionHeadCount of LinkedinInsightsServiceImpl");
			final long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			this.paseGenericService.saveException(
					new ExceptionVO(companyId, PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID, "getEmployeeFunctionHeadCount", lineNumber,
							e.getMessage(), "", ExceptionUtil.determineExceptionCodeByException(e)));
		}
		return functionGrowthInfoList;
	}


	@Override
	public boolean saveFunctionGrowth(List<FunctionGrowthInfo> functionGrowthInfo) 
	{
		try
		{
			for(FunctionGrowthInfo functionInfo:functionGrowthInfo)
			{
				try
				{
					FunctionGrowthInfo CompanyGrowthInfo2=this.checkFunctionGrowthInfoExistance(functionInfo, functionInfo.getCompanyId());
					if(CompanyGrowthInfo2==null)
						this.linkedinInsightsDAO.saveFunctionGrowthInfo(functionInfo);
				}
				catch(Exception e)
				{
					//LOGGER.info("Exception - saveFunctionGrowth of LinkedinInsightsServiceImpl");
					final long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
					this.paseGenericService.saveException(
							new ExceptionVO(functionGrowthInfo.get(0).getCompanyId(), PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID, "saveFunctionGrowth", lineNumber,
									e.getMessage(), "", ExceptionUtil.determineExceptionCodeByException(e)));
				}
			}
		}
		catch(Exception e)
		{
			//LOGGER.info("Exception - saveFunctionGrowth of LinkedinInsightsServiceImpl");
			final long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			this.paseGenericService.saveException(
					new ExceptionVO(functionGrowthInfo.get(0).getCompanyId(), PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID, "saveFunctionGrowth", lineNumber,
							e.getMessage(), "", ExceptionUtil.determineExceptionCodeByException(e)));
		}
		return false;
	}


	@Override
	public FunctionGrowthInfo checkFunctionGrowthInfoExistance(FunctionGrowthInfo functionGrowthInfo, long companyId) 
	{
		//LOGGER.info("Start - checkFunctionGrowthInfoExistance of LinkedinInsightsServiceImpl");

		try {

			functionGrowthInfo = this.linkedinInsightsDAO.checkFunctionGrowthInfoExistance(functionGrowthInfo);

			//LOGGER.info("End - checkFunctionGrowthInfoExistance of LinkedinInsightsServiceImpl");

		} catch (Exception excp) {
			functionGrowthInfo = null;
			//LOGGER.error("Exception - checkFunctionGrowthInfoExistance of LinkedinInsightsServiceImpl");
			final long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			this.paseGenericService.saveException(new ExceptionVO(companyId,
					PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID, "checkFunctionGrowthInfoExistance",
					lineNumber, excp.getMessage(), "", ExceptionUtil.determineExceptionCodeByException(excp)));

		}
		return functionGrowthInfo;
	}


	@Override
	public List<NewHiresGrowthInfo> getNewHireGrowthInfo(Element element, long companyId) 
	{
		Elements trEle=null;
		Element childTr=null;
		Elements tdEle=null;
		String date="";
		String[] yearMonth=null;
		int month=0;
		int year=0;
		int numberOfSeniorHires=0;
		int numberOfOtherHires=0;
		NewHiresGrowthInfo newHiresGrowthInfo=null;
		List<NewHiresGrowthInfo> newHiresGrowthInfoList=null;
		try
		{
			newHiresGrowthInfoList=new ArrayList<>();

			final DateFormat utcFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
			utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			utcFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			final SimpleDateFormat activityDateSdf = new SimpleDateFormat("yyyy-MM-dd");
			final String activityDateStr = activityDateSdf.format(new Date());

			trEle=element.select("tbody tr");
			if(trEle!=null)
			{
				for(int i=0;i<trEle.size();i++)
				{
					try
					{
						childTr=trEle.get(i);
						if(childTr!=null)
						{
							tdEle=childTr.select("td");
							if(tdEle!=null)
							{
								date=tdEle.get(0).text();
								if(!date.equals(""))
								{
									yearMonth=date.split(" ");
									String month1=yearMonth[0];
									if(!month1.equals(""))
									{
										MonthConversion conversion=new MonthConversion();
										month=conversion.StringToInt(month1);
									}
									year=Integer.parseInt(yearMonth[1]);
								}
								String[] day1=activityDateStr.split("-");
								int day=Integer.parseInt(day1[2].toString());
								if(!tdEle.get(1).text().equals(""))
								{
									numberOfSeniorHires= Integer.parseInt(tdEle.get(1).text().replace(",", ""));
								}
								else
								{
									numberOfSeniorHires=0;
								}

								if(!tdEle.get(2).text().equals(""))
								{
									numberOfOtherHires= Integer.parseInt(tdEle.get(2).text().replace(",", ""));
								}
								else
								{
									numberOfOtherHires=0;
								}

								newHiresGrowthInfo=new NewHiresGrowthInfo();
								newHiresGrowthInfo.setCompanyId(companyId);
								newHiresGrowthInfo.setNumberOfSeniorHires(numberOfSeniorHires);
								newHiresGrowthInfo.setNumberOfOtherHires(numberOfOtherHires);
								newHiresGrowthInfo.setMonth(month);
								newHiresGrowthInfo.setYear(year);
								newHiresGrowthInfo.setDay(day);
								newHiresGrowthInfo.setActivity_datetime(new Date());
								newHiresGrowthInfo.setActivity_date(activityDateStr);
								//companyGrowthInfo.setFkSourceId(PASEConstants.LINKEDIN_SOURCE_ID);
								newHiresGrowthInfo.setActivity_datetime_temp(activityDateSdf.parse(activityDateStr));
								newHiresGrowthInfoList.add(newHiresGrowthInfo);

							}
						}
					}
					catch(Exception e)
					{
						//LOGGER.error("Exception - getNewHireGrowthInfo of LinkedinInsightsServiceImpl");
						final long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
						this.paseGenericService.saveException(new ExceptionVO(companyId,
								PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID, "getNewHireGrowthInfo",
								lineNumber, e.getMessage(), "", ExceptionUtil.determineExceptionCodeByException(e)));
					}
				}
			}
		}
		catch(Exception e)
		{
			//LOGGER.error("Exception - getNewHireGrowthInfo of LinkedinInsightsServiceImpl");
			final long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			this.paseGenericService.saveException(new ExceptionVO(companyId,
					PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID, "getNewHireGrowthInfo",
					lineNumber, e.getMessage(), "", ExceptionUtil.determineExceptionCodeByException(e)));
		}
		return newHiresGrowthInfoList;
	}


	@Override
	public boolean saveNewHireGrowth(List<NewHiresGrowthInfo> newHiresGrowthInfo) 
	{
		try
		{
			for(NewHiresGrowthInfo hireGrowthInfo:newHiresGrowthInfo)
			{
				try
				{
					NewHiresGrowthInfo newHiresGrowthInfo1=this.checkNewHireGrowthInfoExistance(hireGrowthInfo, hireGrowthInfo.getCompanyId());
					if(newHiresGrowthInfo1==null)
						this.linkedinInsightsDAO.saveNewHireGrowthInfo(hireGrowthInfo);
				}
				catch(Exception e)
				{
					//LOGGER.info("Exception - saveNewHireGrowth of LinkedinInsightsServiceImpl");
					final long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
					this.paseGenericService.saveException(
							new ExceptionVO(newHiresGrowthInfo.get(0).getCompanyId(), PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID, "saveNewHireGrowth", lineNumber,
									e.getMessage(), "", ExceptionUtil.determineExceptionCodeByException(e)));
				}
			}
		}
		catch(Exception e)
		{
			//LOGGER.info("Exception - saveNewHireGrowth of LinkedinInsightsServiceImpl");
			final long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			this.paseGenericService.saveException(
					new ExceptionVO(newHiresGrowthInfo.get(0).getCompanyId(), PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID, "saveNewHireGrowth", lineNumber,
							e.getMessage(), "", ExceptionUtil.determineExceptionCodeByException(e)));
		}
		return false;
	}


	@Override
	public NewHiresGrowthInfo checkNewHireGrowthInfoExistance(NewHiresGrowthInfo newHiresGrowthInfo, long companyId) 
	{
		//LOGGER.info("Start - checkNewHireGrowthInfoExistance of LinkedinInsightsServiceImpl");

		try {

			newHiresGrowthInfo = this.linkedinInsightsDAO.checkNewHiresGrowthInfoExistance(newHiresGrowthInfo);

			//LOGGER.info("End - checkNewHireGrowthInfoExistance of LinkedinInsightsServiceImpl");

		} catch (Exception excp) {
			newHiresGrowthInfo = null;
			//LOGGER.error("Exception - checkNewHireGrowthInfoExistance of LinkedinInsightsServiceImpl");
			final long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			this.paseGenericService.saveException(new ExceptionVO(companyId,
					PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID, "checkNewHireGrowthInfoExistance",
					lineNumber, excp.getMessage(), "", ExceptionUtil.determineExceptionCodeByException(excp)));

		}
		return newHiresGrowthInfo;
	}


	@Override
	public List<JobGrowthInfo> getJobGrowthInfo(Element element, long companyId) 
	{
		Elements tbodyTrEle=null;
		Elements tdEle=null;
		JobGrowthInfo jobGrowthInfo=null;
		List<JobGrowthInfo> jobGrowthInfoList=null;
		try
		{
			jobGrowthInfoList=new ArrayList<>();

			final DateFormat utcFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
			utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			utcFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			final SimpleDateFormat activityDateSdf = new SimpleDateFormat("yyyy-MM-dd");
			final String activityDateStr = activityDateSdf.format(new Date());

			tbodyTrEle=element.select("tbody tr");
			if(tbodyTrEle!=null)
			{
				for(int i=1;i<tbodyTrEle.size();i++)
				{
					int employeeCount=0;
					int threeMonthGrowth=0;
					int sixMonthGrowth=0;
					int oneYearGrowth=0;
					int totalHeadCountGrowth=0;
					try
					{
						tdEle=tbodyTrEle.get(i).select("td");
						if(tdEle!=null)
						{
							String functionName=tdEle.get(0).text();
							String numberOfEmployees=tdEle.get(1).text();
							String totalHeadCountPercent=tdEle.get(2).text();

							if(!numberOfEmployees.equals(""))
							{
								employeeCount=Integer.parseInt(numberOfEmployees.replace(",", ""));
							}
							if(!totalHeadCountPercent.equals(""))
							{
								totalHeadCountGrowth=Integer.parseInt(totalHeadCountPercent.replace("%", "").trim());
							}
							String threeMonth=tdEle.get(3).text();
							if(!threeMonth.equals(""))
							{
								if(threeMonth.contains("No change"))
								{
									threeMonth=threeMonth.substring(0,threeMonth.trim().lastIndexOf("%"));

								}else
								{
									threeMonth=threeMonth.substring(threeMonth.trim().indexOf("%")+1,threeMonth.trim().lastIndexOf("%"));
								}
							}


							String sixmonth=tdEle.get(4).text();
							if(!sixmonth.equals(""))
							{
								if(sixmonth.contains("No change"))
								{
									sixmonth=sixmonth.substring(0,sixmonth.trim().lastIndexOf("%"));

								}else
								{
									sixmonth=sixmonth.substring(sixmonth.trim().indexOf("%")+1,sixmonth.trim().lastIndexOf("%"));
								}
							}
							String oneYear=tdEle.get(5).text();
							if(!oneYear.equals(""))
							{
								if(oneYear.contains("No change"))
								{
									oneYear=oneYear.substring(0,oneYear.trim().lastIndexOf("%"));
								}
								else
								{
									oneYear=oneYear.substring(oneYear.trim().indexOf("%")+1,oneYear.trim().lastIndexOf("%"));
								}
							}
							threeMonthGrowth=Integer.parseInt(threeMonth.replace(",", "").replace("+", "").trim());
							sixMonthGrowth=Integer.parseInt(sixmonth.replace(",", "").replace("+", "").trim());
							oneYearGrowth=Integer.parseInt(oneYear.replace(",", "").replace("+", "").trim());


							jobGrowthInfo=new JobGrowthInfo();
							if(!functionName.equals(""))
								jobGrowthInfo.setFunctionName(functionName);
							jobGrowthInfo.setCompanyId(companyId);
							jobGrowthInfo.setThreeMonthGrowth(threeMonthGrowth);
							jobGrowthInfo.setSixMonthGrowth(sixMonthGrowth);
							jobGrowthInfo.setOneYearGrowth(oneYearGrowth);
							jobGrowthInfo.setNoOfEmployees(employeeCount);
							jobGrowthInfo.setTotalHeadCountPercentage(totalHeadCountGrowth);
							String[] yearMonth=activityDateStr.split("-");
							int year=Integer.parseInt(yearMonth[0].toString());
							int month=Integer.parseInt(yearMonth[1].toString());
							int day=Integer.parseInt(yearMonth[2].toString());
							jobGrowthInfo.setMonth(month);
							jobGrowthInfo.setYear(year);
							jobGrowthInfo.setDay(day);
							jobGrowthInfo.setActivity_datetime(new Date());
							jobGrowthInfo.setActivity_date(activityDateStr);
							//companyGrowthInfo.setFkSourceId(PASEConstants.LINKEDIN_SOURCE_ID);
							jobGrowthInfo.setActivity_datetime_temp(activityDateSdf.parse(activityDateStr));
							jobGrowthInfoList.add(jobGrowthInfo);
						}
					}
					catch(Exception e)
					{
						//LOGGER.info("Exception - getJobGrowthInfo of LinkedinInsightsServiceImpl");
						final long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
						this.paseGenericService.saveException(
								new ExceptionVO(companyId, PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID, "getJobGrowthInfo", lineNumber,
										e.getMessage(), "", ExceptionUtil.determineExceptionCodeByException(e)));
					}
				}
			}
		}
		catch(Exception e)
		{
			//LOGGER.info("Exception - getJobGrowthInfo of LinkedinInsightsServiceImpl");
			final long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			this.paseGenericService.saveException(
					new ExceptionVO(companyId, PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID, "getJobGrowthInfo", lineNumber,
							e.getMessage(), "", ExceptionUtil.determineExceptionCodeByException(e)));
		}
		return jobGrowthInfoList;
	}


	@Override
	public boolean saveJobGrowthInfo(List<JobGrowthInfo> jobGrowthInfo) 
	{
		try
		{
			for(JobGrowthInfo jobInfo:jobGrowthInfo)
			{
				try
				{
					JobGrowthInfo JobGrowthInfo1=this.checkJobGrowthInfoExistance(jobInfo, jobInfo.getCompanyId());
					if(JobGrowthInfo1==null)
						this.linkedinInsightsDAO.saveJobInfo(jobInfo);
				}
				catch(Exception e)
				{
					//LOGGER.info("Exception - saveJobGrowthInfo of LinkedinInsightsServiceImpl");
					final long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
					this.paseGenericService.saveException(
							new ExceptionVO(jobGrowthInfo.get(0).getCompanyId(), PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID, "saveJobGrowthInfo", lineNumber,
									e.getMessage(), "", ExceptionUtil.determineExceptionCodeByException(e)));
				}
			}
		}
		catch(Exception e)
		{
			//LOGGER.info("Exception - saveJobGrowthInfo of LinkedinInsightsServiceImpl");
			final long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			this.paseGenericService.saveException(
					new ExceptionVO(jobGrowthInfo.get(0).getCompanyId(), PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID, "saveJobGrowthInfo", lineNumber,
							e.getMessage(), "", ExceptionUtil.determineExceptionCodeByException(e)));
		}
		return false;
	}

	@Override
	public JobGrowthInfo checkJobGrowthInfoExistance(JobGrowthInfo jobGrowthInfo, long companyId) 
	{
		//LOGGER.info("Start - checkJobGrowthInfoExistance of LinkedinInsightsServiceImpl");

		try {

			jobGrowthInfo = this.linkedinInsightsDAO.checkJobInfoExistance(jobGrowthInfo);

			//LOGGER.info("End - checkJobGrowthInfoExistance of LinkedinInsightsServiceImpl");

		} catch (Exception excp) {
			jobGrowthInfo = null;
			//LOGGER.error("Exception - checkJobGrowthInfoExistance of LinkedinInsightsServiceImpl");
			final long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			this.paseGenericService.saveException(new ExceptionVO(companyId,
					PASEConstants.LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID, "checkJobGrowthInfoExistance",
					lineNumber, excp.getMessage(), "", ExceptionUtil.determineExceptionCodeByException(excp)));

		}
		return jobGrowthInfo;
	}



	public WebDriver setChromeDriver() 
	{	
		WebDriver driver = null;
		ChromeOptions options =null;
		try
		{
			options = new ChromeOptions();
			options.addArguments("--headless");
			options.addArguments("--disable-gpu");
			options.addArguments("--allow-insecure-localhost");
			options.addArguments("--allow-running-insecure-content");
			options.addArguments("--ignore-certificate-errors");
			options.addArguments("--no-sandbox");
			// options.addArguments("--start-maximized");
			options.addArguments("--window-size=2000,6000");
			options.setPageLoadStrategy(PageLoadStrategy.NONE);
			options.setCapability("acceptSslCerts", true);
			options.setCapability("acceptInsecureCerts", true);
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\akumar\\Desktop\\chromedriver_win32 (6)\\chromedriver.exe");
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
			Thread.sleep(10000);
			driver.findElement(By.cssSelector("input[name='session_key']")).sendKeys("asyed@ltu.edu");	
			Thread.sleep(10000);
			driver.findElement(By.cssSelector("input[name='session_password']")).sendKeys("Acpcrp123!");
			Thread.sleep(10000);
			driver.findElement(By.cssSelector("button[aria-label='Sign in']")).click();	
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);   
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return driver;
	}


	public WebDriver getCompanyPage(WebDriver driver,String linkedinURL)
	{
		String loginUrl ="";
		int scrollLimit = 15;
		if(linkedinURL.contains("?"))
			linkedinURL=linkedinURL.substring(0, linkedinURL.indexOf("?"));
		//String page="";


		if(linkedinURL.trim().contains("http://www.linkedin.com/") || linkedinURL.trim().contains("https://www.linkedin.com/"))
		{
			if(linkedinURL.endsWith("/"))
				loginUrl=linkedinURL+"insights/";
			else
				loginUrl=linkedinURL+"/insights/";
		}
		else
		{
			if(linkedinURL.endsWith("/"))
				loginUrl = "https://www.linkedin.com/company/"+linkedinURL+"insights/";
			else
				loginUrl = "https://www.linkedin.com/company/"+linkedinURL+"/insights/";
		}
		//String loginUrl = "https://www.linkedin.com/company/"+linkedinURL+"/about/";
		try
		{
			/*
			driver.get(loginUrl);
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(60, TimeUnit.SECONDS).pollingEvery(2, TimeUnit.SECONDS).ignoring(NoSuchElementException.class)
		            .ignoring(ElementNotVisibleException.class)
		            .ignoring(WebDriverException.class);
			By inputArea = By.xpath("//div[@id='ember5']");
			List <WebElement> myIput = driver.findElements(inputArea);
			if(!myIput.isEmpty())
			{
				wait.until(ExpectedConditions.visibilityOfAllElements(myIput));
			}
			waitForLoad(driver);
			Thread.sleep(10000);*/

			driver.navigate().to(loginUrl);
			Thread.sleep(10000);
			for (int i = scrollLimit; i >= 0; i--) 
			{
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("window.scrollBy(0, 1000)");

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

}

package com.aldrich.service;

import java.io.IOException;
import java.io.Reader;
import java.text.BreakIterator;
import java.util.Date;
import java.util.List;

import com.aldrich.entity.CrawlingStatus;
import com.aldrich.pase.vo.ExceptionVO;


public interface PaseGenericService
{
	/*public void saveGenericRssFeedsDetails(List<Object> feeds, int sourceId, int attemptCount,
			Integer subUrldailyStatusId, long serviceId);

	public String getDomainNameForURL(String url);

	public String readAll(Reader rd) throws IOException;

	public List<CompanyDetailsVO> getCompaniesDetailsByTypeId(Long typeId);*/

	public void saveException(ExceptionVO exceptionVO);
	
	public String getDurationForService(Date startTime, Date endTime);

	/*public String getDurationForService(Date startTime, Date endTime);

	public String getDomainName(String url);

	public UrlRedirectionDetailsVO getRedirectedUrlAndStatusCode(String url);

	public String getSentences(BreakIterator bi, String source);

	public boolean newsTriggerProcessDetails(RssFeeds rssFeed, long companyId, long serviceId);*/

	public CrawlingStatus saveDailyCrawlingStatus(long companyId, long serviceId, long batchId);

	public void updateDailyCrawlingStatus(CrawlingStatus crawlingStatusInfo);

	/*public String getBingWebSearchResults(String searchString, long companyId, long serviceId);

	public String getRedictedURL(String link, long companyId, String companyDomain);

	public String getResponseFromWebpage(String link, long companyId, String companyDomain);

	public String getWebContentForJSWebsites(String url);

	public List<BingSearchVO> invokeBingSearchAPI(BingSearchParamsVO params);*/
}

package com.aldrich.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aldrich.dao.CrawlingStatusDAO;
import com.aldrich.dao.ExceptionDAO;
import com.aldrich.entity.CrawlingStatus;
import com.aldrich.entity.ExceptionDetails;
import com.aldrich.pase.vo.ExceptionVO;
import com.aldrich.service.PaseGenericService;
import com.aldrich.util.PASEConstants;

@SuppressWarnings({ "nls", "boxing" })
@Service
public class PaseGenericServiceImpl implements PaseGenericService {
	//private static final Logger LOGGER = Logger.getLogger(PaseGenericServiceImpl.class);

	/*@Autowired
	private RssFeedsDAO rssFeedsDAO;

	@Autowired
	private SocialMediaLinkDAO socialMediaLinkDAO;*/

	@Autowired
	private ExceptionDAO exceptionDAO;

	/*@Autowired
	private NewsTriggerDAO newsTriggerDAO;

	@Autowired
	private CompanyDAO companyDAO;*/

	@Autowired
	private CrawlingStatusDAO crawlingStatusDAO;

	/*@Autowired
	private CompanyJavascriptInfoDAO companyJavascriptInfoDAO;*/

	

	@SuppressWarnings("nls")
	@Override
	public void saveException(ExceptionVO exceptionVO) {
		try {
			ExceptionDetails exceptionDetails = new ExceptionDetails();

			exceptionDetails.setFkCompanyId(exceptionVO.getFkCompanyId());
			exceptionDetails.setFkServiceId(exceptionVO.getFkServiceId());
			exceptionDetails.setMethodName(exceptionVO.getMethodName());
			exceptionDetails.setLineNumber(exceptionVO.getLineNumber());
			exceptionDetails.setExceptionMessage(exceptionVO.getExceptionMessage());
			exceptionDetails.setNotes(exceptionVO.getNotes());
			exceptionDetails.setExceptionCode(exceptionVO.getExceptionCode());
			exceptionDetails.setActivityDateTime(new Date());
			SimpleDateFormat activityDateSdf = new SimpleDateFormat("yyyy-MM-dd");
			String activityDateStr = activityDateSdf.format(exceptionDetails.getActivityDateTime());
			try {
				exceptionDetails.setActivityDateTimeTemp(activityDateSdf.parse(activityDateStr));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			exceptionDetails.setActivityDate(activityDateStr);

			if (this.exceptionDAO.checkForExistance(exceptionDetails) == null) {
				this.exceptionDAO.save(exceptionDetails);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("nls")
	@Override
	public String getDurationForService(Date startTime, Date endTime) {

		String duration = "";

		try {
			long diff = endTime.getTime() - startTime.getTime();
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			duration = diffDays + ":" + diffHours + ":" + diffMinutes + ":" + diffSeconds;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return duration;
	}

	

	

	

	

	@SuppressWarnings("nls")
	@Override
	public CrawlingStatus saveDailyCrawlingStatus(long companyId, long serviceId, long batchId) {
		CrawlingStatus crawlingStatusInfo = new CrawlingStatus();
		try {
			// set the daily crawling values
			SimpleDateFormat activityDateSdf = new SimpleDateFormat("yyyy-MM-dd");
			crawlingStatusInfo.setServerName(PASEConstants.DEV_SERVER_NAME);
			crawlingStatusInfo.setServerIPAddress(PASEConstants.DEV_SERVER_IP_ADDRESS);
			crawlingStatusInfo.setFkBatchId(batchId);
			crawlingStatusInfo.setFkServiceId(serviceId);
			crawlingStatusInfo.setFkCompanyId(companyId);
			crawlingStatusInfo.setStatus("started");
			crawlingStatusInfo.setActivityDateTime(new Date());
			final String activityDateStr = activityDateSdf.format(crawlingStatusInfo.getActivityDateTime());
			crawlingStatusInfo.setActivityDateTimeTemp(activityDateSdf.parse(activityDateStr));
			crawlingStatusInfo.setActivityDate(activityDateStr);
			crawlingStatusInfo = this.crawlingStatusDAO.save(crawlingStatusInfo);
			// end of daily crawling values
		} catch (Exception e) {
			final long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			this.saveException(
					new ExceptionVO(companyId, serviceId, "saveDailyCrawlingStatus", lineNumber, e.getMessage(), ""));
		}
		return crawlingStatusInfo;
	}

	@SuppressWarnings("nls")
	@Override
	public void updateDailyCrawlingStatus(CrawlingStatus crawlingStatusInfo) {
		try {
			crawlingStatusInfo.setStatus("completed");
			this.crawlingStatusDAO.update(crawlingStatusInfo);
		} catch (Exception e) {
			final long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			this.saveException(new ExceptionVO(crawlingStatusInfo.getFkCompanyId(), crawlingStatusInfo.getFkServiceId(),
					"updateDailyCrawlingStatus", lineNumber, e.getMessage(), ""));
		}
	}

}

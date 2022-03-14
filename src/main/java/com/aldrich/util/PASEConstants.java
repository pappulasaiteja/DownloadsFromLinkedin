package com.aldrich.util;

import java.util.Calendar;
import java.util.Date;

import com.aldrich.pase.vo.CalendarCompaniesVO;

@SuppressWarnings({ "nls", "boxing" })
public interface PASEConstants 
{

	public static final String PR_ZOOM = "PR_ZOOM";
	public static final String PR_URGENT_NEWS = "PR_URGENT_NEWS";
	public static final String ONLINE_PR_MEDIA = "ONLINE_PR_MEDIA";
	public static final String PR_NEWS_WIRE = "PR_NEWS_WIRE";

	// OLD SOURCE IDS
	public static final Integer PR_ZOOM_SOURCE_ID = 13;
	public static final Integer PR_URGENT_NEWS_SOURCE_ID = 14;
	public static final Integer ONLINE_PR_MEDIA_SOURCE_ID = 15;
	public static final Integer PR_NEWS_WIRE_SOURCE_ID = 16;
	public static final Integer FT_SOURCE_ID = 22;
	public static final Integer REUTERS_SOURCE_ID = 21;
	public static final Integer WSJ_SOURCE_ID = 20;
	public static final Integer TECHCRUNCH_SOURCE_ID = 19;
	public static final Integer VENTUREBEAT_SOURCE_ID = 23;
	public static final Integer FORBES_SOURCE_ID = 17;
	public static final Integer FEED_BURNER_SOURCE_ID = 18;

	// EXCEPTION CODES
	public static final Integer SUCCESS_EXCEPTION_CODE = 0;
	public static final Integer SOCKET_TIMEOUT_EXCEPTION = 28;
	public static final Integer IO_EXCEPTION = 29;
	public static final Integer MOLFORMED_EXCEPTION_CODE = 30;
	public static final Integer NULL_POINTER_EXCEPTION_CODE = 31;
	public static final Integer ARRAY_INDEX_BOUNDS_EXCEPTION_CODE = 32;
	public static final Integer JSON_EXCEPTION = 33;
	public static final Integer ANY_OTHER_EXCEPTION = 99;
	public static final Integer MAX_ATTEMPT_COUNT = 3;

	// EMPLOYEE COUNT & COMPETITORS
	public static final String LINKED_IN_CODE = "LN";
	public static final String CRUNCH_BASE_CODE = "CB";
	public static final Float LINKED_IN_DEFAULT_CONFIDENCE_SCORE = 0.0F;
	public static final Float CRUCNH_BASE_DEFAULT_CONFIDENCE_SCORE = 0.0F;

	// COMPANY TYPES
	public static final Long TRACKING_COMPANY_TYPE_ID = 1L;
	public static final Long GENERIC_API_COMPANY_TYPE_ID = 2L;
	public static final Long THIRD_PARTY_NEWS_COMPANY_TYPE_ID = 3L;
	public static final Long IGNORE_COMPANY_TYPE_ID = 4L;
	public static final Long GENERIC_WEBPAGE_COMPANY_TYPE_ID = 5L;
	public static final Long PARTNER_WEBPAGE_COMPANY_TYPE_ID = 6L;
	public static final Long CUSTOMER_WEBPAGE_COMPANY_TYPE_ID = 7L;
	public static final Long COMPETITOR__COMPANY_TYPE_ID = 8L;
	public static final Long INVESTMENT_TYPE_ID = 9L;
	public static final Long DUPLICATE_COMPANY_TYPE_ID = 10L;
	public static final Long OWN_ALGORITHM_TYPE_ID = 11L;
	public static final Long EMP_COMPANY_ID = 12L;

	// DATA SOURCE TYPES
	public static final Long DATA_SOURCE_ALL = 1L;
	public static final Long DATA_SOURCE_INC_500 = 2L;
	public static final Long DATA_SOURCE_CRUNCH_BASE = 3L;
	public static final Long DATA_SOURCE_INTERNAL_CRM_SYSTEM = 4L;
	public static final Long DATA_SOURCE_OTHERS = 5L;
	public static final Long DATA_SOURCE_LINKEDIN = 6L;
	public static final Long DATA_SOURCE_MOBILE_APPS = 7L;
	public static final Long DATA_SOURCE_ANGEL_LIST = 8L;
	public static final Long DATA_SOURCE_QC = 9L;
	public static final Long DATA_SOURCE_SEED_DB = 10L;

	// LINK TYPE INFO
	public static final Long LINK_TYPE_FB = 1L;
	public static final Long LINK_TYPE_TW = 2L;
	public static final Long LINK_TYPE_GOOGLE_PLUS = 3L;
	public static final Long LINK_TYPE_LINKED_IN = 4L;
	public static final Long LINK_TYPE_CRUNCH_BASE = 5L;
	public static final Long LINK_TYPE_YOUTUBE = 6L;
	public static final Long LINK_TYPE_RSS_FEED = 7L;
	public static final Long LINK_TYPE_GOOGLE_PLAY_STORE = 8L;
	public static final Long LINK_TYPE_FB_APPS_PLAY_STORE = 9L;

	// PATHS
	public static final String PATENT_LITIGATION_FILE_PATH = "/home/ubuntu/aldrich/excel_files/patent_litigation";
	public static final String INVESTORS_FILE_PATH = "/home/ubuntu/aldrich/excel_files/crunchbase/investors-04052016.xls";
	public static final String EMPLOYEE_CAREER_PATH = "/home/ubuntu/aldrich/excel_files/emp-career-data-set1.xls";
	public static final String IMPORT_CB_URL_PATH = "C:\\Users\\Satyam\\Desktop\\cb-links.xls";
	public static final String EMPLOYESS_FILE_PATH = "/home/ubuntu/aldrich/excel_files/emp-data-173-cos.xls";
	public static final String KEYWORDS_FILE_PATH = "/home/ubuntu/aldrich/excel_files/keywords.xml";
	public static final String OPENCALSIS_PARTNERS_FILE_PATH = "/home/ubuntu/aldrich/partners.txt";
	public static final String OPENCALSIS_CUSTOMERS_FILE_PATH = "/home/ubuntu/aldrich/customers.txt";
	public static final String OPENCALSIS_BOARD_MEMBERS_FILE_PATH = "/home/ubuntu/aldrich/boardmembers.txt";
	public static final String OPENCALSIS_TEST_FILE_PATH = "D:/test.txt";
	public static final String EMPLOYEE_LINKEDIN_INFORMATION_FILE_PATH = "/home/ubuntu/aldrich/university/";
	// public static final String EMPLOYEE_LINKEDIN_INFORMATION_FILE_PATH =
	// "C:\\Users\\Satyam\\Desktop\\uni\\";
	public static final String PUBLICATION_FILE_PATH = "/home/ubuntu/aldrich/excel_files/pub-data-set1.xls";

	// public static final String KEYWORDS_FILE_PATH = "D:\\keywords.xml";

	// public static final String NEW_COMPANY_SHEET_PATH =
	// "/home/ubuntu/softwares/new-company-list.xls";
	public static final String NEW_COMPANY_SHEET_PATH = "C:\\Users\\Satyam\\Desktop\\new-leads.xls";
	// public static final String KEYWORDS_FILE_PATH = "E:\\keywords.xml";

	public static final String ACTIVE_STATUS_YES = "Y";

	public static final String CRUNCH_BASE_DOMAIN = "crunchbase.com";
	public static final String CRUNCH_BASE_NAME = "crunchbase";
	public static final String LINKED_IN_DOMAIN = "linkedin.com";
	public static final String LINKED_IN_NAME = "linkedin";

	public static final Long DEFAULT_USER_ID = 1L;
	public static final Long UNKNOWN_CATEGORY_TYPE_ID = 25L;

	// SOURCE IDS - STATIC
	public static final Long INDEED_SOURCE_ID = 267529L;
	public static final Long LINKEDIN_SOURCE_ID = 267530L;
	public static final Long PATENT_FILINGS_SOURCE_ID = 267699L;
	public static final Long ALDRICH_ALGORITHM = 270149L;
	public static final Long ZOOMINFO_REVENUE = 270150L;
	public static final Long INC5000_REVENUE = 270151L;
	public static final Long CRUNCHBASE_API_SOURCE_ID = 263976L;
	public static final Long GOOGLE_CSE_WBPAGE_SOURCE_ID = 270176L;
	public static final Long GOOGLE_APP_STORE_SOURCE_ID = 263968L;
	public static final Long ITUNES_APP_STORE_SOURCE_ID = 263969L;
	public static final Long TWITTER_SOURCE_ID = 400000L;
	public static final Long FACEBOOK_SOURCE_ID = 400001L;
	public static final Long EXHIBITRAC_SOURCE_ID = 400002L;
	public static final Long CONFERENCE_MANAGEMENT_SYSTEM_SOURCE_ID = 400004L;
	public static final Long NTRADE_SHOWS_SOURCE_ID = 400005L;
	public static final Long EVENTS_IN_AMERICA_SOURCE_ID = 400006L;
	public static final Long GOOGLE_SCHOLAR_SOURCE_ID = 426538L;
	public static final Long OWLER_SOURCE_ID=265680L;

	public static final Long DEFENDANT_ID = 1L;
	public static final Long PLAINTIFF_ID = 2L;
	public static final Long UNKNOWN_COMPANY_ID = 316088L;
	public static final Long LINKEDIN_API_SOURCE_ID = 266374L;

	// STATIC URLS
	public static final String CRUNCHBASE_URL = "https://api.crunchbase.com/v/3/organizations/";
	public static final String YOUTUBE_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=";
	public static final String LINKEDKIN_URL = "https://www.linkedin.com/company/";
	public static final String CRUNCHBASE_FUNDINGROUNDS_URL = "https://api.crunchbase.com/v/3/funding-rounds/";
	public static final String YOUTUBE_STAS_URL = "https://www.googleapis.com/youtube/v3/videos?part=snippet%2CcontentDetails%2Cstatistics&id=";
	public static final String BING_WEB_SEARCH_URL = "https://api.datamarket.azure.com/Bing/Search/Web?Query=%%27%s%%27&$format=JSON";

	// OPEN CALAIS
	public static final String CALAIS_URL = "https://api.thomsonreuters.com/permid/calais";

	// LINKEDIN PREMIUM ACCOUNT COOKIES
	public static final String LINKEDIN_PREMIUM_INSIGHTS_COOKIES = "lang='v=2&lang=en-us'; JSESSIONID='ajax:6545974309894766468'; bcookie='v=2&746f1fc7-97ba-4322-8b21-f2a410b39bed'; bscookie='v=1&20170116181829214d8291-18c6-43b2-8c53-f23a00999311AQGTiwNPFSrTNcgtnBONlzhLM4pUBdkB'; lidc='b=SGST03:g=3:u=1:i=1484590771:t=1484677171:s=AQF3YpDIIvi9V9pFF6RnzAAamUrJ34-T'; visit='v=1&M'; RT=s=1484590782656&r=https%3A%2F%2Fwww.linkedin.com%2Fuas%2Flogout%3Fsession_full_logout%3D%26csrfToken%3Dajax%253A0232219789825257743%26trk%3Dnav_account_sub_nav_signout; oz_props_fetch_size1_179163=15; wutan=HYxmee5Nq9X/1+nS21tjn3iUBfwvVm3V+d7zBPRA4lc=; share_setting=PUBLIC; sdsc=1%3A1SZM1shxDNbLt36wZwCgPgvN58iw%3D; sl='v=1&msTvD'; li_at=AQEDAQACu9sEObp7AAABWaiB4WYAAAFZqjlVZlEAcDLwJYDSxwMXr75P-37J8-GNzy27x3acd15rsbsrbXEeLBF77dTZKVclmWkih4eFg8JP8fBXlQxLBlXoys3ih-oEmoKaUDEVzSk2C-T2f6hF8Vim; liap=true";
	public static final String LINKEDIN_COOKIES = "'JSESSIONID='ajax:4120916639348474879'; bcookie='v=2&159eca75-ecb2-4344-8f3e-f070df445769'; bscookie='v=1&20170127101933721c387f-f850-4f47-8ad3-c132e2af3b14AQHqpplBmR97wKEYofJ3sQ1fCo7QmE3k'; visit='v=1&M'; lang='v=2&lang=en-us'; lidc='b=SB17:g=36:u=13:i=1485851397:t=1485858406:s=AQGmBk7eqducw7aWbhloepqnBSqenmrH'; _ga=GA1.2.2012556296.1485759283; wutan=q64DGOp/fiXTBjlLpzQlJbhTNvvEIGoGypCZ3RpO1wE=; sdsc=1%3A1SZM1shxDNbLt36wZwCgPgvN58iw%3D; share_setting=PUBLIC; _lipt=0_US4gxHw6tL3sOPU3lUmv3MwjCckPPmo5jUU1_tj4tu2228-FvC3FZwopNfJRvIXjpw8Wnh4AosOWsuSO5TDCbdBlV2lIJM_iK7HCmOuI-WwCwpU2kXu6ViGVbKA-cdBN8_JAoskuvId_wUUisqR9IBy6W-fMnfWgcCRmpB6CnhRMqHKkS_T-Y-wvQtwg8MQj; _gat=1; sl='v=1&G64vT'; li_at=AQEDAR9utw0CtLJBAAABWfOlCtkAAAFZ9Vx-2VEABLa36xcLDILG-0AHAQ5cETAdwqI9OCxJbVdz0-naG_nlA9nHvMQJI6Iq077MnP7s4KeJBMTMYkqb_Aje9VGQbw5f7Qvgx1PB2Cv4fObzNQR-S1_n; liap=true; RT=s=1485851393675&r=https%3A%2F%2Fwww.linkedin.com%2F; oz_props_fetch_size1_undefined=undefined'";

	// SERVICE IDs
	public static final Long TWITTER_SERVICE_ID = 1L;
	public static final Long FACEBOOK_SERVICE_ID = 2L;
	public static final Long YOUTUBE_SERVICE_ID = 3L;
	public static final Long YOUTUBE_VIDEO_STATS_SERVICE_ID = 4L;
	public static final Long LINKEDIN_FOLLOWERS_SERVICE_ID = 5L;
	public static final Long COMPETITOR_SERVICE_ID = 6L;
	public static final Long INVESTOR_PROFILE_SERVICE_ID = 7L;
	public static final Long INVESTOR_SCORE_CAL_SERVICE_ID = 8L;
	public static final Long INVESTOR_SYNDICATE_INDEX_SERVICE_ID = 9L;
	public static final Long INVESTOR_SYNDICATE_GROWTH_SERVICE_ID = 10L;
	public static final Long FUNDING_INDEX_SERVICE_ID = 11L;
	public static final Long REVENUE_SERVICE_ID = 12L;
	public static final Long INDEED_JOBS_SERVICE_ID = 13L;
	public static final Long LINKEDIN_JOBS_SERVICE_ID = 14L;
	public static final Long EMPLOYEE_COUNT_SERVICE_ID = 15L;
	public static final Long ACQUISITION_DETAILS_SERVICE_ID = 16L;
	public static final Long GOOGLE_APPS_LINKS_COLLECTION_SERVICE_ID = 17L;
	public static final Long GOOGLE_APPS_DATA_EXTRACTION_SERVICE_ID = 18L;
	public static final Long ITUNES_APPS_LINKS_COLLECTION_SERVICE_ID = 19L;
	public static final Long ITUNES_APPS_DATA_EXTRACTION_SERVICE_ID = 20L;
	public static final Long PATENTS_SERVICE_ID = 21L;
	public static final Long PATENT_LITIGATION_SERVICE_ID = 22L;
	public static final Long COMPANY_RSS_FEED_SERVICE_ID = 23L;
	public static final Long CRUNCHBASE_NEWS_SERVICE_ID = 24L;
	public static final Long COMPANY_NEWS_SERVICE_ID = 25L;
	public static final Long GSCE_NEWS_SERVICE_ID = 26L;
	public static final Long PR_NEWS_WIRE_SERVICE_ID = 27L;
	public static final Long ONLINE_PR_MEDIA_SERVICE_ID = 28L;
	public static final Long PRURGET_NEWS_SERVICE_ID = 29L;
	public static final Long PR_ZOOM_SERVICE_ID = 30L;
	public static final Long FEED_BURNER_SERVICE_ID = 31L;
	public static final Long FORBES_SERVICE_ID = 32L;
	public static final Long FT_SERVICE_ID = 33L;
	public static final Long REUTERS_SERVICE_ID = 34L;
	public static final Long WSJ_SERVICE_ID = 35L;
	public static final Long VENTUREBEAT_SERVICE_ID = 36L;
	public static final Long TECHCRUNCH_SERVICE_ID = 37L;
	public static final Long BOARD_MEMBERS_SERVICE_ID = 39L;
	public static final Long JOB_GROWTH_SERVICE_ID = 40L;
	public static final Long FUNDING_GROWTH_SERVICE_ID = 41L;
	public static final Long COMPETITOR_GROWTH_SERVICE_ID = 42L;
	public static final Long PATENT_LITIGATION_INDEX_SERVICE_ID = 43L;
	public static final Long PARTNER_LINK_EXTRACTION_SERVICE_ID = 44L;
	public static final Long CLIENT_LINK_EXTRACTION_SERVICE_ID = 45L;
	public static final Long FORM_D_DATA_EXTRACTION_SERVICE_ID = 46L;
	public static final Long APP_INDEX_SERVICE_ID = 47L;
	public static final Long PARTNER_DATA_EXTRACTION_SERVICE_ID = 48L;
	public static final Long CUSTOMER_DATA_EXTRACTION_SERVICE_ID = 49L;
	public static final Long TWITTER_GROWTH_CALCULATION_SERVICE_ID = 50L;
	public static final Long FACEBOOK_GROWTH_CALCULATION_SERVICE_ID = 51L;
	public static final Long LINKEDIN_GROWTH_CALCULATION_SERVICE_ID = 52L;
	public static final Long YOUTUBE_GROWTH_CALCULATION_SERVICE_ID = 53L;
	public static final Long EXTRACT_COMPANY_DETAILAS_FROM_LINKEDIN_SERVICE_ID = 54L;
	public static final Long BOARD_MEMBERS_LINK_EXTRACTION_SERVICE = 55L;
	public static final Long PRODUCT_EXTRACTION_FROM_CRUNCHBASE_SERVICE_ID = 56L;
	public static final Long LINKEDIN_NEWS_SERVICE_ID = 57L;
	public static final Long URL_REDIRECTION_VERIFICATION_SERVICE_ID = 58L;
	public static final Long EXTRACT_SOCIAL_MEDIA_LINKS_SERVICE_ID = 59L;
	public static final Long EMPLOYEE_INFO_FROM_LINKEDIN_SERVICE_ID = 60L;
	public static final Long PRODUCT_LINK_EXTRACTION_SERVICE_ID = 61L;
	public static final Long PARTNER_INDEX_SERVICE_ID = 62L;
	public static final Long CUSTOMER_INDEX_SERVICE_ID = 63L;
	public static final Long PATENT_INDEX_SERVICE_ID = 64L;
	public static final Long ADD_NEW_COMPANY_SERVICE_ID = 65L;
	public static final Long ESTIMATED_REVENUE_CAL_SERVICE_ID = 66L;
	public static final Long PRODUCT_DATA_EXTRACTION_SERVICE_ID = 67L;
	public static final Long OFFICE_SPACE_LINK_EXTRACTION_SERVICE_ID = 68L;
	public static final Long OFFICE_SPACE_DATA_EXTRACTION_SERVICE_ID = 69L;
	public static final Long GOOGLE_URL_REQUESTS_SERVICE_ID = 70L;
	public static final Long EMPLOYEE_COUNT_EXTRACTION_SERVICE_ID = 71L;
	public static final Long NEWS_TRIGGER_SERVICE_ID = 72L;
	public static final Long BOARD_MEMBERS_DATA_EXTRACTION_FROM_WEBSITE_SERVICE = 73L;
	public static final Long PRODUCT_INDEX_SERVICE_ID = 74L;
	public static final Long OFFICE_SPACE_INDEX_SERVICE_ID = 75L;
	public static final Long BOARD_MEMBERS_INDEX_SERVICE = 76L;
	public static final Long CRUNCHBASE_OFFICE_SPACE_SERVICE_ID = 77L;
	public static final long EVENT_EXTRACTION_TWITTER_SERVICE_ID = 78L;
	public static final Long JAVASCRIPT_BASED_WEBSITES_SERVICE = 79L;
	public static final Long DATABASE_CLEAN_UP_SERVICE_ID = 80L;
	public static final long EVENT_EXTRACTION_FB_SERVICE_ID = 81L;
	public static final long EXHIBIT_RAC_SERVICE_ID = 82L;
	public static final long EVENT_EXTRACTION_CRUNCHBASE_SERVICE_ID = 83L;
	public static final long EVENT_EXTRACTION_COMS_SERVICE_ID = 84L;
	public static final long EVENT_EXTRACTION_NTRADESHOWS_SERVICE_ID = 85L;
	public static final long EVENT_EXTRACTION_EVENTS_IN_AMERICA_SERVICE_ID = 87L;
	public static final long EXTRACT_PUBLICATION_SERVICE_ID = 88L;
	public static final long PUBLICATION_INDEX_SERVICE_ID = 90L;
	public static final long EVENT_EXTRACTION_WEBSITES_SERVICE_ID = 91L;
	public static final long EMPLOYEE_DETAILS_FROM_CRUNCHBASE_SERVICE_ID = 92L;
	public static final long BUILT_WITH_SERVICE_SERVICE_ID = 93L;
	public static final long EMPLOYEE_WEBSITE_INFO_SERVICE_ID = 94L;
	public static final long EMPLOYEE_NEWS_INFO_SERVICE_ID = 95L;
	public static final long EMPLOYEE_VIDEO_INFO_SERVICE_ID = 96L;
	public static final long EMPLOYEE_INVESTMENT_INFO_SERVICE_ID = 97L;
	public static final long AUTOMATIC_RANKING_DATA_COLLECTION_SERVICE_ID = 98L;
	public static final long IMPORT_LINKEDIN_EMPLOYEE_DETAILS_SERVICE_ID = 99L;
	public static final long EMPLOYEE_EDUCATION_INFO__FROM_CRUNCHBASE_SERVICE_ID = 100L;
	public static final long EMPLOYEE_JOBS_INFO_SERVICE_ID = 101L;
	public static final long IMPORT_LINKEDIN_EMPLOYEE_CAREER_DETAILS_SERVICE_ID = 102L;
	public static final long EMPLOYEE_TWITTER_INFO_SERVICE_ID = 103L;
	public static final long EDUCATION_SCORING_SERVICE_ID = 104L;
	public static final long LINKEDIN_PREMIUM_DATA_EXTRACTION_SERVICE_ID = 105L;
	public static final long EMPLOYEE_EDUCATION_MAPPING_SERVICE_ID = 106L;
	public static final long IMPORT_LINKEDIN_EMPLOYEE_EDUCATION_DETAILS_SERVICE = 107L;
	public static final long IMPORT_LINKEDIN_EMPLOYEE_PUBLICATION_DETAILS_SERVICE_ID = 108L;
	public static final long JOB_Index_SERVICE_ID = 109L;
	public static final long EMPLOYEE_EDUCATION_RANKING_SERVICE_ID = 110L;
	public static final long TWITTER_GROWTH_INDEX_SERVICE_ID = 111L;
	public static final long EMPLOYEE_GROWTH_SERVICE_ID = 112L;
	public static final long EMPLOYEE_NETWORK_RANKING_SERVICE_ID = 113L;
	public static final long IMPORT_CRUNCHBASE_LINK_DETAILS_SERVICE_ID = 114L;
	public static final long EMPLOYEE_FACEBOOK_VIDEO_DATA_EXTRACTION_SERVICE_ID = 115L;
	public static final long COMPANY_SCORING_SERVICE_ID = 116L;
	public static final long CONFERENCE_INDEX_SERVICE_ID = 117L;
	public static final long EMPLOYEE_FACEBOOK_EVENT_DATA_EXTRACTION_SERVICE_ID = 118L;
	public static final long EMPLOYEE_CAREER_SCORING_SERVICE_ID = 119L;
	public static final long EXTRACT_LINKEDIN_LINK_FROM_GCSE_SERVICE_ID = 120L;
	public static final long EXTRACT_TWITTER_LINK_FROM_GCSE_SERVICE_ID = 121L;
	public static final long EXTRACT_FACEBOOK_LINK_FROM_GCSE_SERVICE_ID = 122L;
	public static final long EXTRACT_YOUTUBE_LINK_FROM_GCSE_SERVICE_ID = 123L;
	public static final long NEAR_BY_COMPANIES_SERVICE_ID = 124L;
	public static final long ADS_SPENT_SERVICE_ID = 125L;
	public static final long LINKEDIN_PREMIUM_DATA_EXTRACTION_FROM_SELINUM_SERVICE_ID = 126L;
	public static final long UPDATE_LATITUDE_LONGITUDE_SERVICE_ID=135L;
	

	// server details
	//public static final String DEV_SERVER_NAME = "worker1-server";
	//public static final String TEST_SERVER_NAME = "test-server";
	//public static final String DEV_SERVER_IP_ADDRESS = "54.87.254.43";
	//public static final String TEST_SERVER_IP_ADDRESS = "192.168.90.71";
	
	public static final String DEV_SERVER_NAME = "local-server";
	public static final String TEST_SERVER_NAME = "test-server";
	public static final String DEV_SERVER_IP_ADDRESS = "192.168.90.160";
	public static final String TEST_SERVER_IP_ADDRESS = "192.168.90.71";

	// OPEN CALAIS
	public static final String CALAIS_KEY = "SGWU8XSeYdwwGq7qAxARJdPct4BlU88Y";
	public static final String BING_API_KEY = "GSPdGNHXNrZcUM0CT4RCoPEiL8taR2DkC9zGiLrNuoI";

	// User Agent
	public final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0";

	public static final String VALID_TRACKING_COMPANY = "yes-tracking";
	public static final String VALID_NOT_TRACKING_COMPANY = "yes-not-tracking";

	public static final long TIME_INTERVAL_BETWEEN_REQUESTS = 10000;
	public static final int CONNECTION_TIMEOUT = 20000;

	// relevant variables
	public static final String RELEVANT_NO = "no";
	public static final String RELEVANT_YES_TRACKING = "yes-tracking";
	public static final String RELEVANT_YES_NOT_TRACKING = "yes-not-tracking";

	public static final Date CURRENT_DATE = new Date();

	public static final long NO_URL_COMPANY_ID = 21235L;

	/*
	 * public static final Long ACQUISITION_DETAILS_SERVICE_ID = 1L; public
	 * static final Long YOUTUBE_SERVICE_ID = 1L; public static final Long
	 * LINKEDIN_FOLLOWERS_SERVICE_ID = 1L; public static final Long
	 * EMPLOYEE_COUNT_SERVICE_ID = 1L; public static final Long
	 * PATENT_LITIGATION_SERVICE_ID = 1L; public static final Long
	 * FUNDING_INDEX_SERVICE_ID = 1L; public static final Long
	 * FUNDING_GROWTH_SERVICE_ID = 1L; public static final Long
	 * YOUTUBE_VIDEO_STATS_SERVICE_ID = 1L; public static final Long
	 * COMPETITOR_GROWTH_SERVICE_ID = 1L; public static final Long
	 * PATENT_LITIGATION_INDEX_SERVICE_ID = 1L; public static final Long
	 * COMPETITOR_SERVICE_ID = 1L;
	 */
	
	
	
	public static CalendarCompaniesVO getStartAndEndDate() 
	{
		CalendarCompaniesVO vo=null;
		try
		{
			Date today = new Date(); // Fri Jun 17 14:54:28 PDT 2016
			Calendar cal = Calendar.getInstance();
			cal.setTime(today); // don't forget this if date is arbitrary e.g. 01-01-2014

			

			int month = cal.get(Calendar.MONTH); // 5
			int year = cal.get(Calendar.YEAR); // 2016
			
			 vo=new CalendarCompaniesVO();
			vo.setMonth(Integer.toString(month+1));
			vo.setYear(Integer.toString(year));
			vo.setStartDate(Integer.toString(cal.getActualMinimum(Calendar.DAY_OF_MONTH)));
			vo.setEndDate(Integer.toString(cal.getActualMaximum(Calendar.DAY_OF_MONTH)));
			
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return vo;
	}
	
	
	
	public static final String startDate=getStartAndEndDate().getYear()+"-"+getStartAndEndDate().getMonth()+"-"+getStartAndEndDate().getStartDate();
	public static final String endDate=getStartAndEndDate().getYear()+"-"+getStartAndEndDate().getMonth()+"-"+getStartAndEndDate().getEndDate();
	
	
	//Calendar Inputs
	public static final String appCode="OAQABAAIAAACQN9QBRU3jT6bcBQLZNUj7zWdoyIlsayauMeUyIACe3bX1Ydpzs878dPN8JAtLrjRTgmp2q-DdyzC9QSbIqhI0F5Z7LkzJuy6XO9b9iHhY5FD0_RzTs2RuOBX9WUogzDBNKr-b2CFHRzzt2A-wrBnuY9KnR-WUs0laVIRbT-gvd2FUxZjTMurHWX3TeWVW5zf7ptC5qfAQlqOrX1R48jJqzPZH0vgg0CIciuaYHKmcvPQb6Sw_22icvL62XWmupuh3RjoYwHWKgU2Azu3DnwKMOimhKNrMv9rja9kH43DIYanLZx0580tBuW0mHvx-YLfI9znKZ7Abb3VW0ncjzbsXSWhp-6XTdiWJhWcqCCVkz_Fcvw2nOkqZ5qlPvopy87lBW2OPpLjrXc4WXrW0tbNN677MXWqH9YbMqee0-xpP8nn1Qnoe7bYCCiEqO2QeqhwzdeN8SicF9l_BDmQaOYhuZB9QnMdiu7T3AaL9FlEMXviK6qgERhTKEXCowA9dD3TLsYzZjGTGzMIdSr5qiF4A35oFVW7bF8NGN8eGBl2WEYAmRRJHDXd-2AnbiS6ixwYk-MdKj5p4siuiPzv3n-Kl54E1Z-pziq3R8cTX56JKEIH4H11NzxWlaATNljScfvRU-qQWqUpYFCMsJgMrxNCSB_hioDXJFmRKfAB0LHsrJtKgpqgH8DloaG1FRePooJkgAA";
	public static final String clientID="957e00bb-a5f4-47a2-99f5-747f87fec5fe";
	public static final String clientSecret="/:=in/xGzbBaZq2oFb3p2miZPMdK4HzA";
	public static final String accessTokenURL="https://login.microsoftonline.com/common/oauth2/v2.0/token";
	public static final String userName="amateen@aldrichcap.com";
	//public static final String getRequestURL="https://graph.microsoft.com/v1.0/Users/amateen@aldrichcap.com/calendarview?startdatetime="+startDate+"T08:21:58.309Z&enddatetime="+endDate+"T08:21:58.309Z"+"&$top=1000";
	
	public static final String getRequestURL="https://graph.microsoft.com/v1.0/Users/amateen@aldrichcap.com/calendarview?startdatetime=2019-10-01T08:21:58.309Z&enddatetime=2019-10-31T08:21:58.309Z";
	
	public static final String COMPANIES_LOG_RESOURCES_FOLDER_PATH="excel/Companies List.xlsx";
	public static final String EMAIL_TO = "amateen@aldrichcap.com";	
	public static final String EMAIL_CC = "sputtala@aldrichcap.com";
	public static final String ACP_USER_NAME= "paseteam@aldrichcap.com";
	public static final String ACP_PASSWORD= "AcP_987654@";
	public static final String ACP_HOST_NAME= "outlook.office.com";
	public static final String ACP_DOMAIN_NAME= "aldrichcap.com";
	
	
	//Linkedin Local Files Path
	public static final String folder="C:\\Users\\akumar\\Desktop\\Linkedin\\09.06.2019\\";
	public static final String folder1="C:\\LinkedinData\\";
	
	

}

package com.aldrich.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.aldrich.dao.ExceptionDAO;
import com.aldrich.entity.ExceptionDetails;
@Repository
@Qualifier("ExceptionDAO")
@SuppressWarnings(
{
		"nls", "boxing"
})
public class ExceptionDAOImpl implements ExceptionDAO
{
	

	private static final String EXCEPTION_COLLECTION = "exception_info";
	
	@Autowired
	MongoTemplate mongoTemplate;

	

	@Override
	public void save(ExceptionDetails exceptionDetails)
	{
		this.mongoTemplate.insert(exceptionDetails, EXCEPTION_COLLECTION);
	}

	@Override
	public ExceptionDetails checkForExistance(ExceptionDetails exceptionDetails)
	{
		Criteria criteria = new Criteria().andOperator(
				Criteria.where("line_number").is(exceptionDetails.getLineNumber()),
				Criteria.where("fk_service_id").is(exceptionDetails.getFkServiceId()),
				Criteria.where("method_name").is(exceptionDetails.getMethodName()),
				Criteria.where("exception_message").is(exceptionDetails.getExceptionMessage()));

		Query query = new Query(criteria);

		return this.mongoTemplate.findOne(query, ExceptionDetails.class, EXCEPTION_COLLECTION);
	}

	@Override
	public Boolean updateFormdData(ExceptionDetails exceptionDetails)
	{
		Boolean processed = false;
		try
		{
			Date date = new Date();
			DateFormat datetime = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

			Criteria criteria = new Criteria().andOperator(
					Criteria.where("fk_company_id").is(exceptionDetails.getFkCompanyId()),
					Criteria.where("fk_service_id").is(exceptionDetails.getFkServiceId()),
					Criteria.where("method_name").is(exceptionDetails.getMethodName().trim()),
					Criteria.where("exception_message").is(exceptionDetails.getExceptionMessage().trim()));

			Query query = new Query(criteria);
			Update update = new Update();
			update.set("activityDateTime", date);
			update.set("activityDateTimeTemp", date);
			update.set("activityDate", datetime.format(date).substring(0, 8));
			this.mongoTemplate.updateFirst(query, update, EXCEPTION_COLLECTION);
			processed = true;
			return processed;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return processed;
	}

	@Override
	public List<ExceptionDetails> getExceptionFiredURI(Long serviceID)
	{
		List<ExceptionDetails> exceptionDetails = null;
		try
		{
			Date date = new Date();
			DateFormat datetime = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
			String activityDate = datetime.format(date).substring(0, 8);
			Criteria criteria = new Criteria().andOperator(Criteria.where("fk_service_id").is(serviceID),
					Criteria.where("activity_date").is(activityDate));

			Query query = new Query(criteria);
			exceptionDetails = this.mongoTemplate.find(query, ExceptionDetails.class, EXCEPTION_COLLECTION);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return exceptionDetails;
	}

	@Override
	public void appsDeleteSucessfullEntries(ExceptionDetails details, String className)
	{
		try
		{
			Long companyID = details.getFkCompanyId();
			Long serviceID = details.getFkServiceId();
			// Long lineNumber = details.getLineNumber();
			String methodName = details.getMethodName();
			String exceptionMessage = details.getExceptionMessage();
			String keyword = details.getNotes();
			Criteria criteria = new Criteria().andOperator(Criteria.where("fk_company_id").is(companyID),
					Criteria.where("fk_service_id").is(serviceID), Criteria.where("method_name").is(methodName),
					Criteria.where("exception_message").is(exceptionMessage), Criteria.where("notes").is(keyword));
			Query query = new Query(criteria);

			this.mongoTemplate.remove(query, EXCEPTION_COLLECTION);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public List<ExceptionDetails> getErrorsByServiceId(Long serviceId)
	{
		Criteria criteria = new Criteria().andOperator(Criteria.where("fk_service_id").is(serviceId));
		Query query = new Query(criteria);
		query.with(new Sort(Sort.Direction.DESC, "activity_datetime"));
		return this.mongoTemplate.find(query, ExceptionDetails.class, EXCEPTION_COLLECTION);
	}
}

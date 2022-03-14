package com.aldrich.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.aldrich.dao.CrawlingStatusDAO;
import com.aldrich.entity.CrawlingStatus;
@Repository
@Qualifier("CrawlingStatusDAO")
public class CrawlingStatusDAOImpl implements CrawlingStatusDAO {

	@Autowired
	MongoTemplate mongoTemplate;
	
	private static final String CRAWLING_STATUS_COLLECTION = "daily_crawling_status_info_linkedin";

	

	@Override
	public CrawlingStatus save(CrawlingStatus crawlingStatusInfo) {
		this.mongoTemplate.insert(crawlingStatusInfo, CRAWLING_STATUS_COLLECTION);
		return crawlingStatusInfo;
	}

	@SuppressWarnings("nls")
	@Override
	public void update(CrawlingStatus crawlingStatusInfo) {
		Criteria criteria = new Criteria().andOperator(
				Criteria.where("fk_batch_id").is(crawlingStatusInfo.getFkBatchId()),
				Criteria.where("fk_service_id").is(crawlingStatusInfo.getFkServiceId()),
				Criteria.where("fk_company_id").is(crawlingStatusInfo.getFkCompanyId()));
		Query query = new Query(criteria);
		Update update = new Update();
		update.set("status", crawlingStatusInfo.getStatus());
		this.mongoTemplate.updateFirst(query, update, CRAWLING_STATUS_COLLECTION);
	}
}

package com.aldrich.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.aldrich.dao.EmployeeDetailsDAO;
import com.aldrich.entity.EmployeeDetails;


@Repository
public class EmployeeDetaisDAOImpl implements EmployeeDetailsDAO {
	/*private MongoOperations mongoOps;
	@SuppressWarnings("nls")
	private static final String EMPLOYEE_COLLECTION = "employee_info";

	public EmployeeDetaisDAOImpl(MongoOperations mongoOps) {
		super();
		this.mongoOps = mongoOps;
	}*/
	
	private static final String EMPLOYEE_COLLECTION = "employee_info";
	
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public void save(EmployeeDetails employeeDetails) {
		this.mongoTemplate.insert(employeeDetails, EMPLOYEE_COLLECTION);
	}

	@SuppressWarnings("nls")
	@Override
	public List<EmployeeDetails> checkForExistance(String cbPermalink) {
		Criteria criteria = new Criteria().andOperator(Criteria.where("full_name").is(cbPermalink));
		Query query = new Query(criteria);
		// query.with(new
		// org.springframework.data.domain.Sort(Sort.Direction.DESC,
		// "activity_datetime"));
		return this.mongoTemplate.find(query, EmployeeDetails.class, EMPLOYEE_COLLECTION);

	}

	@SuppressWarnings({ "nls", "static-access" })
	@Override
	public Long getId() {
		Query query = new Query();
		try {
			Criteria criteria = new Criteria();
			query.addCriteria(criteria.where("emp_id").gte(0));
			query.with(new Sort(Sort.Direction.DESC, "emp_id"));
			List<EmployeeDetails> empIDs = this.mongoTemplate.find(query, EmployeeDetails.class, EMPLOYEE_COLLECTION);
			Long maxID = (long) 0;
			if (empIDs.size() > 0) {
				EmployeeDetails empDetails = empIDs.get(0);
				maxID = empDetails.getEmpId();
			} else {
				maxID = (long) 0;
			}
			return maxID;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (long) 0;
	}

}

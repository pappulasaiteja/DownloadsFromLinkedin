package com.aldrich.dao;

import java.util.List;

import com.aldrich.entity.ExceptionDetails;

public interface ExceptionDAO {
	public void save(ExceptionDetails exceptionDetails);

	public ExceptionDetails checkForExistance(ExceptionDetails exceptionDetails);

	public Boolean updateFormdData(ExceptionDetails exceptionDetails);

	public List<ExceptionDetails> getExceptionFiredURI(Long serviceID);

	public void appsDeleteSucessfullEntries(ExceptionDetails details, String className);

	public List<ExceptionDetails> getErrorsByServiceId(Long serviceId);
}

package com.aldrich.dao.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aldrich.dao.ServiceStatusDAO;
import com.aldrich.entity.ServiceStatus;

@SuppressWarnings(
{
	"nls"
})
@Repository
public class ServiceStatusDAOImpl implements ServiceStatusDAO
{
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Object save(ServiceStatus serviceStatusInfo)
	{
		Object obj=null;
		try
		{
			 obj=this.sessionFactory.openSession().save(serviceStatusInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public void updateServiceDetails(ServiceStatus serviceStatusInfo, Long id)
	{
		Query query = this.sessionFactory.openSession().createQuery(
				"update ServiceStatus model set model.endDateTime =:endDateTime, model.duration =:duration,model.status =:status where model.id =:id");
		query.setParameter("id", id);
		query.setParameter("endDateTime", serviceStatusInfo.getEndDateTime());
		query.setParameter("duration", serviceStatusInfo.getDuration());
		query.setParameter("status", serviceStatusInfo.getStatus());
		query.executeUpdate();
	}
}

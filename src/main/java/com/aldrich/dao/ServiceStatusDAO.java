package com.aldrich.dao;

import com.aldrich.entity.ServiceStatus;

public interface ServiceStatusDAO {
	public Object save(ServiceStatus serviceStatusInfo);

	public void updateServiceDetails(ServiceStatus serviceStatusInfo, Long id);
}

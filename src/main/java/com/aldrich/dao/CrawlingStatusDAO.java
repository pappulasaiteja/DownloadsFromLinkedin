package com.aldrich.dao;

import com.aldrich.entity.CrawlingStatus;

public interface CrawlingStatusDAO {
	public CrawlingStatus save(CrawlingStatus crawlingStatusInfo);

	public void update(CrawlingStatus crawlingStatusInfo);
}

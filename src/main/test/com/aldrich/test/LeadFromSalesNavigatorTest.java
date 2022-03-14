package com.aldrich.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aldrich.config.BeanConfigurator;
import com.aldrich.service.impl.LeadFromSalesNavigatorServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=BeanConfigurator.class)
public class LeadFromSalesNavigatorTest {

	@Autowired
	LeadFromSalesNavigatorServiceImpl leadFromSalesNavigatorServiceImpl;
	
	//@Ignore
		@Test
		public void runServiceTest()
		{
			try
			{
				leadFromSalesNavigatorServiceImpl.runService();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
}

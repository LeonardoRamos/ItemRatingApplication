package com.uff.item.rating.application;

import org.junit.BeforeClass;

import com.uff.item.rating.application.dao.environment.EnvironmentFactory;

public class ApplicationTest {

	@BeforeClass
	public static void setUpTestEnvironment() { 
		System.setProperty(EnvironmentFactory.TEST_SYSTEM_PARAMETER, EnvironmentFactory.IS_TEST_VALUE);
	}
	
}
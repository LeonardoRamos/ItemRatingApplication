package com.uff.item.rating.application.dao.environment;

public class EnvironmentFactory {
	
	public static final String TEST_SYSTEM_PARAMETER = "testEnvironment";
	public static final String IS_TEST_VALUE = "true";
	protected static final String PROPERTIES_PATH = "src/main/resources/application.properties";
	protected static final String PROPERTIES_TEST_PATH = "src/test/resources/application.properties";
	
	public static String getEnvironmentPath() {
		String isTestEnvironment = System.getProperty(TEST_SYSTEM_PARAMETER);
		
		if (isTestEnvironment != null && isTestEnvironment.equals(IS_TEST_VALUE)) {
			return PROPERTIES_TEST_PATH;
		}
		
		return PROPERTIES_PATH;
	}
	
}
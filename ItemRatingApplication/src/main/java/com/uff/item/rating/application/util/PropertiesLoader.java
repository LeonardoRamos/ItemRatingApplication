package com.uff.item.rating.application.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertiesLoader {
	
	private static final String RECOVER_DATASOURCE_MESSAGE = "Recovering dataSource url attribute";
	private static final String ERROR_CLOSING_PROPERTIES = "Error while closing properties file\n";
	private static final String ERROR_LOADING_PROPERTIES = "Error while loading properties\n";
	private static final String LOAD_PROPERTIES_SUCCESS_MESSAGE = "Application loaded successfully.";
	private static final String START_LOAD_MESSAGE = "Starting load of properties of application.";
	protected static final String DATASOURCE_ATTRIBUTE = "dataSource.url";
	protected static final String DATASOURCE_TEST_ATTRIBUTE = "dataSource.url.test";
	protected static final String DATASOURCE_TEST_FLAG_ATTRIBUTE = "application.test";
	
	private static final Logger log = Logger.getLogger(PropertiesLoader.class.getName());
	
	public static Properties laodProperties(String propertiesPath) {
		log.info(START_LOAD_MESSAGE);
		
		Properties properties = null;
		InputStream input = null;
	 
		try {
			properties = new Properties();
			
			input = new FileInputStream(propertiesPath);
			properties.load(input);
			
			log.info(LOAD_PROPERTIES_SUCCESS_MESSAGE);
			
			return properties;
			
		} 
		catch (IOException e) {
			log.severe(ERROR_LOADING_PROPERTIES + StackTraceUtils.getStackTraceAsString(e));
		} 
		finally {
			closeInputResource(input);
		}
		
		return properties;
	}

	private static void closeInputResource(InputStream input) {
		if (input != null) {
			try {
				input.close();
			} 
			catch (IOException e) {
				log.severe(ERROR_CLOSING_PROPERTIES + StackTraceUtils.getStackTraceAsString(e));
			}
		}
	}
	
	public static String getDataSourceUrlAttribute(Properties properties) {
		log.info(RECOVER_DATASOURCE_MESSAGE);
		
		if (properties == null) {
			return null;
		}
		
		return properties.getProperty(DATASOURCE_ATTRIBUTE);
	}
	
}
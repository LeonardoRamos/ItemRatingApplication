package com.uff.item.rating.application.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import com.uff.item.rating.application.bundle.MessageBundle;

public class PropertiesLoader {
	
	protected static final String DATASOURCE_ATTRIBUTE = "dataSource.url";
	
	private static final Logger log = Logger.getLogger(PropertiesLoader.class.getName());
	
	public static Properties laodProperties(String propertiesPath) {
		log.info(MessageBundle.START_LOAD_MESSAGE);
		
		Properties properties = null;
		InputStream input = null;
	 
		try {
			properties = new Properties();
			
			input = new FileInputStream(propertiesPath);
			properties.load(input);
			
			log.info(MessageBundle.LOAD_PROPERTIES_SUCCESS_MESSAGE);
			
			return properties;
		} 
		catch (IOException e) {
			log.severe(String.format(MessageBundle.ERROR_LOADING_PROPERTIES, StackTraceUtils.getStackTraceAsString(e)));
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
				log.severe(String.format(MessageBundle.ERROR_CLOSING_PROPERTIES, StackTraceUtils.getStackTraceAsString(e)));
			}
		}
	}
	
	public static String getDataSourceUrlAttribute(Properties properties) {
		log.info(MessageBundle.RECOVER_DATASOURCE_MESSAGE);
		
		if (properties == null) {
			return null;
		}
		
		return properties.getProperty(DATASOURCE_ATTRIBUTE);
	}
	
}

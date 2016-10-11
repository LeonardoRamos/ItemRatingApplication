package com.uff.item.rating.application.dao;

import java.io.File;
import java.util.Scanner;
import java.util.logging.Logger;

public abstract class AbstractDao<T> {
	
	private static final Logger log = Logger.getLogger(AbstractDao.class.getName());
	
	protected File dataSource;
	
	private static final String CLOSING_RESOURCE_MESSAGE = "Closing resource.";
	
	protected void closeResource(Scanner reader) {
		log.info(CLOSING_RESOURCE_MESSAGE);
		
		if (reader != null) {
			reader.close();
		}
	}
	
}
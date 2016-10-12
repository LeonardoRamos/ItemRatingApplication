package com.uff.item.rating.application.dao;

import java.io.File;
import java.util.Scanner;
import java.util.logging.Logger;

import com.uff.item.rating.application.bundle.MessageBundle;

public abstract class AbstractDao<T> {
	
	private static final Logger log = Logger.getLogger(AbstractDao.class.getName());
	
	protected File dataSource;
	
	protected void closeResource(Scanner reader) {
		log.info(MessageBundle.CLOSING_RESOURCE_MESSAGE);
		
		if (reader != null) {
			reader.close();
		}
	}
	
}
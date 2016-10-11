package com.uff.item.rating.application.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Logger;

import com.uff.item.rating.application.dao.environment.EnvironmentFactory;
import com.uff.item.rating.application.dao.mapper.ItemMapper;
import com.uff.item.rating.application.dao.mapper.UserMapper;
import com.uff.item.rating.application.domain.Item;
import com.uff.item.rating.application.domain.User;
import com.uff.item.rating.application.util.PropertiesLoader;
import com.uff.item.rating.application.util.StackTraceUtils;

public class ItemRatingDao extends AbstractDao<User> {
	
	private static final Logger log = Logger.getLogger(ItemRatingDao.class.getName());
	
	private static final String STARTING_INSTANCE_MESSAGE = "Starting instance of ItemRatingDao Class.";
	private static final String FIND_ALL_SUCCESS_MESSAGE = "Users data gathered with success";
	private static final String STARTING_FIND_ALL_MESSAGE = "Starting to read users from dataSource.";
	private static final String ERROR_READING_FILES = "Error while reading users of dataSource\n";
	
	private static class Loader {
		private static ItemRatingDao instance = new ItemRatingDao();
    }
		
	private ItemRatingDao() {
		log.info(STARTING_INSTANCE_MESSAGE);
		Properties properties = PropertiesLoader.laodProperties(EnvironmentFactory.getEnvironmentPath());
		dataSource = new File(PropertiesLoader.getDataSourceUrlAttribute(properties));
	}
	
	public static ItemRatingDao getInstance() {
		return Loader.instance;
	}

	public List<User> findAllItemUserRatings() {
		log.info(STARTING_FIND_ALL_MESSAGE);
		
		List<User> usersResult = new ArrayList<User>();
		Scanner reader = null;
		
		try {
			reader = new Scanner(dataSource);
			
			List<Item> itemsResult = ItemMapper.mapItems(reader.nextLine()); 
			
			while (reader.hasNextLine()) {
				String userData = reader.nextLine();
				usersResult.add(UserMapper.mapUser(itemsResult, userData));
			}
			
			log.info(FIND_ALL_SUCCESS_MESSAGE);
			return usersResult;
		} 
		catch (Exception e) {
			log.severe(ERROR_READING_FILES + StackTraceUtils.getStackTraceAsString(e));
			return null;
		}
		finally {
			closeResource(reader);
		}
	}
	
}
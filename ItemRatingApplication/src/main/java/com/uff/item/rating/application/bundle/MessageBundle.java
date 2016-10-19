package com.uff.item.rating.application.bundle;

public class MessageBundle {
	
	//Commons
	public static final String INPUT_DElIMITER = "/";
	
	//ItemRatingController
	public static final String EMPTY_INPUT_MESSAGE = "Empty input data.";
	public static final String INVALID_INPUT_MESSAGE = "Invalid input data";
	
	//Mappers
	public static final String CAN_NOT_MAP_MESSAGE = "Can not map empty data.";
	
	//AbstractDao
	public static final String CLOSING_RESOURCE_MESSAGE = "Closing resource.";
	
	//ItemRatingDao
	public static final String FIND_ALL_SUCCESS_MESSAGE = "Users data gathered with success";
	public static final String STARTING_FIND_ALL_MESSAGE = "Starting to read users from dataSource.";
	public static final String ERROR_READING_FILES = "Error while reading users of dataSource\n%s";
	
	//ItemRatingService
	public static final String USER_NOT_FOUND_MESSAGE = "User not found";
	public static final String RATING_NOT_FOUND_MESSAGE = "Rating not found for input given.";
	public static final String RATING_MESSAGE = "Rating: ";
	public static final String ERROR_PREDICTING_MESSAGE = "Error while retrieving prediction strategy\n%s";
	public static final String TOTAL_RATINGS_FOR_ITEM_MESSAGE = "Total users that rated item '%s': %d\n";
	public static final String TOTAL_ITEMS_RATED_BY_USER_MESSAGE = "Total items rated by user '%s': %d\n";
	public static final String TOTAL_SIMILAR_USERS_MESSAGE = "Total users that rated same items as user '%s' and rated item '%s': %d\n";
	public static final String COULD_NOT_PREDICT_MESSAGE = "Could not predict rating for item '%s'\n";
	public static final String PRED_USER_BASED_MESSAGE = "pred(rxy) using user based approach: %s\n";
	public static final String PRED_ITEM_BASED_MESSAGE = "pred(rxy) using item based approach: %s";
	
	//RatingPredictionStrategyFactory
	public static final String INVALID_STRATEGY_MESSAGE = "Invalid prediction Strategy.";
	
	//PropertiesLoader
	public static final String RECOVER_DATASOURCE_MESSAGE = "Recovering dataSource url attribute";
	public static final String ERROR_CLOSING_PROPERTIES = "Error while closing properties file\n%s";
	public static final String ERROR_LOADING_PROPERTIES = "Error while loading properties\n%s";
	public static final String LOAD_PROPERTIES_SUCCESS_MESSAGE = "Application loaded successfully.";
	public static final String START_LOAD_MESSAGE = "Starting load of properties of application.";
	
	//ItemRatingView
	public static final String ENTER_INPUT_MESSAGE = "Enter a pair of UserX / ItemY:";
	
	//Application
	public static final String EXITING_APP_MESSAGE = "Exiting ItemRatingApplication...";
	public static final String STARTING_APP_MESSAGE = "Starting ItemRatingApplication...";
	
}
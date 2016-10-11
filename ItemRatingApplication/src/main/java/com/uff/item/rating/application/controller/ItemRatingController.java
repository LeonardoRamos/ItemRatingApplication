package com.uff.item.rating.application.controller;

import java.util.logging.Logger;

import com.uff.item.rating.application.service.ItemRatingService;

public class ItemRatingController {

	private static final Logger log = Logger.getLogger(ItemRatingController.class.getName());

	private static final String STARTING_INSTANCE_MESSAGE = "Starting instance of ItemRatingController Class.";
	private static final String EMPTY_INPUT_MESSAGE = "Empty input data.";
	private static final String INVALID_INPUT_MESSAGE = "Invalid input data";
	private static final String INPUT_DElIMITER = "/";

	private ItemRatingService itemRatingService;
	
	private static class Loader {
		private static ItemRatingController instance = new ItemRatingController();
    }
		
	private ItemRatingController() {
		log.info(STARTING_INSTANCE_MESSAGE);
		itemRatingService = ItemRatingService.getInstance();
	}
	
	public static ItemRatingController getInstance() {
		return Loader.instance;
	}

	public String getItemRating(String input) {
		if (input == null || input.isEmpty()) {
			return EMPTY_INPUT_MESSAGE;
		}
		
		String[] inputData = input.split(INPUT_DElIMITER);
		
		if (inputData == null || inputData.length < 2) {
			return INVALID_INPUT_MESSAGE;
		}
		
		String userName = inputData[0].trim();
		String itemName = inputData[1].trim();
		
		return itemRatingService.getItemRating(userName, itemName);
	}
	
}
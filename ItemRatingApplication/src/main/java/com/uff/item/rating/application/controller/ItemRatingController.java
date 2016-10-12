package com.uff.item.rating.application.controller;

import com.uff.item.rating.application.bundle.MessageBundle;
import com.uff.item.rating.application.service.ItemRatingService;

public class ItemRatingController {
	
	private ItemRatingService itemRatingService;
	
	private static class Loader {
		private static ItemRatingController instance = new ItemRatingController();
    }
		
	private ItemRatingController() {
		itemRatingService = ItemRatingService.getInstance();
	}
	
	public static ItemRatingController getInstance() {
		return Loader.instance;
	}

	public String getItemRating(String input) {
		if (input == null || input.isEmpty()) {
			return MessageBundle.EMPTY_INPUT_MESSAGE;
		}
		
		String[] inputData = input.split(MessageBundle.INPUT_DElIMITER);
		
		if (inputData == null || inputData.length < 2) {
			return MessageBundle.INVALID_INPUT_MESSAGE;
		}
		
		String userName = inputData[0].trim();
		String itemName = inputData[1].trim();
		
		return itemRatingService.getItemRating(userName, itemName);
	}
	
}
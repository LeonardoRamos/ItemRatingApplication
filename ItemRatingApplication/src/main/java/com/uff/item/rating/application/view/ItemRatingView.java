package com.uff.item.rating.application.view;

import java.util.Scanner;
import java.util.logging.Logger;

import com.uff.item.rating.application.controller.ItemRatingController;

public class ItemRatingView {
	
	private static final Logger log = Logger.getLogger(ItemRatingView.class.getName());

	private static final String STARTING_INSTANCE_MESSAGE = "Starting instance of ItemRatingView Class.";

	private ItemRatingController itemRatingController;
	
	private static class Loader {
		private static ItemRatingView instance = new ItemRatingView();
    }
		
	private ItemRatingView() {
		log.info(STARTING_INSTANCE_MESSAGE);
		itemRatingController = ItemRatingController.getInstance();
		buildView();
	}
	
	public static ItemRatingView getInstance() {
		return Loader.instance;
	}
	
	private void buildView() {
		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter a pair of UserX / ItemY:");
		String input = in.nextLine();
		
		System.out.println(itemRatingController.getItemRating(input));
		
		in.close();
		System.exit(0);
	}
	
}
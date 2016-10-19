package com.uff.item.rating.application.view;

import java.util.Scanner;

import com.uff.item.rating.application.bundle.MessageBundle;
import com.uff.item.rating.application.controller.ItemRatingController;

public class ItemRatingView {
	
	private ItemRatingController itemRatingController;
	
	private static class Loader {
		private static ItemRatingView instance = new ItemRatingView();
    }
		
	private ItemRatingView() {
		itemRatingController = ItemRatingController.getInstance();
	}
	
	public static ItemRatingView getInstance() {
		return Loader.instance;
	}
	
	public void buildView() {
		Scanner in = new Scanner(System.in);
		
		System.out.println(MessageBundle.ENTER_INPUT_MESSAGE);
		System.out.println(itemRatingController.getItemRating(in.nextLine()));
		
		in.close();
	}
	
}
package com.uff.item.rating.application;

import java.util.logging.Logger;

import com.uff.item.rating.application.bundle.MessageBundle;
import com.uff.item.rating.application.view.ItemRatingView;

public class Application {
	
	private static final Logger log = Logger.getLogger(Application.class.getName());
	
	public static void main(String[] args) {
		log.info(MessageBundle.STARTING_APP_MESSAGE);
		
		ItemRatingView.getInstance().buildView(); 
		
		log.info(MessageBundle.EXITING_APP_MESSAGE);
		System.exit(0);
	}
	
}
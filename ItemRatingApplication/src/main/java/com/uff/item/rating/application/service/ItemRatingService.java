package com.uff.item.rating.application.service;

import java.util.List;
import java.util.logging.Logger;

import com.uff.item.rating.application.bundle.MessageBundle;
import com.uff.item.rating.application.dao.ItemRatingDao;
import com.uff.item.rating.application.domain.Item;
import com.uff.item.rating.application.domain.RatingRange;
import com.uff.item.rating.application.domain.User;
import com.uff.item.rating.application.exception.InvalidPredictionStrategyException;
import com.uff.item.rating.application.strategy.PredictionType;
import com.uff.item.rating.application.strategy.RatingPredictionStrategy;
import com.uff.item.rating.application.strategy.factory.RatingPredictionStrategyFactory;
import com.uff.item.rating.application.util.StackTraceUtils;

public class ItemRatingService {

	private static final Logger log = Logger.getLogger(ItemRatingService.class.getName());

	private RatingPredictionStrategy ratingPredictionStrategy;
	private ItemRatingDao itemRatingDao;
	
	private static class Loader {
		private static ItemRatingService instance = new ItemRatingService();
    }
		
	private ItemRatingService() {
		itemRatingDao = ItemRatingDao.getInstance();
	}
	
	public static ItemRatingService getInstance() {
		return Loader.instance;
	}

	public String getItemRating(String userName, String itemName) {
		List<User> users = itemRatingDao.findAllItemUserRatings();
		
		User user = getUserByName(users, userName);
		
		if (user == null) {
			return MessageBundle.USER_NOT_FOUND_MESSAGE;
		}
		
		String rating = user.getRatingByItem(itemName);
		
		if (rating == null) {
			return MessageBundle.RATING_NOT_FOUND_MESSAGE;
		}
		
		if (!RatingRange.NOT_RATED.getRating().equals(rating)) {
			StringBuilder ratingMessage = new StringBuilder(MessageBundle.RATING_MESSAGE);
			return ratingMessage.append(rating).toString();
		}
		
		return buildRatingDetails(userName, itemName, users, user);
	}

	private String buildRatingDetails(String userName, String itemName, List<User> users, User user) {
		StringBuilder ratingDetails = new StringBuilder();
		
		ratingDetails.append(String.format(MessageBundle.TOTAL_RATINGS_FOR_ITEM_MESSAGE, 
				 			 itemName, getTotalRatingForItem(users, itemName)));
		
		ratingDetails.append(String.format(MessageBundle.TOTAL_ITEMS_RATED_BY_USER_MESSAGE, 
										   userName, user.getTotalValidRatings()));
		
		ratingDetails.append(String.format(MessageBundle.TOTAL_SIMILAR_USERS_MESSAGE, 
				 			 user.getName(), itemName, getTotalUsersSimilarityForItem(users, user, itemName)));
		
		buildPredictionsDetails(user, itemName, users, ratingDetails);
		
		return ratingDetails.toString();
	}

	private void buildPredictionsDetails(User user, String itemName, List<User> users, StringBuilder ratingDetails) {
		try {
			ratingPredictionStrategy = RatingPredictionStrategyFactory.getPredictionStrategy(PredictionType.USER_BASED);
			ratingDetails.append(String.format(MessageBundle.PRED_USER_BASED_MESSAGE, 
								 ratingPredictionStrategy.predictRating(users, user, itemName)));
			
			ratingPredictionStrategy = RatingPredictionStrategyFactory.getPredictionStrategy(PredictionType.ITEM_BASED);
			ratingDetails.append(String.format(MessageBundle.PRED_ITEM_BASED_MESSAGE, 
					 			 ratingPredictionStrategy.predictRating(users, user, itemName)));
		}
		catch (InvalidPredictionStrategyException e) {
			log.severe(String.format(MessageBundle.ERROR_PREDICTING_MESSAGE, StackTraceUtils.getStackTraceAsString(e)));
			ratingDetails.append(String.format(MessageBundle.COULD_NOT_PREDICT_MESSAGE, itemName));
		}
	}

	private Integer getTotalUsersSimilarityForItem(List<User> users, User user, String itemName) {
		Integer total = 0;
		
		for (User otherUser : users) {
			if (!user.getName().equals(otherUser.getName()) && user.hasRatedSameItems(otherUser, itemName) && 
				otherUser.hasRatedItem(itemName)) {
				total++;
			}
		}
		
		return total;
	}

	private User getUserByName(List<User> users, String userName) {
		for (User user : users) {
			if (userName.equals(user.getName())) {
				return user;
			}
		}
		
		return null;
	}

	private Integer getTotalRatingForItem(List<User> users, String itemName) {
		Integer total = 0;
		
		for (User user : users) {
			for (Item item : user.getItemRatings()) {
				if (itemName.equals(item.getName()) && !RatingRange.NOT_RATED.getRating().equals(item.getRating())) {
					total++;
				}
			}
		}
		
		return total;
	}
	
}
package com.uff.item.rating.application.service;

import java.util.List;
import java.util.logging.Logger;

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

	private static final String USER_NOT_FOUND_MESSAGE = "User not found";
	private static final String STARTING_INSTANCE_MESSAGE = "Starting instance of ItemRatingService Class.";
	private static final String RATING_NOT_FOUND_MESSAGE = "Rating not found for input given.";

	private RatingPredictionStrategy ratingPredictionStrategy;
	private ItemRatingDao itemRatingDao;
	
	private static class Loader {
		private static ItemRatingService instance = new ItemRatingService();
    }
		
	private ItemRatingService() {
		log.info(STARTING_INSTANCE_MESSAGE);
		itemRatingDao = ItemRatingDao.getInstance();
	}
	
	public static ItemRatingService getInstance() {
		return Loader.instance;
	}

	public String getItemRating(String userName, String itemName) {
		List<User> users = itemRatingDao.findAllItemUserRatings();
		
		User user = getUserByName(users, userName);
		
		if (user == null) {
			return USER_NOT_FOUND_MESSAGE;
		}
		
		String rating = user.getRatingByItem(itemName);
		
		if (rating == null) {
			return RATING_NOT_FOUND_MESSAGE;
		}
		
		if (!RatingRange.NOT_RATED.getRating().equals(rating)) {
			StringBuilder ratingMessage = new StringBuilder("Rating: ");
			return ratingMessage.append(rating).toString();
		}
		
		return buildRatingDetails(userName, itemName, users, user);
	}

	private String buildRatingDetails(String userName, String itemName, List<User> users, User user) {
		StringBuilder ratingDetails = new StringBuilder();
		
		buildTotalRatingDetails(itemName, users, ratingDetails);
		buildValidRatingsDetails(userName, user, ratingDetails);
		buildUsersSimilarityDetails(itemName, users, user, ratingDetails);
		buildPredictionsDetails(user, itemName, users, ratingDetails);
		
		return ratingDetails.toString();
	}

	private void buildPredictionsDetails(User user, String itemName, List<User> users, StringBuilder ratingDetails) {
		try {
			ratingPredictionStrategy = RatingPredictionStrategyFactory.getPredictionStrategy(PredictionType.USER_BASED);
			ratingDetails.append("pred(rxy) using user based approach: ");
			ratingDetails.append(ratingPredictionStrategy.predictRating(users, user, itemName));
			ratingDetails.append("\n");
			
			ratingPredictionStrategy = RatingPredictionStrategyFactory.getPredictionStrategy(PredictionType.ITEM_BASED);
			ratingDetails.append("pred(rxy) using item based approach: ");
			ratingDetails.append(ratingPredictionStrategy.predictRating(users, user, itemName));
			ratingDetails.append("\n");
		}
		catch (InvalidPredictionStrategyException e) {
			log.severe("Error while retrieving prediction strategy\n" + StackTraceUtils.getStackTraceAsString(e));
			ratingDetails.append("Could not predict rating for item '");
			ratingDetails.append(itemName);
			ratingDetails.append("'\n");
		}
	}

	private void buildUsersSimilarityDetails(String itemName, List<User> users, User user, StringBuilder ratingDetails) {
		ratingDetails.append("Total users that rated same items as user '");
		ratingDetails.append(user.getName());
		ratingDetails.append("' and rated item '");
		ratingDetails.append(itemName);
		ratingDetails.append("': ");
		ratingDetails.append(getTotalUsersSimilarityForItem(users, user, itemName));
		ratingDetails.append("\n");
	}

	private void buildValidRatingsDetails(String userName, User user, StringBuilder ratingDetails) {
		ratingDetails.append("Total items rated by user '");
		ratingDetails.append(userName);
		ratingDetails.append("': ");
		ratingDetails.append(user.getTotalValidRatings());
		ratingDetails.append("\n");
	}

	private void buildTotalRatingDetails(String itemName, List<User> users, StringBuilder ratingDetails) {
		ratingDetails.append("Total users that rated item '");
		ratingDetails.append(itemName);
		ratingDetails.append("': ");
		ratingDetails.append(getTotalRatingForItem(users, itemName));
		ratingDetails.append("\n");
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
				if (itemName.equals(item.getName())) {
					total++;
				}
			}
		}
		
		return total;
	}
	
}
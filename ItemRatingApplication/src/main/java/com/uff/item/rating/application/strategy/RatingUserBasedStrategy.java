package com.uff.item.rating.application.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.uff.item.rating.application.domain.Item;
import com.uff.item.rating.application.domain.User;

public class RatingUserBasedStrategy implements RatingPredictionStrategy {

	private BigDecimal calculateSimilarity(User user, User otherUser, String itemName) {
		BigDecimal upperResult = calculateUpperValue(user, otherUser, itemName);
		BigDecimal firstLowerResult = calculateLowerValue(user);
		BigDecimal secondLowerResult = calculateLowerValue(otherUser);
		
		return upperResult.divide(firstLowerResult.multiply(secondLowerResult), RoundingMode.HALF_EVEN);
	}

	private BigDecimal calculateUpperValue(User user, User otherUser, String itemName) {
		BigDecimal result = BigDecimal.ZERO;
		
		for (Item item : user.getItemRatings()) {
			if (itemName.equals(item.getName())) {
				continue;
			}
			
			Item otherItem = otherUser.getItemByName(item.getName());
			
			if (otherItem == null || Item.NOT_RATED.equals(otherItem.getRating())) {
				continue;
			}
			
			BigDecimal userAverageRating = user.calculateAverageRating();
			BigDecimal otherUserAverageRating = otherUser.calculateAverageRating();
			
			result = result.add((BigDecimal.valueOf(Double.valueOf(item.getRating())).subtract(userAverageRating))
						   .multiply(BigDecimal.valueOf(Double.valueOf(otherItem.getRating()))
						   .subtract(otherUserAverageRating)));
		}
		
		return result;
	}
	
	private BigDecimal calculateLowerValue(User user) {
		BigDecimal result = BigDecimal.ZERO;
		
		for (Item item : user.getItemRatings()) {
			if (Item.NOT_RATED.equals(item.getRating())) {
				continue;
			}
			
			BigDecimal userAverageRating = user.calculateAverageRating();
			
			result = result.add((BigDecimal.valueOf(Double.valueOf(item.getRating())).subtract(userAverageRating)).pow(2));
		}
		
		return BigDecimal.valueOf(Math.sqrt(result.doubleValue()));
	}

	@Override
	public String predictRating(List<User> users, User user, String itemName) {
		List<UserSimilarity> userSimilarities = calculateSimilarities(users, user, itemName);
		
		BigDecimal userRating = user.calculateAverageRating();
		BigDecimal upperPredictionValue = calculateUpperPredictionValue(user, itemName, userSimilarities);
		BigDecimal lowerPredictionValue = calculatePredictionValue(userSimilarities);
		
		return userRating.add(upperPredictionValue.divide(lowerPredictionValue, RoundingMode.HALF_EVEN)).toString();
	}

	private BigDecimal calculatePredictionValue(List<UserSimilarity> userSimilarities) {
		BigDecimal result = BigDecimal.ZERO;
		
		for (UserSimilarity userSimilarity : userSimilarities) {
			result = result.add(userSimilarity.getSimilarity());
		}
		
		return result;
	}

	private BigDecimal calculateUpperPredictionValue(User user, String itemName, List<UserSimilarity> userSimilarities) {
		BigDecimal result = BigDecimal.ZERO;
		
		for (UserSimilarity userSimilarity : userSimilarities) {
			BigDecimal neighborAverageRating = userSimilarity.getOtherUser().calculateAverageRating();
			
			if (!Item.NOT_RATED.equals(userSimilarity .getOtherUser().getRatingByItem(itemName))) {
				result = result.add(userSimilarity.getSimilarity()
							   .multiply(BigDecimal.valueOf(Double.valueOf(userSimilarity
							   .getOtherUser().getRatingByItem(itemName)))
							   .subtract(neighborAverageRating)));
				
			}
		}
		
		return result;
	}

	private List<UserSimilarity> calculateSimilarities(List<User> users, User user, String itemName) {
		List<UserSimilarity> similarities = new ArrayList<UserSimilarity>();
		
		for (User otherUser : users) {
			if (!user.getName().equals(otherUser.getName())) { 
				similarities.add(UserSimilarity.builder()
											   .user(user)
											   .otherUser(otherUser)
											   .similarity(calculateSimilarity(user, otherUser, itemName))
											   .build());
			}
		}
		
		return similarities;
	}
	
	public static class UserSimilarity {
		
		private User user;
		private User otherUser;
		private BigDecimal similarity;
		
		public UserSimilarity(UserSimilarityBuilder userSimilarityBuilder) {
			this.user = userSimilarityBuilder.user;
			this.otherUser = userSimilarityBuilder.otherUser;
			this.similarity = userSimilarityBuilder.similarity;
		}

		public User getUser() {
			return user;
		}
		
		public void setUser(User user) {
			this.user = user;
		}
		
		public User getOtherUser() {
			return otherUser;
		}
		
		public void setOtherUser(User otherUser) {
			this.otherUser = otherUser;
		}
		
		public BigDecimal getSimilarity() {
			return similarity;
		}
		
		public void setSimilarity(BigDecimal similarity) {
			this.similarity = similarity;
		}
		
		public static UserSimilarityBuilder builder() {
			return new UserSimilarityBuilder();
		}
		
		public static class UserSimilarityBuilder {
			
			private User user;
			private User otherUser;
			private BigDecimal similarity;
			
			public UserSimilarityBuilder user(User user) {
				this.user = user;
				return this;
			}
			
			public UserSimilarityBuilder otherUser(User otherUser) {
				this.otherUser = otherUser;
				return this;
			}
			
			public UserSimilarityBuilder similarity(BigDecimal similarity) {
				this.similarity = similarity;
				return this;
			}
			
			public UserSimilarity build() {
				return new UserSimilarity(this);
			}
		} 
	}
	
}
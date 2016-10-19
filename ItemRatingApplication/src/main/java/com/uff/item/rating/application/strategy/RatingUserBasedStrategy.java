package com.uff.item.rating.application.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.uff.item.rating.application.domain.Item;
import com.uff.item.rating.application.domain.RatingRange;
import com.uff.item.rating.application.domain.User;

public class RatingUserBasedStrategy extends AbstractRatingPrediction implements RatingPredictionStrategy {

	private BigDecimal calculateSimilarity(User user, User otherUser, String itemName) {
		BigDecimal upperResult = calculateUpperValue(user, otherUser);
		BigDecimal firstLowerResult = calculateLowerValue(user);
		BigDecimal secondLowerResult = calculateLowerValue(otherUser);
		
		return upperResult.divide(firstLowerResult.multiply(secondLowerResult), 3, RoundingMode.HALF_EVEN);
	}

	private BigDecimal calculateLowerValue(User user) {
		BigDecimal result = BigDecimal.ZERO;
		
		for (Item item : user.getItemRatings()) {
			if (!RatingRange.NOT_RATED.getRating().equals(item.getRating())) {
				BigDecimal userAverageRating = user.calculateAverageRating();
				result = result.add((new BigDecimal(item.getRating()).subtract(userAverageRating)).pow(2));
			}
		}
		
		return BigDecimal.valueOf(Math.sqrt(result.doubleValue()));
	}
	
	private BigDecimal calculateUpperValue(User user, User otherUser) {
		BigDecimal result = BigDecimal.ZERO;
		
		for (Item item : user.getItemRatings()) {
			String itemRating = user.getRatingByItem(item.getName());
			String otherItemRating = otherUser.getRatingByItem(item.getName());
			
			if (!RatingRange.NOT_RATED.getRating().equals(otherItemRating) &&
				!RatingRange.NOT_RATED.getRating().equals(itemRating)) {
				
				BigDecimal userAverageRating = user.calculateAverageRating();
				BigDecimal otherUserAverageRating = otherUser.calculateAverageRating();
				
				result = result.add((new BigDecimal(item.getRating()).subtract(userAverageRating))
							   .multiply(new BigDecimal(otherItemRating)
							   .subtract(otherUserAverageRating)));
			}
		}
		
		return result;
	}

	@Override
	public String predictRating(List<User> users, User user, String itemName) {
		BigDecimal upperValue = BigDecimal.ZERO;
		BigDecimal lowerValue = BigDecimal.ZERO;
		BigDecimal userAverageRating = user.calculateAverageRating();
		
		for (User otherUser : users) {
			if (!user.getName().equals(otherUser.getName())) { 
				BigDecimal neighborAverageRating = otherUser.calculateAverageRating();
				BigDecimal similarity = calculateSimilarity(user, otherUser, itemName);
				
				lowerValue = lowerValue.add(similarity);
				upperValue = upperValue.add(similarity.multiply(new BigDecimal(otherUser.getRatingByItem(itemName))
									   .subtract(neighborAverageRating)));
			}
		}
		
		return processPredictionResult(userAverageRating.add(upperValue.divide(lowerValue, 3, RoundingMode.HALF_EVEN)));
	}

}
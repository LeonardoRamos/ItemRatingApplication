package com.uff.item.rating.application.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.uff.item.rating.application.domain.Item;
import com.uff.item.rating.application.domain.User;

public class RatingItemBasedStrategy implements RatingPredictionStrategy {

	private BigDecimal calculateSimilarity(List<User> users, String itemName, String otherItemName) {
		BigDecimal upperResult = calculateUpperValue(users, itemName, otherItemName);
		BigDecimal itemEuclidianLength = calculateItemEuclidianLength(users, itemName);
		BigDecimal otherItemEuclidianLength = calculateItemEuclidianLength(users, otherItemName);
		
		return upperResult.divide(itemEuclidianLength.multiply(otherItemEuclidianLength), RoundingMode.HALF_EVEN);
	}

	private BigDecimal calculateItemEuclidianLength(List<User> users, String itemName) {
		BigDecimal result = BigDecimal.ZERO;
		
		for (User user : users) {
			String itemRating = user.getRatingByItem(itemName);
			
			if (!Item.NOT_RATED.equals(itemRating)) {
				BigDecimal userAverageRating = user.calculateAverageRating();
				
				result = result.add((BigDecimal.valueOf(Double.valueOf(itemRating))
							   .subtract(userAverageRating))
							   .multiply(BigDecimal.valueOf(Double.valueOf(itemRating))));
			}
		}
		
		return result;
	}

	private BigDecimal calculateUpperValue(List<User> users, String itemName, String otherItemName) {
		BigDecimal result = BigDecimal.ZERO;
		
		for (User user : users) {
			String itemRating = user.getRatingByItem(itemName);
			String otherItemRating = user.getRatingByItem(otherItemName);
			
			if (!Item.NOT_RATED.equals(itemRating) && !Item.NOT_RATED.equals(otherItemRating)) {
				BigDecimal userAverageRating = user.calculateAverageRating();
				
				result = result.add((BigDecimal.valueOf(Double.valueOf(itemRating))
							   .subtract(userAverageRating))
							   .multiply((BigDecimal.valueOf(Double.valueOf(otherItemRating))
							   .subtract(userAverageRating))));
			}
		}
		
		return BigDecimal.valueOf(Math.sqrt(result.doubleValue()));
	}
	
	@Override
	public String predictRating(List<User> users, User user, String itemName) {
		BigDecimal upperValue = BigDecimal.ZERO;
		BigDecimal lowerValue = BigDecimal.ZERO;
		
		for (Item otherItem : user.getItemRatings()) {
			if (!itemName.equals(otherItem.getName()) && !Item.NOT_RATED.equals(otherItem.getRating())) {
				BigDecimal similarity = calculateSimilarity(users, itemName, otherItem.getName());
				
				upperValue = upperValue.add(similarity.multiply(BigDecimal.valueOf(Double.valueOf(otherItem.getRating()))));
				lowerValue = lowerValue.add(similarity);
			}
		}
		
		return upperValue.divide(lowerValue, RoundingMode.HALF_EVEN).toString();
	}
	
}
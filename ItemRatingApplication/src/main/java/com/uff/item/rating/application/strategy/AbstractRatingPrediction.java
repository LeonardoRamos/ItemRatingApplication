package com.uff.item.rating.application.strategy;

import java.math.BigDecimal;

import com.uff.item.rating.application.domain.RatingRange;

public abstract class AbstractRatingPrediction {
	
	protected String processPredictionResult(BigDecimal predictedValue) {
		if (predictedValue.compareTo(new BigDecimal(RatingRange.MAX_RATING.getRating())) > 0) {
			return RatingRange.MAX_RATING.getRating();
		}
		else if (predictedValue.compareTo(new BigDecimal(RatingRange.MIN_RATING.getRating())) < 0) {
			return RatingRange.MIN_RATING.getRating();
		}
		
		return predictedValue.toString();
	}
	
}
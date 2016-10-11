package com.uff.item.rating.application.strategy;

import java.math.BigDecimal;

import com.uff.item.rating.application.domain.Item;

public abstract class AbstractRatingPrediction {
	
	protected String processPredictionResult(BigDecimal predictedValue) {
		if (predictedValue.compareTo(BigDecimal.valueOf(Item.MAX_RATING)) > 0) {
			return Item.MAX_RATING.toString();
		}
		else if (predictedValue.compareTo(BigDecimal.valueOf(Item.MIN_RATING)) < 0) {
			return Item.MIN_RATING.toString();
		}
		
		return predictedValue.toString();
	}
	
}
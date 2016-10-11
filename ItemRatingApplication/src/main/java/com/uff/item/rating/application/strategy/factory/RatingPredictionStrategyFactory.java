package com.uff.item.rating.application.strategy.factory;

import com.uff.item.rating.application.exception.InvalidPredictionStrategyException;
import com.uff.item.rating.application.strategy.PredictionType;
import com.uff.item.rating.application.strategy.RatingItemBasedStrategy;
import com.uff.item.rating.application.strategy.RatingPredictionStrategy;
import com.uff.item.rating.application.strategy.RatingUserBasedStrategy;

public class RatingPredictionStrategyFactory {

	public static RatingPredictionStrategy getPredictionStrategy(PredictionType predictionType) throws InvalidPredictionStrategyException {
		if (PredictionType.ITEM_BASED.equals(predictionType)) {
			return new RatingItemBasedStrategy();
		}
		
		if (PredictionType.USER_BASED.equals(predictionType)) {
			return new RatingUserBasedStrategy();
		}
		
		throw new InvalidPredictionStrategyException("Invalid prediction Strategy.");
	}

}
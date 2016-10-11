package com.uff.item.rating.application.strategy;

import java.util.List;

import com.uff.item.rating.application.domain.User;

public interface RatingPredictionStrategy {
	
	public String predictRating(List<User> users, User user, String itemName);

}
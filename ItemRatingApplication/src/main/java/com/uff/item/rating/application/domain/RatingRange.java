package com.uff.item.rating.application.domain;

public enum RatingRange {
	
	MAX_RATING("5"),
	MIN_RATING("1"),
	NOT_RATED("?");
	
	private String rating;
	
	RatingRange(String rating) {
		this.rating = rating;
	}
	
	public String getRating() {
	    return rating;
    }
	
}
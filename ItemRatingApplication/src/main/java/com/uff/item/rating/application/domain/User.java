package com.uff.item.rating.application.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class User {
	
	private String name;
	private List<Item> itemRatings;
	
	public User() {}
	
	public User(UserBuilder userBuilder) {
		this.name = userBuilder.name;
		this.itemRatings = userBuilder.itemRatings;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Item> getItemRatings() {
		if (itemRatings == null) {
			itemRatings = new ArrayList<Item>();
		}
		
		return itemRatings;
	}
	
	public void setItemRatings(List<Item> itemRatings) {
		this.itemRatings = itemRatings;
	}
	
	public String getRatingByItem(String itemName) {
		for (Item item : getItemRatings()) {
			if (itemName.equals(item.getName())) {
				return item.getRating();
			}
		}
		
		return null;
	}
	
	public BigDecimal calculateAverageRating() {
		BigDecimal sumRatings = BigDecimal.ZERO;
		
		for (Item item : getItemRatings()) {
			if (!RatingRange.NOT_RATED.getRating().equals(item.getRating())) {
				sumRatings = sumRatings.add(new BigDecimal(item.getRating()));
			}
		}
		
		return sumRatings.divide(BigDecimal.valueOf(getTotalValidRatings()), 3, RoundingMode.HALF_EVEN);
	}
	
	public Integer getTotalValidRatings() {
		Integer total = 0;
		
		for (Item item : getItemRatings()) {
			if (!RatingRange.NOT_RATED.getRating().equals(item.getRating())) {
				total++;
			}
		}
		
		return total;
	}
	
	public Boolean hasRatedSameItems(User otherUser, String itemName) {
		for (Item item : getItemRatings()) {
			for (Item otherItem : otherUser.getItemRatings()) {
				if (item.getName().equals(otherItem.getName()) && !item.getName().equals(itemName) &&
				   (RatingRange.NOT_RATED.getRating().equals(otherItem.getRating()) || RatingRange.NOT_RATED.getRating().equals(item.getRating()))) {
					
					return Boolean.FALSE;
				}
			}
		}
		
		return Boolean.TRUE;
	}
	
	public Boolean hasRatedItem(String itemName) {
		for (Item item : getItemRatings()) {
			if (itemName.equals(item.getName()) && !RatingRange.NOT_RATED.getRating().equals(item.getRating())) {
				return Boolean.TRUE;
			}
		}
		
		return Boolean.FALSE;
	}
	
	public static UserBuilder builder() {
		return new UserBuilder();
	}
	
	public static class UserBuilder {
		
		private String name;
		private List<Item> itemRatings;
		
		public UserBuilder name(String name) {
			this.name = name;
			return this;
		}
		
		public UserBuilder itemRatings(List<Item> itemRatings) {
			this.itemRatings = itemRatings;
			return this;
		}
		
		public User build() {
			return new User(this);
		}
	}

}
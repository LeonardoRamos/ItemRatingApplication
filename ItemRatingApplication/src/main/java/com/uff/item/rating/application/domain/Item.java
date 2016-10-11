package com.uff.item.rating.application.domain;

public class Item {
	
	private String name;
	private String rating;
	
	public static final String NOT_RATED = "?";
	
	public Item() {}
	
	public Item(ItemBuilder itemBuilder) {
		this.name = itemBuilder.name;
		this.rating = itemBuilder.rating;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}
	
	public static ItemBuilder builder() {
		return new ItemBuilder();
	}
	
	public static class ItemBuilder {
		
		private String name;
		private String rating;
		
		public ItemBuilder name(String name) {
			this.name = name;
			return this;
		}
		
		public ItemBuilder rating(String rating) {
			this.rating = rating;
			return this;
		}
		
		public Item build() {
			return new Item(this);
		}
	}	

}
package com.uff.item.rating.application.dao.mapper;

import java.util.List;
import java.util.logging.Logger;

import com.uff.item.rating.application.domain.Item;
import com.uff.item.rating.application.domain.User;

public class UserMapper extends CsvMapper {
	
	private static final Logger log = Logger.getLogger(ItemMapper.class.getName());

	private static final String CAN_NOT_MAP_MESSAGE = "Can not map empty data.";
	private static final Integer USER_NAME_INDEX = 0;

	public static User mapUser(List<Item> itemsResult, String data) {
		if (data == null || itemsResult == null || itemsResult.isEmpty()) {
			log.warning(CAN_NOT_MAP_MESSAGE);
			return null;
		}
		
		String[] userData = data.split(CSV_DELIMITER);
		User user = User.builder()
						.name(userData[USER_NAME_INDEX])
						.build();
		
		processItemsRates(itemsResult, userData, user);
		
		return user;
	}

	private static void processItemsRates(List<Item> itemsResult, String[] userData, User user) {
		for (int i = USER_NAME_INDEX + 1; i < userData.length; i++) {
			user.getItemRatings().add(Item.builder()
										  .name(itemsResult.get(i - 1).getName())
										  .rating(userData[i])
										  .build());
		}
	}
	
}
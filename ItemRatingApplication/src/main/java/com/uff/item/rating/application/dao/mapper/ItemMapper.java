package com.uff.item.rating.application.dao.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.uff.item.rating.application.domain.Item;

public class ItemMapper extends CsvMapper {
	
	private static final Logger log = Logger.getLogger(ItemMapper.class.getName());

	private static final String CAN_NOT_MAP_MESSAGE = "Can not map empty data.";

	public static List<Item> mapItems(String data) throws Exception {
		if (data == null) {
			log.warning(CAN_NOT_MAP_MESSAGE);
			return null;
		}
		
		List<Item> items = new ArrayList<Item>();
		String[] itensData = data.split(CSV_DELIMITER);
		
		for (int i = 0; i < itensData.length; i++) {
			items.add(Item.builder().name(itensData[i]).build());
		}

		return items;
	}
	
}
package com.jphilips.inventorymanagementapi.dto.mapper;

import com.jphilips.inventorymanagementapi.dto.ItemRequestDTO;
import com.jphilips.inventorymanagementapi.dto.ItemResponseDTO;
import com.jphilips.inventorymanagementapi.entity.Item;

public class ItemMapper {
	public static ItemResponseDTO toDto (Item item) {
		return new ItemResponseDTO(
						item.getId(),
						item.getName(), 
						item.getDescription(),
						item.getStocks(),
						item.getPrice());
	}
	
	public static Item toEntity(ItemRequestDTO itemRequestDTO) {
		Item item = new Item();
		
		item.setName(itemRequestDTO.getName());
		item.setDescription(itemRequestDTO.getDescription());
		item.setStocks(itemRequestDTO.getStocks());
		item.setPrice(itemRequestDTO.getPrice());
		
		return item;
	}
}

package com.jphilips.inventorymanagementapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ItemResponseDTO {
	private Long id;
	
	private String name;
	
	private String description;
	
	private Integer stocks;
	
	private Double price;
}

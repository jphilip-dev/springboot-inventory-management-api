package com.jphilips.inventorymanagementapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestDTO {
	
	@NotBlank
	private String name;
	
	@NotBlank
	@Size(min = 10, message = "At least 10 characters")
	private String description;
	
	@NotNull
	@Min(value = 1, message = "At least 1")
	private Integer stocks;
	
	@NotNull
	@Min(value = 1, message = "At least 1")
	private Double price;
}

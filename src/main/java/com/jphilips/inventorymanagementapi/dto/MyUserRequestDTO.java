package com.jphilips.inventorymanagementapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUserRequestDTO {
	@Size(min = 6, max = 32, message = "Accepts only 6 - 32 characters")
	private String username;
	
	@Size(min = 6, message = "At least 6 characters")
	private String password;
	
	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
}

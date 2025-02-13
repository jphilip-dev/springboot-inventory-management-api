package com.jphilips.inventorymanagementapi.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MyUserUpdateDTO {
	@Size(min = 6, max = 32, message = "Accepts only 6 - 32 characters")
	private String firstName;
	
	@Size(min = 6, message = "At least 6 characters")
	private String lastName;
	
	@NotBlank
	private String password;
	
	@NotNull
	private Boolean isActive;
	
	@NotEmpty
	private List<String> roles;
}

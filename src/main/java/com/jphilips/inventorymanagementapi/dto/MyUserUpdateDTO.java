package com.jphilips.inventorymanagementapi.dto;

import java.util.List;

import lombok.Data;

@Data
public class MyUserUpdateDTO {
	private String firstName;

	private String lastName;
	
	private String password;
	
	private Boolean isActive;

	private List<String> roles;
}

package com.jphilips.inventorymanagementapi.dto;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MyUserResponseWithRolesDTO {
	private Long id;

	private String username;

	private String firstName;

	private String lastName;
	
	private Boolean isActive;

	private List<String> roles;
}

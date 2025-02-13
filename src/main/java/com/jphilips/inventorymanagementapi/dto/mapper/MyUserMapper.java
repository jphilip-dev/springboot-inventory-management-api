package com.jphilips.inventorymanagementapi.dto.mapper;

import java.util.stream.Collectors;

import com.jphilips.inventorymanagementapi.dto.MyUserRequestDTO;
import com.jphilips.inventorymanagementapi.dto.MyUserResponseDTO;
import com.jphilips.inventorymanagementapi.entity.MyUser;

public class MyUserMapper {
	
	public static MyUserResponseDTO toDto(MyUser myUser) {
		return new MyUserResponseDTO(
								myUser.getId(),
								myUser.getUsername(), 
								myUser.getFirstName(),
								myUser.getLastName(), 
								myUser.isActive(),
								myUser.getRoles().stream()
										.map(role -> role.getRole())
										.collect(Collectors.toList()));
	}
	
	public static MyUser toEntity(MyUserRequestDTO myUserRequestDTO) {
		MyUser newUser = new MyUser();
		
		newUser.setUsername(myUserRequestDTO.getUsername());
		newUser.setPassword(myUserRequestDTO.getPassword());
		newUser.setFirstName(myUserRequestDTO.getFirstName());
		newUser.setLastName(myUserRequestDTO.getLastName());
		
		return newUser;
		
	}
}

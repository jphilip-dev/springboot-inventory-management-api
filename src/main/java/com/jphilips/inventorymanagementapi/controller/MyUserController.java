package com.jphilips.inventorymanagementapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jphilips.inventorymanagementapi.dto.MyUserResponseDTO;
import com.jphilips.inventorymanagementapi.dto.MyUserResponseWithRolesDTO;
import com.jphilips.inventorymanagementapi.dto.MyUserUpdateDTO;
import com.jphilips.inventorymanagementapi.service.MyUserService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/users")
@Valid
public class MyUserController {

	private MyUserService myUserService;

	public MyUserController(MyUserService myUserService) {
		this.myUserService = myUserService;
	}
	
	@GetMapping()
	public Page<MyUserResponseDTO> getAllUsers(Pageable pageable) {
		return myUserService.getAllUsers(pageable);
	}
	
	@GetMapping("/{id}")
	public MyUserResponseWithRolesDTO getUserById(@PathVariable Long id ) {
		return myUserService.getUserById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void>  updateUser(@PathVariable Long id ,@Valid @RequestBody MyUserUpdateDTO myUserUpdateDTO) {
		myUserService.updateUser(id, myUserUpdateDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}

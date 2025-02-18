package com.jphilips.inventorymanagementapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jphilips.inventorymanagementapi.dto.LoginRequestDTO;
import com.jphilips.inventorymanagementapi.dto.MyUserRequestDTO;
import com.jphilips.inventorymanagementapi.dto.TokenResponseDTO;
import com.jphilips.inventorymanagementapi.service.AuthService;
import com.jphilips.inventorymanagementapi.service.JwtService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
@Valid
public class AuthController {
	
	private AuthService authService;
	private JwtService jwtService;
	
	public AuthController(AuthService authService,JwtService jwtService) {
		this.authService = authService;
		this.jwtService = jwtService;
	}

	@PostMapping("/login")
	public TokenResponseDTO login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
		
		String username = authService.login(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
		
		String token = jwtService.generateToken(username);
		
		return new TokenResponseDTO(token);
	}
	
	@PostMapping("/register")
	public ResponseEntity<Void> register(@Valid @RequestBody MyUserRequestDTO myUserRequestDTO) {
		
		authService.addUser(myUserRequestDTO);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	
}

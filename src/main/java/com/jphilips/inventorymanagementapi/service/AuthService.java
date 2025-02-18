package com.jphilips.inventorymanagementapi.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jphilips.inventorymanagementapi.dto.MyUserRequestDTO;
import com.jphilips.inventorymanagementapi.dto.mapper.MyUserMapper;
import com.jphilips.inventorymanagementapi.entity.MyUser;
import com.jphilips.inventorymanagementapi.exception.custom.UsernameAlreadyExistsException;
import com.jphilips.inventorymanagementapi.repository.MyUserRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthService {
	
	private MyUserRepository myUserRepository;
	private PasswordEncoder encoder;
	private AuthenticationManager authenticationManager;
	
	public AuthService(MyUserRepository myUserRepository, PasswordEncoder encoder,AuthenticationManager authenticationManager) {
		this.myUserRepository = myUserRepository;
		this.encoder = encoder;
		this.authenticationManager = authenticationManager;
	}

	@Transactional
	public void addUser(MyUserRequestDTO myUserRequestDTO) {
		if (checkUsernameExist(myUserRequestDTO.getUsername())) {
			throw new UsernameAlreadyExistsException();
		}

		MyUser newUser = MyUserMapper.toEntity(myUserRequestDTO);

		newUser.setPassword(encoder.encode(myUserRequestDTO.getPassword()));
		newUser.setActive(true);

		newUser.addRole("USER");
		
		myUserRepository.save(newUser);
	}
	
	public String login(String username, String password) {
		
		Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(username, password));
		
		return authentication.getName();

	}
			
	// helper methods

		private boolean checkUsernameExist(String username) {
			MyUser user = myUserRepository.findByUsername(username).orElse(null);

			if (user == null) {
				return false;
			}

			return true;

		}
}

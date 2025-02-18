package com.jphilips.inventorymanagementapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jphilips.inventorymanagementapi.dto.MyUserResponseDTO;
import com.jphilips.inventorymanagementapi.dto.MyUserResponseWithRolesDTO;
import com.jphilips.inventorymanagementapi.dto.MyUserUpdateDTO;
import com.jphilips.inventorymanagementapi.dto.mapper.MyUserMapper;
import com.jphilips.inventorymanagementapi.entity.MyUser;
import com.jphilips.inventorymanagementapi.repository.MyUserRepository;

import jakarta.transaction.Transactional;

@Service
public class MyUserService implements UserDetailsService{
	private PasswordEncoder encoder;
	private MyUserRepository myUserRepository;
	
	public MyUserService(MyUserRepository myUserRepository,PasswordEncoder encoder) {
		this.myUserRepository = myUserRepository;
		this.encoder=encoder;
	}
	
	public Page<MyUserResponseDTO> getAllUsers(Pageable pageable) {

		Page<MyUser> users = myUserRepository.findAll(pageable);

		return users.map(user -> MyUserMapper.toDto(user));
	}

	public MyUserResponseWithRolesDTO getUserById(Long id) {
		return MyUserMapper.withRolesToDto(myUserRepository.findById(id).orElseThrow(null));
	}

	public MyUserResponseWithRolesDTO getUserByUsername(String username) {
		return MyUserMapper.withRolesToDto(myUserRepository.findByUsername(username).orElseThrow(null));
	}

	
	
	@Transactional
	public void updateUser(Long id, MyUserUpdateDTO myUserUpdateDTO) {
		MyUser user = loadByUserId(id);
		
		user.setFirstName(myUserUpdateDTO.getFirstName());
		user.setLastName(myUserUpdateDTO.getLastName());
		user.setPassword(encoder.encode(myUserUpdateDTO.getPassword()) ); // need to encrypt first, ill do this later after adding security
		
		// check authentication, only allow if have Admin role, later after adding security
		user.setActive(myUserUpdateDTO.getIsActive());
		
		// Remove all existing roles
	    if (user.getRoles() != null) {
	        user.getRoles().clear();
	    }
		
		for (String role : myUserUpdateDTO.getRoles()) {
	        user.addRole(role);
	    }
		
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return myUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
	}


	// helper methods

	private MyUser loadByUserId(Long id) {
		return myUserRepository.findById(id).orElseThrow(null);
	}

	
}

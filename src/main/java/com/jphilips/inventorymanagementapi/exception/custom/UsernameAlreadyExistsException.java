package com.jphilips.inventorymanagementapi.exception.custom;

import org.springframework.security.core.AuthenticationException;

public class UsernameAlreadyExistsException extends AuthenticationException {
	
	private static final long serialVersionUID = -2892077142359534458L;
	
	public UsernameAlreadyExistsException() {
		super("Username Already Exists");
	}

	public UsernameAlreadyExistsException(String msg) {
		super(msg);
	}
	public UsernameAlreadyExistsException(String msg, Throwable cause) {
		super(msg, cause);
	}
}

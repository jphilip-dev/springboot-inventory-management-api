package com.jphilips.inventorymanagementapi.exception;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jphilips.inventorymanagementapi.exception.custom.ItemException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = ItemException.class)
	public ProblemDetail handleItemException(ItemException ex) {
		return createDetail(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), null);
	}
	
	@ExceptionHandler(value = JwtException.class)
	public ProblemDetail handleJwtException(JwtException ex) {
		
		String detail = "Invalid Token";
		
		if(ex instanceof ExpiredJwtException) {
			detail = "Expired Token";
		}
		
		return createDetail(HttpStatus.UNAUTHORIZED, detail, null);
	}
	
	@ExceptionHandler(value = AuthenticationException.class)
	public ProblemDetail handleAuthenticationException(AuthenticationException ex) {
		
		String detail = "Authentication Error";
		
		if(ex instanceof BadCredentialsException ) {
			detail = "Invalid Username or Password";
		}else if (ex instanceof UsernameNotFoundException) {
			detail = "Invalid Username";
		}
		
		return createDetail(HttpStatus.BAD_REQUEST, detail, null);
	}
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		ProblemDetail problemDetail = createDetail(HttpStatus.BAD_REQUEST, "Validation Error", null);
		
		Map<String, String> fieldErrors = new HashMap<>();
		
		for (FieldError err : ex.getBindingResult().getFieldErrors()) {
			fieldErrors.put(err.getField(), err.getDefaultMessage());
		}
		
		problemDetail.setProperty("fieldErrors", fieldErrors);
		
		return problemDetail;
	}

	@ExceptionHandler(value = Exception.class)
	public ProblemDetail handleOtherException(Exception ex) {
		System.err.println("Unhandled exception: \n" + ex);
		return createDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Please contact your administrator.", null);
	}

	// Helper method
	
	private ProblemDetail createDetail(HttpStatus status, String detail, String type) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
		if (type != null) {
			problemDetail.setType(URI.create(type));
		}
		problemDetail.setTitle(status.getReasonPhrase());
		problemDetail.setProperty("timestamp", Instant.now());
		return problemDetail;
	}

}

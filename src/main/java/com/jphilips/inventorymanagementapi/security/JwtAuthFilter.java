package com.jphilips.inventorymanagementapi.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.jphilips.inventorymanagementapi.service.JwtService;
import com.jphilips.inventorymanagementapi.service.MyUserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class JwtAuthFilter extends OncePerRequestFilter {

	private HandlerExceptionResolver resolver;
	private JwtService jwtService;
	private MyUserService myUserService;

	public JwtAuthFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver,JwtService jwtService,MyUserService myUserService) {
		this.resolver = resolver;
		this.jwtService = jwtService;
		this.myUserService = myUserService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// Skip token check for public end points (login, register)
	    if (request.getRequestURI().startsWith("/api/auth")) {
	        filterChain.doFilter(request, response);
	        return; 
	    }
		
		String token = extractToken(request);
		
		if (token == null) {
			// throw error
			handleException(request, response, new JwtException("Missing Token"));
			return;
		}
		
		try {
			Claims claims = jwtService.getClaims(token);
			
			String username = claims.getSubject();
			
			UserDetails userDetails = myUserService.loadUserByUsername(username);
			
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails, userDetails.getPassword(), userDetails.getAuthorities());
			
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);

		} catch (Exception e) {
			
			// Exception will filtered in the GlobalExceptionHandler
			handleException(request, response,  e);
			
			return;
		}
		
		filterChain.doFilter(request, response);
		
		

	}

	// helper methods

	private String extractToken(HttpServletRequest request) {

		String header = request.getHeader("Authorization");

		if (header != null && header.startsWith("Bearer ")) {
			return header.substring(7); // Remove the "Bearer " prefix
		}

		return null;
	}
	
	private void handleException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
	    resolver.resolveException(request, response, null, ex);
	}

}

package com.jphilips.inventorymanagementapi.service;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	@Value("${jwt.secret}")
    private  String SECRET;

    private static final long VALIDITY = TimeUnit.MINUTES.toMillis(30);
    
    public String generateToken(String username) {
    	Map<String, String> claims = new HashMap<String, String>();
		claims.put("iss", "InventoryManagement");
		return Jwts.builder()
				.claims(claims)
				.subject(username)
				.issuedAt(Date.from(Instant.now()))
				.expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
				.signWith(generateKey())
				.compact(); 
    }
    
    private SecretKey generateKey() {
        byte[] decodedKey = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(decodedKey);
    }
    
    public Claims getClaims(String jwt)  {
		Claims claims = Jwts.parser()
				.verifyWith(generateKey())
				.build()
				.parseSignedClaims(jwt)
				.getPayload();
		return claims;
	}
    
    
}

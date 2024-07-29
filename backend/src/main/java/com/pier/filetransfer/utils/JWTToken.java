package com.pier.filetransfer.utils;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

import com.pier.filetransfer.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTToken {
	private static final String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
	
	public static String generate(User user) {
		Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());
		Instant now = Instant.now();
		String jwtToken = Jwts.builder()
				.claim("name", user.getName())
				.claim("surname", user.getSurname())
				.setSubject(user.getUsername())
				.setId(UUID.randomUUID().toString())
				.setIssuedAt(Date.from(now))
				.setExpiration(Date.from(now.plus(24l, ChronoUnit.HOURS)))
				.signWith(hmacKey)
				.compact();
		return jwtToken;
	}
	
	public static Jws<Claims> validate(String jwtToken) {
		Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());
		
		Jws<Claims> claims = Jwts.parserBuilder()
				.setSigningKey(hmacKey)
				.build()
				.parseClaimsJws(jwtToken);
		
		return claims;
	}
}

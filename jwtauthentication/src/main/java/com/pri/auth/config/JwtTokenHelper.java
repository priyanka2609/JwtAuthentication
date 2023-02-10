package com.pri.auth.config;

import java.util.Date;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenHelper {
	
	
	public static final long JWT_TOKEN_VALIDITY = 5*60*60;
	private String secret = "JwtTokenKeyJwtTokenKeyJwtTokenKeyJwtTokenKeyJwtTokenKeyJwtTokenKeyJwtTokenKeyJwtTokenKey";

	public String getUsername(String token) {
		return getClaim(token, Claims::getSubject);
	}

	public Date getExpirationDate(String token) {
		return getClaim(token, Claims::getExpiration);
	}

	public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaims(token);
		return claimsResolver.apply(claims);
	}

	@SuppressWarnings("deprecation")
	private Claims getAllClaims(String token) {
//		SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(AppConstants.SECRET));

		return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();

	}

	private Boolean isExpired(String token) {
		final Date expiration = getExpirationDate(token);
		return expiration.before(new Date(System.currentTimeMillis()));
	}
	
	@SuppressWarnings("deprecation")
	public String generate(UserDetails userDetails) {
		return Jwts.builder().setSubject(userDetails.getUsername())
				.setIssuer(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ JWT_TOKEN_VALIDITY * 100))
				.signWith(SignatureAlgorithm.HS256, secret).compact();
	}
	
	//validate token
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsername(token);
		return (username.equals(userDetails.getUsername()) && !isExpired(token));
	}
}

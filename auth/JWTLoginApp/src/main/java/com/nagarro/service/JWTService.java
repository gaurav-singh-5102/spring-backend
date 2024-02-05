package com.nagarro.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTService {

public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
	
	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, username);
	}

	private String createToken(Map<String, Object> claims, String username) {
		 return Jwts.builder() 
	                .setClaims(claims) 
	                .setSubject(username) 
	                .setIssuedAt(new Date(System.currentTimeMillis())) 
	                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) 
	                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}
	
	private Key getSignKey() { 
        byte[] keyBytes= Decoders.BASE64.decode(SECRET); 
        return Keys.hmacShaKeyFor(keyBytes); 
    } 
	
	public String extractUsername(String token) { 
        return extractClaim(token, Claims::getSubject); 
    } 
	
	public Date extractExpiration(String token) { 
        return extractClaim(token, Claims::getExpiration); 
    } 
  
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) { 
        final Claims claims = extractAllClaims(token); 
        return claimsResolver.apply(claims); 
    } 
    
    private Claims extractAllClaims(String token) { 
        return Jwts 
                .parserBuilder() 
                .setSigningKey(getSignKey()) 
                .build() 
                .parseClaimsJws(token) 
                .getBody(); 
    } 
  
    private Boolean isTokenExpired(String token) { 
        return extractExpiration(token).before(new Date()); 
    } 
  
    public Boolean validateToken(String token) { 
        final String username = extractUsername(token); 
        return (username!=null && !isTokenExpired(token)); 
    } 

    public HashMap<?, ?> decodeJWT(String token) {
        String[] splitToken = token.split("\\.");
        String encodedBody = splitToken[1];
        Base64 base64 = new Base64(true);
        Gson gson = new Gson();
        String decodedBody = new String(base64.decode(encodedBody));
        System.out.println(decodedBody);
        HashMap<?, ?> body = gson.fromJson(decodedBody  , HashMap.class);
        return body;
    }

}

package com.nagarro.websockets.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTService {

	public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
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

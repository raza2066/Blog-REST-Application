package com.springboot.blog.security;

import com.springboot.blog.exception.BlogApiException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.modelmapper.internal.asm.tree.TryCatchBlockNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

// Utility class for generating, validating, and parsing JWT tokens
@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret; // Secret key for signing the JWT token

    @Value("${app-jwt-expiration-milliseconds}")
    private Long jwtExpirationDate; // JWT expiration time in milliseconds

    // =================================================================================================================================

    // Method to return the secret key used for signing the JWT token
    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // =================================================================================================================================

    // Method to generate JWT token using authentication details
    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();

        // Set expiration date
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        // Create and return the JWT token
        String token = Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(key())
                .compact();

        return token;
    }

    // =================================================================================================================================

    // Method to extract the username from the JWT token
    public String getUsername(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // =================================================================================================================================

    // Method to validate the JWT token by checking if it's malformed, expired, or unsupported
    public boolean validateToken(String token){

        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parse(token); // Validate the token
            return true;
        } catch (MalformedJwtException malformedJwtException) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid JWT Token");
        } catch (ExpiredJwtException expiredJwtException) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Expired JWT Token");
        } catch (UnsupportedJwtException unsupportedJwtException) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT Token");
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "JWT claims string is null or empty");
        }
    }
}

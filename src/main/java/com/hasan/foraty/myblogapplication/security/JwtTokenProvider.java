package com.hasan.foraty.myblogapplication.security;

import com.hasan.foraty.myblogapplication.exception.BlogApiException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app-jwt-expiration-milliseconds}")
    private long jwtExpirationDate;
    //Generate JWT Token
    public String generateToken(Authentication authentication){
        String username = authentication.getName();

        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime()+jwtExpirationDate);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(key())
                .compact();
    }


    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    public String  getUsername(String token){
        var parser = Jwts.parserBuilder()
                .setSigningKey(key())
                .build();
        return parser.parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    //validate Jwt token
    public boolean validateToken(String token){
        try {
            var parser = Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build();
            parser.parse(token);
            return true;
        }catch (MalformedJwtException | ExpiredJwtException ex){
            throw new BlogApiException("Invalid JWT Token",HttpStatus.BAD_REQUEST);
        }catch (UnsupportedJwtException ex){
            throw new BlogApiException("Unsupported JWT token",HttpStatus.BAD_REQUEST);
        }catch (IllegalArgumentException e){
            throw new BlogApiException("Jwt claims string is empty",HttpStatus.BAD_REQUEST);
        }
    }
}

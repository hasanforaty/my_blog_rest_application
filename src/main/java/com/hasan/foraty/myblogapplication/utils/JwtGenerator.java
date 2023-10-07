package com.hasan.foraty.myblogapplication.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class JwtGenerator {

    public static void main(String[] args) {
        String username = "Zahra31";

        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime()+60000);
        String  token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(key())
                .compact();
        System.out.println(token);
    }
    private static Key key(){
        String jwtSecret = "null";//add jwt secret here.....
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}

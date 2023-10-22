package com.zoro.userservice.utils;

import com.zoro.userservice.dtos.UserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.*;

@Setter
@Getter
@Service
public class JwtUtil {
    private String RandomSecretKey = "somerandomsecretkeygeneratedforlearning12345";

    public String generateToken(UserDto userDto){
        Map<String, Object> claims = new HashMap<>();
        claims.put("email",userDto.getEmail());
        claims.put("roles",userDto.getRoles());
        return createToken(claims,"Z0r0|Pratik");
    }

    private String createToken(Map<String,Object>claims,String issuer) {
        String jwtToken = Jwts.builder()
                .setClaims(claims)
                .issuer(issuer)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*60*24)) //Expires in 1 day
                .signWith(SignatureAlgorithm.HS256,RandomSecretKey)
                .compact();
        return jwtToken;
    }

    private Boolean isTokenExpired(String token){
        //TODO: Check if the token is expired
        return false;
    }

    public Boolean validateToken(String token, UserDto userDto){
        //TODO: Validate the token
        return true;
    }
}

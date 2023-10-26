package com.zoro.userservice.security;

import com.zoro.userservice.models.Role;
import com.zoro.userservice.models.Session;
import com.zoro.userservice.models.SessionStatus;
import com.zoro.userservice.models.User;
import com.zoro.userservice.repositories.SessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.*;


@Setter
@Getter
@Service
public class JwtUtil {
    private String RandomSecretKey = "somerandomsecretkeygeneratedforlearning12345";
    private final SessionRepository sessionRepository;

    public JwtUtil(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public String generateToken(User user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("email",user.getEmail());
        claims.put("roles",user.getRoles());
        claims.put("uid",user.getId());
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

    private Boolean isTokenExpired(Date createdAt){
        if(createdAt.before(Date.from(Instant.now().minus(Duration.ofDays(1)))))return true;
        return false;
    }

    public Boolean validateToken(String token){
        //TODO: Don't just return true or false, return a JwtObject

        Optional<Session>sessionOptional = sessionRepository.findByToken(token);
        if(sessionOptional.isEmpty()){
            return false;
        }

        Session session = sessionOptional.get();

        if(!session.getStatus().equals(SessionStatus.ACTIVE))return false;

        Jws<Claims> claims = Jwts.parser().setSigningKey(RandomSecretKey).build().parseSignedClaims(token);

        String email = (String) claims.getPayload().get("email");
        List<Role>roles = (List<Role>)claims.getPayload().get("roles");
        String userIDString = (String)claims.getPayload().get("uid");
        UUID userID = UUID.fromString(userIDString);
        Date createdAt = claims.getBody().getIssuedAt();
        String issuer = claims.getBody().getIssuer();

        if(!session.getUser().getId().equals(userID))return false;
        User user = session.getUser();

        if(isTokenExpired(createdAt))return false;
        if(!issuer.equals("Z0r0|Pratik"))return false;
        if(!email.equals(user.getEmail()))return false;
        if(!roles.equals(user.getRoles()))return false;

        return true;
    }
}

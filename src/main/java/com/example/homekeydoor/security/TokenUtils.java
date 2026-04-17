package com.example.homekeydoor.security;

import com.example.homekeydoor.consts.DateUtils;
import com.example.homekeydoor.consts.UserType;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

@Component
public class TokenUtils {

    private static final String USER_TYPE = "user-type";


    public static String getUsernameFromToken(String token, String secret) {
        String username;
        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

            username = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public static UserType getUserTypeFromToken(String token, String secret) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

            String type = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get(USER_TYPE, String.class);

            return UserType.getByLabel(type);

        } catch (Exception e) {
            return null;
        }
    }

    private static Date getExpirationDate(String token, String secret) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration();

        } catch (Exception e) {
            return null;
        }
    }

    public static String generateToken(UserDetails userDetails, UserType userType, String secret, long expiration) {
        Claims customClaims = (Claims) Jwts.claims();
        customClaims.put(USER_TYPE, userType);
        return Jwts.builder().setClaims(customClaims)
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public TokenUser getTokenUser(String token, String secret){
        try{
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secret)
                    .build().parseSignedClaims(token);

            String username = claims.getBody().getSubject();
            UserType userType = UserType.getByLabel(claims.getBody().get(USER_TYPE, String.class));
            Date date = claims.getBody().getExpiration();

            return new TokenUser(username, userType, DateUtils.asLocalDateTime(date));
        }
        catch (Exception exp){
            return null;
        }
    }
}

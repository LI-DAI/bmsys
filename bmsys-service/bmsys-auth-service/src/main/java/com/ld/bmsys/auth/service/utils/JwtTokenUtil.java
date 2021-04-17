package com.ld.bmsys.auth.service.utils;

import com.ld.bmsys.auth.service.security.vo.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lidai
 * @date 2018/11/2 16:21
 */
public class JwtTokenUtil {

    private static final Long EXPIRATION = 3 * 60 * 60 * 1000L;
    private static final String SECRET = "C*F-JaNdRgUkXn2r5u8x/A?D(G+KbPeShVmYq3s6v9y$B&E)H@McQfTjWnZr4u7w";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Base64.getEncoder().encode(SECRET.getBytes()));

    private static final JwtParser PARSER = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build();

    public static String createToken(AuthUser authUser) {
        return createToken(authUser.getUsername(), "");
    }

    public static String createToken(String username, String authorities) {
        final Date createdDate = new Date();
        final Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION);
        Map<String, String> claims = new HashMap<>(4);
        claims.put("username", username);
        claims.put("authorities", authorities);
        String token = Jwts.builder()
                .setHeaderParam("type", "JWT")
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .setClaims(claims)
                .setIssuer("LD")
                .setIssuedAt(createdDate)
                .setSubject(username)
                .setExpiration(expirationDate)
                .compact();
        return token;
    }


    public static Map<String, Object> parseJwtToken(String token) {
        return getClaims(token);
    }

    public static boolean isExpiration(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().before(new Date());
    }

    public static Claims getClaims(String token) {
        return PARSER.parseClaimsJws(token).getBody();
    }

    public static void main(String[] args) {
        String jwtToken = createToken("test", "test");
        System.out.println(jwtToken);
        System.out.println(parseJwtToken(jwtToken));
        System.out.println(isExpiration(jwtToken));
    }

}


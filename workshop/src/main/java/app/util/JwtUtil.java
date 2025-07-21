package app.util;

import app.entity.subclass.AuthInfo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {
    public static final String AUTHORITY_CLAIM = "authority";
    public static final String USER_AUTHORITY = "USER";
    public static final String ADMIN_AUTHORITY = "ADMIN";

    @Value("${jwt.secret}")
    private static String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    public String generateToken(AuthInfo authInfo) {
        return Jwts.builder()
                .setSubject(authInfo.getUsername())
                .claim(AUTHORITY_CLAIM, authInfo.getUser() == null ? USER_AUTHORITY : ADMIN_AUTHORITY)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(signKey())
                .compact();
    }

    public static SecretKey signKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static Claims extractToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static String extractSubject(String token) {
        return extractToken(token).getSubject();
    }

    public static boolean isTokenValid(String token, String username) {
        return (extractSubject(token).equals(username) && !isTokenExpired(token));
    }

    public static boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public static Date extractExpiration(String token) {
        return extractToken(token).getExpiration();
    }
}


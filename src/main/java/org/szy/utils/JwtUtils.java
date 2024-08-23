package org.szy.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtils {
    // in seconds, 7 days
    private static long EXPRIRE = 604800;
    private static String SECRET_KEY = "shydjdhegsbhdydjfb";

    /**
     * generate token by username
     * @param username
     * @return
     */
    public static String generateToken(Long userId) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + 1000 * EXPRIRE);
        // JWT: header.subject.signature
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
    public static Claims getClaimsByToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
    public static boolean isTokenExpired(String token) {
        try {
            final Date expiration = getClaimsByToken(token).getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            // invalid token
            return true;
        }
    }
}

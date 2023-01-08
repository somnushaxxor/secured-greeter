package ru.kolesnik.springsecuritydemo.auth.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.kolesnik.springsecuritydemo.auth.config.TokenConfigProperties;
import ru.kolesnik.springsecuritydemo.auth.exception.AccessTokenExpiredException;
import ru.kolesnik.springsecuritydemo.auth.exception.UnsupportedAccessTokenException;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtAccessTokenService implements AccessTokenService {

    private final TokenConfigProperties tokenConfigProperties;
    private final Key signingKey;

    public JwtAccessTokenService(TokenConfigProperties tokenConfigProperties) {
        this.tokenConfigProperties = tokenConfigProperties;
        final byte[] keyBytes = Decoders.BASE64.decode(tokenConfigProperties.getAccess().getSecretKey());
        signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public void checkTokenValid(String token) {
        isTokenExpired(token);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + tokenConfigProperties.getAccess().getDuration().toMillis())
                )
                .signWith(signingKey, SignatureAlgorithm.HS512)
                .compact();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new AccessTokenExpiredException();
        } catch (JwtException e) {
            throw new UnsupportedAccessTokenException();
        }
    }

}

package ru.kolesnik.securedgreeter.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.kolesnik.securedgreeter.auth.config.TokenConfigProperties;
import ru.kolesnik.securedgreeter.auth.exception.AccessTokenException;

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
    public void checkTokenValidity(String token) {
        extractAllClaims(token);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
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
        } catch (JwtException e) {
            throw new AccessTokenException(e);
        }
    }

}

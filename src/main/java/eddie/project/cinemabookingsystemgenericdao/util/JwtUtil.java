package eddie.project.cinemabookingsystemgenericdao.util;

import eddie.project.cinemabookingsystemgenericdao.dto.user.UserJwtResponseDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component // ✅ 讓 Spring 管理這個 Bean
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime; // 以秒為單位

    // ✅ 轉換 `secretKey` 為 `Key`
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // ✅ 產生 JWT Token
    public String generateToken(UserJwtResponseDTO userJwtResponseDTO) {
        return Jwts.builder()
                .setSubject(userJwtResponseDTO.getAccount())
                .claim("id", userJwtResponseDTO.getId())
                .claim("account", userJwtResponseDTO.getAccount())
                .claim("role", userJwtResponseDTO.getRole())
                .setIssuer("CinemaBookingSystem")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime * 1000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ 驗證並解析 Token
    public UserJwtResponseDTO validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return new UserJwtResponseDTO(
                    claims.get("id", Integer.class),
                    claims.get("account", String.class),
                    claims.get("role", Integer.class)
            );
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("錯誤的 token");
        }
    }

    // ✅ 檢查 Token 是否過期
    public boolean isTokenExpired(String token) {
        try {
            Date expiration = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();

            return expiration.before(new Date());
        } catch (JwtException e) {
            return true;
        }
    }

    // ✅ 刷新 Token
    public String refreshToken(String oldToken) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(oldToken)
                    .getBody();

            return Jwts.builder()
                    .setSubject(claims.getSubject())
                    .setClaims(claims)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + expirationTime * 1000))
                    .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (JwtException e) {
            throw new RuntimeException("無法刷新 Token");
        }
    }

    // ✅ 檢查 Token 是否有效
    public boolean isValidToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}

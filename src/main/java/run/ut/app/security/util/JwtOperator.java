package run.ut.app.security.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import run.ut.app.model.domain.User;
import run.ut.app.model.enums.UserRolesEnum;
import run.ut.app.security.token.AuthToken;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wenjie
 */
@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("WeakerAccess")
@Component
public class JwtOperator {
    /**
     * secret key
     */
    @Value("${secret:uuuuuuuuuuttttttttttuuuuuuuuuuttttttttttuuuuuuuuuuttttttttttuuuuuuuuuutttttttttt}")
    private String secret;

    // @Value("${expire-time-in-second:1209600}") // 2 weeks
    @Value("${expire-time-in-second:31536000}") // 10 year for test
    private Long expirationTimeInSecond;

    /**
     * get Claims from token
     *
     * @param token token
     * @return claim
     */
    public Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                .setSigningKey(this.secret.getBytes())
                .parseClaimsJws(token)
                .getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            log.error("token解析错误", e);
            throw new IllegalArgumentException("Token invalided.");
        }
    }


    /**
     * Get expiration date from token
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token)
            .getExpiration();
    }

    /**
     * @return Expired - true, Not Expired - false
     */
    private Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Calculate the expiration time of token
     */
    public Date getExpirationTime() {
        return new Date(System.currentTimeMillis() + this.expirationTimeInSecond * 1000);
    }

    /**
     * Generate token
     */
    public String generateToken(Map<String, Object> claims) {
        Date createdTime = new Date();
        Date expirationTime = this.getExpirationTime();


        byte[] keyBytes = secret.getBytes();
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(createdTime)
                .setExpiration(expirationTime)
                // more：https://github.com/jwtk/jjwt#features
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 判断token是否非法
     *
     * @param token token
     * @return 未过期返回true，否则返回false
     */
    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 构建token
     * @param user 用户信息
     * @return accessToken and expirationTime
     */
    public AuthToken buildAuthToken(@NonNull User user) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("uid", user.getUid());
        userInfo.put("openid", user.getOpenid());
        userInfo.put("roles", user.getRoles());

        return AuthToken.builder()
                .accessToken(this.generateToken(userInfo))
                .expirationTime(this.getExpirationTime().getTime()).build();
    }

}
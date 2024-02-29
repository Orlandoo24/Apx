package com.hqzl.apx.common.utils;

import com.hqzl.apx.common.constant.CommonConstants;
import com.hqzl.apx.common.constant.TokenConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import java.util.Map;

public class JwtUtil {

    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, TokenConstants.TOKEN_SECRET).compact();
    }


    public static Claims parseToken(String token) {
        if (!StringUtils.isEmpty(token) && token.startsWith(TokenConstants.TOKEN_PREFIX)) {
            token = token.replaceFirst(TokenConstants.TOKEN_PREFIX, "");
        }
        return Jwts.parser().setSigningKey(TokenConstants.TOKEN_SECRET).parseClaimsJws(token).getBody();
    }

    public static String getIdFromToken(Claims claims) {
        return claims.get("id").toString();
    }

    public static long getExpirationTimeFromToken(Claims claims) {
        return (long)claims.get("expirationTime");
    }
}

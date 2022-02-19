package top.goodz.future.domain.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import top.goodz.future.domain.model.vo.token.TokenTemplate;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.UUID;


public class JwtUtil {

    public static final String ACCESS_TOKEN_KEY = "Aa123456qwertyu!dgsgsgsgsgsgsgstgsgedhjrjrtjr";
    public static final String REFRESH_TOKEN_KEY = "bB123456qwertyu!dgsgsgsgsgsgsgstgsgedhjrjrtjr";

    private static final String USER_NAME = "userName";

    long now = System.currentTimeMillis();

    public  static TokenTemplate createJwtToken(Map<String, Object> map,int  keepliveTime) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        byte[] access_bytes = DatatypeConverter.parseBase64Binary(REFRESH_TOKEN_KEY);
        byte[] refresh_bytes = DatatypeConverter.parseBase64Binary(REFRESH_TOKEN_KEY);
        Key accessSecretKey = new SecretKeySpec(access_bytes, signatureAlgorithm.getJcaName());
        Key refreshSecretKey = new SecretKeySpec(refresh_bytes, signatureAlgorithm.getJcaName());

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
        Date issuedAt = Date.from(now.toInstant());

        String  accessToken = Jwts.builder()
                .setId("future-" + UUID.randomUUID().toString())
                .setSubject(map.get(USER_NAME).toString())
                .addClaims(map)
                .setIssuedAt(issuedAt)
                .setExpiration(Date.from(now.plusMinutes(keepliveTime).toInstant()))
                .signWith(SignatureAlgorithm.HS512, accessSecretKey)
                .claim(USER_NAME, map.get(USER_NAME))
                .compact();

        String  refreshToken = Jwts.builder()
                .setId("future-" + UUID.randomUUID().toString())
                .setSubject(map.get(USER_NAME).toString())
                .setIssuedAt(issuedAt)
                .setExpiration(Date.from(now.plusMinutes(keepliveTime).toInstant()))
                .signWith(SignatureAlgorithm.HS512, refreshSecretKey)
                .claim(USER_NAME, map.get(USER_NAME))
                .compact();

        return TokenTemplate.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


    public static Claims parseAccessToken(String  token) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        byte[] bytes = DatatypeConverter.parseBase64Binary(ACCESS_TOKEN_KEY);
        Key secretKey = new SecretKeySpec(bytes, signatureAlgorithm.getJcaName());

        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token).getBody();
    }

    public static Claims parseRefreshToken(String  token) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        byte[] bytes = DatatypeConverter.parseBase64Binary(REFRESH_TOKEN_KEY);
        Key secretKey = new SecretKeySpec(bytes, signatureAlgorithm.getJcaName());

        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token).getBody();
    }

}

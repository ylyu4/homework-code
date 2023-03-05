package tw.training.homework.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    private static final String SECRET_KEY = "THIS_IS_THE_SECRET_KEY";

    private static final String PREFIX = "Bearer";

    private static final long EXPIRATION_TIME = 60 * 60 * 24 * 1000;


    public static String generateToken(Long userId, String username) {

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("username", username);
        map.put("authority", "user");
        String jwt = Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

        return PREFIX + " " + jwt;
    }

    public static Map<String, Object> getTokenClaims(String token) {

        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token.replace(PREFIX, "")).getBody();

    }

}

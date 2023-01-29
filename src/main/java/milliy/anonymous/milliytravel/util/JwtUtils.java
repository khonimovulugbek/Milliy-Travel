package milliy.anonymous.milliytravel.util;

import lombok.extern.slf4j.Slf4j;
import milliy.anonymous.milliytravel.dto.profile.JwtDTO;
import milliy.anonymous.milliytravel.exception.AppBadRequestException;
import io.jsonwebtoken.*;

import java.util.Date;

@Slf4j
public class JwtUtils {

    private final static String secretKey = "sekret";

    public static String encode(String phone) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setSubject(phone);
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS256, secretKey);

        return jwtBuilder.compact();
    }

    public static JwtDTO decode(String jwt) {
        try {
            JwtParser jwtParser = Jwts.parser();

            jwtParser.setSigningKey(secretKey);
            Jws<Claims> jws = jwtParser.parseClaimsJws(jwt);

            Claims claims = jws.getBody();

            String phone = claims.getSubject();

            return new JwtDTO(phone);

        } catch (JwtException e) {
            log.error("<< decode JWT invalid!" + jwt);
            return null;
        }
    }

}

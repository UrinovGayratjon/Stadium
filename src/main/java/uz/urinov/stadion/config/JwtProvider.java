package uz.urinov.stadion.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.urinov.stadion.entity.enums.RoleEnum;

import java.util.Date;

@Component
public class JwtProvider {
    static long expireTime = 1_000 * 3_600 * 24;
    String password = "12345";

    public String generateToken(String username, RoleEnum roleEnum) {


        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .claim("roles", roleEnum.name())
                .signWith(SignatureAlgorithm.HS512, password)
                .compact();
    }


    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(password)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsername(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(password)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

        } catch (Exception e) {
            return null;
        }
    }

}

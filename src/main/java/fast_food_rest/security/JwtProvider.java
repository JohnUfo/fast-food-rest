package fast_food_rest.security;

import fast_food_rest.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    private final String secretKey = "BuTokenningMaxfiySoziHechKimBilmasim123456789012341234123421341241241234213412354rfgfdvcrtfbfdbfgvbfdbv"; // generates a secure key for HS512

    public String generateToken(String email, User user) {
        return Jwts.builder()
                .claim("fullName",user.getFullName())
                .claim("roles",user.getRoles())
                .setSubject(email)
                .setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        try {
            String email = Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
            return email;
        } catch (Exception e) {
            return null;
        }
    }
}
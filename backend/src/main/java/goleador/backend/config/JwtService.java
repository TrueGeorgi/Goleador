package goleador.backend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {

    private static final String SECRET_KEY = "Bfu91T7pRtROolg3oQwuoo/mwowSqZQCQF9/tm49pxY78wyfFTUANUScJpVQVXXw";

    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {

        int milliseconds = 1000;
        int seconds = 60;
        int minutes = 60;
        int hours = 24;

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + milliseconds * seconds * minutes * hours))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        this.refreshTokenIfExpired(token, userDetails);
        return (username.equals(userDetails.getUsername()));
    }

    private void refreshTokenIfExpired(String token, UserDetails userDetails) {
        if (extractExpiration(token).before(new Date())) {
            this.generateToken(new HashMap<>(),userDetails);
        }
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
   }

    private Key getSignInKey() {

        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }

}

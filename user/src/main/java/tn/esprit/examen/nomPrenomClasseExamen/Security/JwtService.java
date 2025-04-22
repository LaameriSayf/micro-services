package tn.esprit.examen.nomPrenomClasseExamen.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.entities.User;
import tn.esprit.examen.nomPrenomClasseExamen.repositories.UserRepository;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Autowired
    private UserRepository userRepository;

    private static final long jwtExpiration =8640000;

    private static final String SECRET_KEY = "Hi7isV+331vIL/OyyMAgDov2wKus6avDz2nhO/D9gsr1/gJcPJAbxR7mZd0djHl6cgIvdQJ49ByBqAAJ2ihn6wjpE7BhqoS7TPwlLmE5DsakFAFJDHimxddWz2fo45QBw48h5Z4TW7QhRyHK2bsQiF5AV3tXpTO3SQtwaXVTKo7CZ1YYw3Fbzf4k9mKoCamLUaYM3KvXZRCR/8w+nYYPKXYZtYycHf9KFDEWVchSrjHk7dAXoyOCnMkD8tn5lbN/yOIsEe1LMAOqbQIo4GOSux38l+RV4mZGmzWMCQDSnnwq4hcyFNd8DR0E/hugzdDmo90HRw8FAOIucy9tG//aaoYaoZvzvA0J0mU+GXp+ZUM=";
    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);//subject containes username or email of user this how we can ectract
    }
    public String extractPrenom(String token) {
        return extractClaim(token, claims -> claims.get("prenom", String.class));
    }


    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final  Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return buildToken(claims, userDetails, jwtExpiration);
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username=extractUsername(token);
        return (username.equals(userDetails.getUsername()))&&!isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, Long jwtExpiration) {


        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<String> roles = user.getRoles().stream()
                .map(role -> role.getTypeRole().name())
                .collect(Collectors.toList());

        Map<String, Object> claims = new HashMap<>(extraClaims);
        claims.put("roles", roles);
        claims.put("idUser", user.getId());


        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey())
                .compact();
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setAllowedClockSkewSeconds(60 * 60 * 24) // Tolérance de 24h
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes); //one of allgoritheme hmacshakeyfor

    }
}

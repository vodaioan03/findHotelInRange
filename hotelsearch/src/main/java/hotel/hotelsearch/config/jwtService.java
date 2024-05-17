package hotel.hotelsearch.config;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import hotel.hotelsearch.user.model.myUser;
import hotel.hotelsearch.user.repository.userRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class jwtService {
  @Value("${security.jwt.expiration-minutes}")
  private long EXPIRATION_MINUTES;

  @Value("${security.jwt.secret-key}")
  private String SECRET_KEY;

  @Autowired
  private userRepository repository;

  public String generateToken(myUser user, Map<String, Object> extraClaims) {
    Date issuedAt = new Date();
    Date expirationAt = new Date(issuedAt.getTime() + (EXPIRATION_MINUTES * 60 * 1000));

    return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(user.getUsername())
            .setIssuedAt(issuedAt)
            .setExpiration(expirationAt)
            .signWith(generateKey(), SignatureAlgorithm.HS256)
            .compact();
  }

  public String refreshToken(String jwt) {
    Claims claims = null;
    try {
        claims = extractAllClaims(jwt);
    } catch (Exception e) {
        System.err.println("Exception during extracting claims: " + e.getMessage());
    }

    if (claims != null && claims.getExpiration().before(new Date())) {
        myUser user = repository.findUserByUsername(claims.getSubject()).orElse(null);
        if (user != null) {
            return generateToken(user, claims);
        } else {
            System.err.println("User associated with the claims does not exist.");
        }
    } else {
      if (claims == null ) System.err.println("Claims extraction failed.");
    }
    return jwt;
}


  public String extractUsername(String jwt) {
      String subject = extractAllClaims(jwt).getSubject();
      return subject;
  }

  private Claims extractAllClaims(String jwt) {
    try {
        return Jwts.parserBuilder()
                .setSigningKey(generateKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    } catch (ExpiredJwtException ex) {
        System.err.println("JWT token is expired: " + ex.getMessage());
        return ex.getClaims(); 
    } catch (Exception e) {
        System.err.println("Exception during parsing JWT token: " + e.getMessage());
        return null; 
    }
  }


  private Key generateKey() {
      byte[] secretAsBytes = Decoders.BASE64.decode(SECRET_KEY);
      return Keys.hmacShaKeyFor(secretAsBytes);
  }
}

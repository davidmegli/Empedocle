//package it.unifi.ing.stlab.auth.jwt;
//
//import it.unifi.ing.stlab.users.model.User;
//import io.jsonwebtoken.*;
//
//import javax.enterprise.context.ApplicationScoped;
//import java.util.Date;
//
//@ApplicationScoped
//public class JWTService {
//
//    private static final String SECRET = "my-very-secure-jwt-secret-key"; // TODO: Use Environment variable in production
//    private static final long EXPIRATION = 24 * 60 * 60 * 1000; // 24 hours
//
//    public String generateToken(User user) {
//        return Jwts.builder()
//                .setSubject(user.getName())
//                .claim("id", user.getId())
//                .claim("roles", user.getRoles().stream().map(r -> r.getName()).toArray())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
//                .signWith(SignatureAlgorithm.HS256, SECRET)
//                .compact();
//    }
//
//    public Jws<Claims> parseToken(String token) throws JwtException {
//        return Jwts.parser()
//                .setSigningKey(SECRET)
//                .parseClaimsJws(token);
//    }
//}

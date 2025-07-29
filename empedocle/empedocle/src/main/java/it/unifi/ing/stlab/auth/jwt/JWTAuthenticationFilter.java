//package it.unifi.ing.stlab.auth.jwt;
//
//import it.unifi.ing.stlab.users.dao.UserDao;
//import it.unifi.ing.stlab.users.model.User;
//
//import javax.annotation.Priority;
//import javax.inject.Inject;
//import javax.ws.rs.Priorities;
//import javax.ws.rs.container.*;
//import javax.ws.rs.core.HttpHeaders;
//import javax.ws.rs.ext.Provider;
//import java.io.IOException;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jws;
//
//import javax.ws.rs.core.Response;
//
//
//@Provider
//@Priority(Priorities.AUTHENTICATION)
//public class JWTAuthenticationFilter implements ContainerRequestFilter {
//
//    @Inject
//    private JWTService jwtService;
//
//    @Inject
//    private UserDao userDao;
//
//    @Override
//    public void filter(ContainerRequestContext requestContext) throws IOException {
//        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            return; // No token = no authentication, the resource can check if needed
//        }
//
//        String token = authHeader.substring("Bearer ".length()).trim();
//
//        try {
//            Claims claims = jwtService.parseToken(token).getBody();
//            String username = claims.getSubject();
//            User user = userDao.findByUsername(username);
//
//            if (user != null) {
//                requestContext.setProperty("loggedUser", user);
//            }
//        } catch (Exception e) {
//            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
//        }
//    }
//}

//package it.unifi.ing.stlab.auth.api;
//
//import it.unifi.ing.stlab.users.dao.UserDao;
//import it.unifi.ing.stlab.users.model.User;
//import it.unifi.ing.stlab.users.model.PasswordHash;
//import it.unifi.ing.stlab.auth.jwt.JWTService;
//
//import javax.inject.Inject;
//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//@Path("/auth")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
//public class AuthResource {
//
//    @Inject
//    private UserDao userDao;
//
//    @Inject
//    private JWTService jwtService;
//
//    public static class AuthRequest {
//        public String username;
//        public String password;
//    }
//
//    public static class AuthResponse {
//        public String token;
//        public AuthResponse(String token) {
//            this.token = token;
//        }
//    }
//
//    @POST
//    @Path("/login")
//    public Response login(AuthRequest request) {
//        if (request.username == null || request.password == null) {
//            return Response.status(Response.Status.BAD_REQUEST).build();
//        }
//
//        User user = userDao.findByUsername(request.username);
//        if (user == null || !PasswordHash.createPasswordKey(request.password).equals(user.getPassword())) {
//            return Response.status(Response.Status.UNAUTHORIZED).build();
//        }
//
//        String token = jwtService.generateToken(user);
//        return Response.ok(new AuthResponse(token)).build();
//    }
//}

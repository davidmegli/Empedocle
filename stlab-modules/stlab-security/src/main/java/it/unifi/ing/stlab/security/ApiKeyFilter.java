package it.unifi.ing.stlab.security;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class ApiKeyFilter implements ContainerRequestFilter {

    // API Key. TODO: Replace with a secure method of storing and retrieving the API key.
    private static final String API_KEY = "MY_SECRET_TOKEN_123";

    private static final String HEADER_NAME = "X-API-KEY";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String apiKey = requestContext.getHeaderString(HEADER_NAME);

        if (apiKey == null || !apiKey.equals(API_KEY)) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("Invalid or missing API Key")
                            .build()
            );
        }
    }
}

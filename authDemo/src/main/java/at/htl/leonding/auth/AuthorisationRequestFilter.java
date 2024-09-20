package at.htl.leonding.auth;

import jakarta.annotation.Priority;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.util.Arrays;

import static at.htl.leonding.auth.AuthenticationRequestFilter.CREDENTIALS;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorisationRequestFilter implements ContainerRequestFilter {

    @Inject
    Logger logger;

    @Context
    ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        logger.info("Authorisation request filter");

        var annotations = resourceInfo.getResourceClass().getAnnotations();
        var annotation = Arrays.stream(annotations).filter(ann -> ann.annotationType().equals(PermitAll.class))
                .findFirst().orElse(null);

        if (annotation == null) {
            Credentials credentials = (Credentials) containerRequestContext.getProperty(CREDENTIALS);

            if(!credentials.username().equals("admin") || !credentials.password().equals("password")) {
                containerRequestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
            }
        }
    }
}

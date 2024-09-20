package at.htl.leonding.auth;

import jakarta.annotation.Priority;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationRequestFilter implements ContainerRequestFilter {

    @Inject
    Logger logger;

    @Context
    ResourceInfo resourceInfo;

    @Inject
    Base64AuthenticationParser base64Parser;
    public static final String CREDENTIALS = AuthenticationRequestFilter.class.getSimpleName() + ".credentials";

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {

        var annotations = resourceInfo.getResourceClass().getAnnotations();
        var annotation = Arrays.stream(annotations).filter(ann -> ann.annotationType().equals(PermitAll.class))
                .findFirst().orElse(null);

        if(annotation == null) {
            containerRequestContext.getHeaders().forEach((key, value) -> {
                logger.infof("header: %s=%s", key, value);
            });
            logger.info("Es gibt mich");
            var auth = containerRequestContext.getHeaders().getFirst("Authorization");
            if(auth == null) {
                containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
            basicAuthBase64(auth, containerRequestContext);
        }

    }

    public void basicAuthBase64(String auth, ContainerRequestContext containerRequestContext) {
        Credentials credentials = base64Parser.parseAuthenticationHeader(auth);
        containerRequestContext.setProperty(CREDENTIALS, credentials);
    }
}

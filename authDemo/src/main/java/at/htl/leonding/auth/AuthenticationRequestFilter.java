package at.htl.leonding.auth;

import jakarta.annotation.Priority;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
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

    @Inject
    Base64AuthenticationParser base64Parser;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
//        var annot = Arrays.stream(containerRequestContext.getRequest().getClass()
//                .getAnnotations()).filter(annotation -> annotation.getClass().equals(PermitAll.class))
//                .findFirst().orElse(null);

        containerRequestContext.getHeaders().forEach((key, value) -> {
            logger.infof("header: %s=%s", key, value);
        });
        logger.info("Es gibt mich");
        var auth = containerRequestContext.getHeaders().getFirst("Authorization");
        if(auth == null) {
            containerRequestContext.abortWith(Response.status(Response.Status.NETWORK_AUTHENTICATION_REQUIRED).build());
        }
        else if(!basicAuthBase64(auth)) {
            containerRequestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
        }
    }

    public boolean checkAuth(String auth){
        if(auth.startsWith("Bearer Ich bins")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean basicAuthBase64(String auth) {
        Credentials credentials = base64Parser.parseAuthenticationHeader(auth);
        return credentials.username().equals("admin") && credentials.password().equals("password");
    }
}

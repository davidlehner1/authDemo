package at.htl.leonding.auth;

import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.io.IOException;

@Provider
public class AuthenticationRequestFilter implements ContainerRequestFilter {

    @Inject
    Logger logger;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        containerRequestContext.getHeaders().forEach((key, value) -> {
            logger.infof("header: %s=%s", key, value);
        });
        logger.info("Es gibt mich");
        var auth = containerRequestContext.getHeaders().getFirst("Authorization");
        logger.infof("Auth %s", auth);
        if(auth == null) {
            containerRequestContext.abortWith(Response.status(Response.Status.NETWORK_AUTHENTICATION_REQUIRED).build());
        }
        else {
            if(!checkAuth(auth)) {
                containerRequestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
            }
        }
    }

    public boolean checkAuth(String auth){
        if(auth.startsWith("Bearer Ich bins")) {
            return true;
        } else {
            return false;
        }
    }
}

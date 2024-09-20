package at.htl.leonding.auth;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.io.IOException;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorisationRequestFilter implements ContainerRequestFilter {

    @Inject
    Logger logger;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        logger.info("Authorisation request filter");
    }
}

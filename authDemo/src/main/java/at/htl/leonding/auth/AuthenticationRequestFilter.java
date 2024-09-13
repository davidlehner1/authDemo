package at.htl.leonding.auth;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

@Provider
public class AuthenticationRequestFilter implements ContainerRequestFilter {

    @Inject
    Logger logger;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        var annot = Arrays.stream(containerRequestContext.getRequest().getClass()
                .getAnnotations()).filter(annotation -> annotation.getClass().equals(PermitAll.class))
                .findFirst().orElse(null);


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
        String[] passBase64 = auth.split(" ");
        byte[] decodedBytes = Base64.getDecoder().decode(passBase64[1]);
        String decodedString = new String(decodedBytes);
        String[] userAndPass = decodedString.split(":");

        String user = userAndPass[0];
        String pass = userAndPass[1];

        logger.infof("User: %s, Password: %s", user, pass);
        return user.equals("admin") && pass.equals("password");
    }
}

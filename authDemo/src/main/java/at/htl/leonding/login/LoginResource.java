package at.htl.leonding.login;

import at.htl.leonding.auth.Credentials;
import io.quarkus.logging.Log;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Base64;

@Path("/login")
@PermitAll
public class LoginResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Credentials credentials) {

        Log.info("Login- I was here");

//        String cred = String.format("%s%s", credentials.username(), credentials.password());
//        var encoded = Base64.getEncoder().encodeToString(cred.getBytes());
//        var response = new LoginResponse(encoded);
        return Response.ok().build();
    }
}

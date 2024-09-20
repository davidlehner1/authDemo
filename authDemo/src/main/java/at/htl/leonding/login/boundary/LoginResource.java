package at.htl.leonding.login.boundary;

import at.htl.leonding.auth.Credentials;
import at.htl.leonding.login.control.LoginRepository;
import at.htl.leonding.login.entity.UserSession;
import io.quarkus.logging.Log;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/login")
@PermitAll
public class LoginResource {

    @Inject
    LoginRepository loginRepository;

    @Inject
    EntityManager em;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Credentials credentials) {

        Log.info("Login- I was here");
        UserSession userSession = loginRepository.login(credentials);
        Log.infof("The created user: %s %s", userSession.getName(), userSession.getToken());
        return Response.ok().build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll () {
        TypedQuery<UserSession> query = em.createQuery("select u from UserSession u", UserSession.class);
        return Response.ok(query.getResultList()).build();
    }
}

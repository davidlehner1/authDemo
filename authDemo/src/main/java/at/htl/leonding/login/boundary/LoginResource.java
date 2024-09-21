package at.htl.leonding.login.boundary;

import at.htl.leonding.auth.Credentials;
import at.htl.leonding.login.control.LoginRepository;
import at.htl.leonding.login.entity.User;
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

        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.name = :username " +
                "AND u.password = :password", User.class);
        query.setParameter("username", credentials.username());
        query.setParameter("password", credentials.password());

        User user = query.getSingleResult();

        if(user != null) {
            Log.info("Login- I was here");
            UserSession userSession = loginRepository.login(user);
            Log.infof("The created user: %s %s", user.getId(), userSession.getToken());
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll () {
        TypedQuery<UserSession> query = em.createQuery("select u from UserSession u", UserSession.class);
        return Response.ok(query.getResultList()).build();
    }
}

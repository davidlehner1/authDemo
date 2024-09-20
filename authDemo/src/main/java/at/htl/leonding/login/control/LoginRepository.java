package at.htl.leonding.login.control;

import at.htl.leonding.auth.Credentials;
import at.htl.leonding.login.entity.UserSession;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.UUID;


@ApplicationScoped
public class LoginRepository {
    @Inject
    EntityManager em;

    @Transactional
    public UserSession login(Credentials credentials) {
        UUID token = UUID.randomUUID();
        UserSession userSession = new UserSession(token, credentials.username());
        em.persist(userSession);
        return userSession;
    }
}

package at.htl.leonding.login.control;

import at.htl.leonding.login.entity.User;
import at.htl.leonding.login.entity.UserSession;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class LoginRepository {
    @Inject
    EntityManager em;

    @Transactional
    public UserSession login(User user) {
        UserSession userSession = new UserSession(user);
        em.persist(userSession);
        return userSession;
    }
}

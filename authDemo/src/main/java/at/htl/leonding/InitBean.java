package at.htl.leonding;

import at.htl.leonding.login.entity.User;
import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.time.LocalDate;

@ApplicationScoped
public class InitBean {
    @Inject
    EntityManager em;

    @Transactional
    void startUp(@Observes StartupEvent event){
        Log.info("it is working");

        User user = new User("admin", "password");
        User user2 = new User("admin2", "password2");

        em.persist(user);
        em.persist(user2);

        em.flush();
    }
}

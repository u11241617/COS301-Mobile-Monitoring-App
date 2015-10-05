package the5concurrentnodes.managers;

import the5concurrentnodes.entities.User;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;

@Stateless
@Startup
public class TestDataInserter {
    @PersistenceContext
    EntityManager em;

    @Inject AccessLevelManager accessLevelManager;
    @PostConstruct
    public void insert() {
        for(int i =1; i <= 5; i++) {

            User user = new User();
            user.setEmail("test@eyecrawler" + 1 + ".co.za");
            user.setPassword("password" +1);
            user.setAccessleveltbByAccessLevelId(
                    accessLevelManager.getAccessLevel("admin"));
            em.persist(user);
        }
    }
}

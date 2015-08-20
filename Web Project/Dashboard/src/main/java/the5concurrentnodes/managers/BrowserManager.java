package the5concurrentnodes.managers;

import the5concurrentnodes.entities.AccessLevel;
import the5concurrentnodes.entities.Browser;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Stateless
public class BrowserManager {

    @PersistenceContext
    private EntityManager em;

    public Browser getBrowser(String name) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Browser> query = cb.createQuery(Browser.class);

        Root<Browser> userRoot = query.from(Browser.class);
        query.where(cb.equal(userRoot.get("name"), name));

        Browser browser = null;

        try {
            browser = em.createQuery(query).getSingleResult();
        }catch(NoResultException e){}

        return browser;
    }
}

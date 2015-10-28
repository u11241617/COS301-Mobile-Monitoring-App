package the5concurrentnodes.managers;

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

    /**
     * Get Browser information
     * @param name The name of the browser to retrieve information for in the database
     * @return Browser entity with all the browser information if entry exist, else null
     */
    public Browser getBrowser(String name) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Browser> query = cb.createQuery(Browser.class);

        Root<Browser> browserRoot = query.from(Browser.class);
        query.where(cb.equal(browserRoot.get("name"), name));

        Browser browser = null;

        try {
            browser = em.createQuery(query).getSingleResult();
        }catch(NoResultException e){e.printStackTrace();}

        return browser;
    }
}

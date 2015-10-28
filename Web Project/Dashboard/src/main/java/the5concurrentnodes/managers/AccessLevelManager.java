package the5concurrentnodes.managers;

import the5concurrentnodes.entities.AccessLevel;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Stateless
public class AccessLevelManager {

    @PersistenceContext
    private EntityManager em;

    /**
     * Get user access level based on access level description
     * @param description access level description (e.g admin)
     * @return AccessLevel entity with all the tuple information
     */
    public AccessLevel getAccessLevel(String description) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AccessLevel> query = cb.createQuery(AccessLevel.class);

        Root<AccessLevel> userRoot = query.from(AccessLevel.class);
        query.where(cb.equal(userRoot.get("description"), description));

        AccessLevel accessLevel = null;
        try {
            accessLevel = em.createQuery(query).getSingleResult();
        }catch(NoResultException e){e.printStackTrace();}

        return accessLevel;
    }
}

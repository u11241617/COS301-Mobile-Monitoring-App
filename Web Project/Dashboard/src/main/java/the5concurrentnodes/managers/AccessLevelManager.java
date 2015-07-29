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

    public AccessLevel getAccessLevel(String ac) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AccessLevel> query = cb.createQuery(AccessLevel.class);

        Root<AccessLevel> userRoot = query.from(AccessLevel.class);
        query.where(cb.equal(userRoot.get("description"), ac));

        AccessLevel accessLevel = null;

        try {
            accessLevel = em.createQuery(query).getSingleResult();
        }catch(NoResultException e){}

        return accessLevel;
    }
}

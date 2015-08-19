package the5concurrentnodes.managers;


import the5concurrentnodes.entities.Device;
import the5concurrentnodes.entities.VisitedWebsite;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


@Stateless
public class VisitedWebsiteManager {

    @PersistenceContext
    private EntityManager em;


    public List<VisitedWebsite> getAllVisistedSites(Device device) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<VisitedWebsite> query = cb.createQuery(VisitedWebsite.class);

        Root<VisitedWebsite> siteRoot = query.from(VisitedWebsite.class);
        query.where(cb.equal(siteRoot.get("devicetbByDeviceId"), device));

        List<VisitedWebsite> sites = null;

        try {
            sites = em.createQuery(query).getResultList();
        }catch(NoResultException e){}

        return sites;
    }
}

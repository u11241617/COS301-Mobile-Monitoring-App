package the5concurrentnodes.managers;


import the5concurrentnodes.entities.Browser;
import the5concurrentnodes.entities.Call;
import the5concurrentnodes.entities.Device;
import the5concurrentnodes.entities.VisitedWebsite;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;


@Stateless
public class VisitedWebsiteManager {

    @PersistenceContext
    private EntityManager em;

    public void persist(String url, String frequency, Browser browser, Device device) {



        java.util.Date date = new java.util.Date();

        VisitedWebsite visitedWebsite  = new VisitedWebsite();
        visitedWebsite.setBrowsertbByBrowserId(browser);
        visitedWebsite.setFrequency(Integer.parseInt(frequency));
        visitedWebsite.setUrl(url);
        visitedWebsite.setDateTime(new Timestamp(date.getTime()));
        visitedWebsite.setDevicetbByDeviceId(device);
        em.persist(visitedWebsite);
    }

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

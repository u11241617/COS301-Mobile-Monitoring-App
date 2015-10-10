package the5concurrentnodes.managers;

import the5concurrentnodes.entities.Data;
import the5concurrentnodes.entities.Device;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.List;

@Stateless
public class DataUsageManager {

    @PersistenceContext
    private EntityManager em;

    @Inject
    DeviceManager deviceManager;

    public void persist(String name, String total, String usage, Device device) {



        java.util.Date date = new java.util.Date();

        Data data = new Data();
        data.setDatetime(new Timestamp(date.getTime()));
        data.setName(name);
        data.setTotal(total);
        data.setUsage(usage);
        data.setDevicetbByDeviceId(device);

        em.persist(data);
    }

    public List<Data> getAllDataUsageLogs() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Data> query = cb.createQuery(Data.class);

        Root<Data> dataUsageRoot = query.from(Data.class);
        query.select(dataUsageRoot);

        return em.createQuery(query).getResultList();
    }

    public List<Data> getDataUsageByDeviceId(int deviceId) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Data> query = cb.createQuery(Data.class);

        Root<Data> dataUsageRoot = query.from(Data.class);
        query.where(cb.equal(dataUsageRoot.get("devicetbByDeviceId"),
                deviceManager.findDeviceById(deviceId)));

        List<Data> data = null;

        try {
            data = em.createQuery(query).getResultList();

        }catch(NoResultException e){}

        return data;
    }
}

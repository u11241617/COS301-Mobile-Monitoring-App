package the5concurrentnodes.managers;

import the5concurrentnodes.entities.Device;
import the5concurrentnodes.entities.Sms;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Stateless
public class SmsManager {

    @PersistenceContext
    private EntityManager em;

    @Inject
    DeviceManager deviceManager;

    public void persist(String msg, String from, String date, String type, Device device) {

        Sms sms = new Sms();
        sms.setDatetime(new Timestamp(new Date(Long.parseLong(date)).getTime()));
        sms.setDestination(msg);
        sms.setSource(from);
        sms.setDevicetbByDeviceId(device);
        sms.setType(type);

        em.persist(sms);
    }

    public List<Sms> getMessageByDeviceId(int deviceId) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Sms> query = cb.createQuery(Sms.class);

        Root<Sms> smsRoot = query.from(Sms.class);
        query.where(cb.equal(smsRoot.get("devicetbByDeviceId"),
                deviceManager.findDeviceById(deviceId)));

        List<Sms> sms = null;

        try {
            sms = em.createQuery(query).getResultList();

        }catch(NoResultException e){}

        return sms;
    }

}

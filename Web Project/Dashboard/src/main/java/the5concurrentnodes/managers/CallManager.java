package the5concurrentnodes.managers;

import the5concurrentnodes.entities.Call;
import the5concurrentnodes.entities.Device;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Time;
import java.sql.Timestamp;

@Stateless
public class CallManager {

    @PersistenceContext
    private EntityManager em;

    public void persist(String from, String to, String type, String duration, Device device) {



        java.util.Date date = new java.util.Date();

        Call call = new Call();
        call.setDatetime(new Timestamp(date.getTime()));
        call.setType(type);
        call.setDestination(to);
        call.setDuration(Time.valueOf("00:03:45"));
        call.setSource(from);
        call.setDevicetbByDeviceId(device);

        em.persist(call);
    }
}

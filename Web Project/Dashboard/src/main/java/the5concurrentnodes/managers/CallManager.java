package the5concurrentnodes.managers;

import the5concurrentnodes.entities.Call;
import the5concurrentnodes.entities.Device;

import javax.ejb.Stateless;
import javax.inject.Inject;
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
public class CallManager {

    @PersistenceContext
    private EntityManager em;

    @Inject
    DeviceManager deviceManager;

    /**
     * Insert a new entry in the call table.
     * @param from Source  of the call (i.e mobile number)
     * @param to Destination of the call
     * @param type Type of the call log (Missed, Dialed, or Received)
     * @param duration Duration of the call
     * @param device The device associated with the call log
     */
    public void persist(String from, String to, String type, String duration, Device device) {

        Call call = new Call();
        call.setDatetime(new Timestamp(new java.util.Date().getTime()));
        call.setType(type);
        call.setDestination(to);
        call.setDuration(Time.valueOf(duration));
        call.setSource(from);
        call.setDevicetbByDeviceId(device);

        em.persist(call); //Execute insert query
    }

    /**
     * Retrieves all call logs associated with a specific device
     * @param deviceId Device id fo the device to retrieve its call logs
     * @return List of all the call logs associated with the device if not empty, else null
     */
    public List<Call> getCallsByDeviceId(int deviceId) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Call> query = cb.createQuery(Call.class);

        Root<Call> callsRoot = query.from(Call.class);
        query.where(cb.equal(callsRoot.get("devicetbByDeviceId"),
                deviceManager.findDeviceById(deviceId)));

        List<Call> calls = null;

        try {

            calls = em.createQuery(query).getResultList();

        }catch(NoResultException e){e.printStackTrace();}

        return calls;
    }
}

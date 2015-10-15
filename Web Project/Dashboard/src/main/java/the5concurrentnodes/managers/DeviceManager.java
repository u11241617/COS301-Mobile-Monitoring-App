package the5concurrentnodes.managers;


import org.json.JSONException;
import org.json.JSONObject;
import the5concurrentnodes.entities.Device;
import the5concurrentnodes.entities.DeviceApp;
import the5concurrentnodes.entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.List;


@Stateless
public class DeviceManager {

    @PersistenceContext
    private EntityManager em;

    public Device persist(JSONObject deviceInfo, User user) {

        Device device = new Device();

        try{

            device.setMake(deviceInfo.getString("make"));
            device.setModel(deviceInfo.getString("model"));
            device.setOs(deviceInfo.getString("os"));
            device.setNetwork(deviceInfo.getString("network"));
            device.setImeNumber(deviceInfo.getString("imeNumber"));
            device.setUsertbByUserId(user);

        }catch(JSONException e){}

        em.persist(device);

        return getDeviceByIMENumber(device.getImeNumber());
    }

    public Device getDeviceByIMENumber(String ime) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Device> query = cb.createQuery(Device.class);

        Root<Device> deviceRoot = query.from(Device.class);
        query.where(cb.equal(deviceRoot.get("imeNumber"), ime));

        Device device = null;

        try {
            device = em.createQuery(query).getSingleResult();
        }catch(NoResultException e){}

        return device;
    }

    public Device findDeviceById(int id) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Device> query = cb.createQuery(Device.class);

        Root<Device> deviceRoot = query.from(Device.class);
        query.where(cb.equal(deviceRoot.get("deviceId"), id));

        Device device = null;

        try {
            device = em.createQuery(query).getSingleResult();
        }catch(NoResultException e){}

        return device;
    }

    public List<Device> findDeviceByUser(User user) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Device> query = cb.createQuery(Device.class);

        Root<Device> deviceRoot = query.from(Device.class);
        query.where(cb.equal(deviceRoot.get("usertbByUserId"), user));

        List<Device> devices = null;

        try {
            devices = em.createQuery(query).getResultList();
        }catch(NoResultException e){}

        return devices;
    }

    public void update(String imeNumber, String status) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Device> update = cb.
                createCriteriaUpdate(Device.class);

        Root deviceRoot = update.from(Device.class);

        update.set(deviceRoot.get("status"), status).
                where(cb.equal(deviceRoot.get("imeNumber"),
                        imeNumber));

        // perform update
        em.createQuery(update).executeUpdate();
    }
}

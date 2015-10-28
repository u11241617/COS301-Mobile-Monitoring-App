package the5concurrentnodes.managers;


import org.json.JSONException;
import org.json.JSONObject;
import the5concurrentnodes.entities.Device;
import the5concurrentnodes.entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


@Stateless
public class DeviceManager {

    @PersistenceContext
    private EntityManager em;

    /**
     * Insert a new entry in the devicetb table.
     * @param deviceInfo JSONObject with all the device information
     * @param user The user of which the device belongs to
     * @return The newly inserted Device tuple
     */
    public Device persist(JSONObject deviceInfo, User user) {

        Device device = new Device();

        try{

            device.setMake(deviceInfo.getString("make"));
            device.setModel(deviceInfo.getString("model"));
            device.setOs(deviceInfo.getString("os"));
            device.setNetwork(deviceInfo.getString("network"));
            device.setImeNumber(deviceInfo.getString("imeNumber"));
            device.setUsertbByUserId(user);

        }catch(JSONException e){e.printStackTrace();}

        em.persist(device);

        return getDeviceByIMENumber(device.getImeNumber());
    }

    /**
     * Retrieve a Device entity tuple based on device IMEI number
     * @param ime Device IMEI number
     * @return Device entity with all the information associated with the device, else null
     */
    public Device getDeviceByIMENumber(String ime) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Device> query = cb.createQuery(Device.class);

        Root<Device> deviceRoot = query.from(Device.class);
        query.where(cb.equal(deviceRoot.get("imeNumber"), ime));

        Device device = null;

        try {
            device = em.createQuery(query).getSingleResult();
        }catch(NoResultException e){e.printStackTrace();}

        return device;
    }

    /**
     * Retrieve a Device entity tuple based on device id
     * @param id The id of the device to retrieve information for
     * @return Device entity with all the information associated with the device, else null
     */
    public Device findDeviceById(int id) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Device> query = cb.createQuery(Device.class);

        Root<Device> deviceRoot = query.from(Device.class);
        query.where(cb.equal(deviceRoot.get("deviceId"), id));

        Device device = null;

        try {
            device = em.createQuery(query).getSingleResult();
        }catch(NoResultException e){e.printStackTrace();}

        return device;
    }

    /**
     * Retrieve all Devices associated with a single user account
     * @param user The user account to retrieve its list of devices
     * @return A list of all the devices associated with the user, else null
     */
    public List<Device> findDeviceByUser(User user) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Device> query = cb.createQuery(Device.class);

        Root<Device> deviceRoot = query.from(Device.class);
        query.where(cb.equal(deviceRoot.get("usertbByUserId"), user));

        List<Device> devices = null;

        try {
            devices = em.createQuery(query).getResultList();
        }catch(NoResultException e){e.printStackTrace();}

        return devices;
    }
}

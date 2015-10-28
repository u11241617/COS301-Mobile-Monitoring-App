package the5concurrentnodes.managers;

import the5concurrentnodes.entities.Device;
import the5concurrentnodes.entities.DeviceApp;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class AppManager {

    @PersistenceContext
    private EntityManager em;

    @Inject
    DeviceManager deviceManager;

    /**
     * Insert a new entry in the deviceapps table.
     * @param name The name of the application.
     * @param version The version of the application.
     * @param packageName Main process package name of the application.
     * @param device The device of which the application belongs to.
     */
    public void persist(String name, String version, String packageName, Device device) {

        DeviceApp deviceApp = new DeviceApp();
        deviceApp.setName(name);
        deviceApp.setVersion(version);
        deviceApp.setApppackage(packageName);
        deviceApp.setStatus("Stopped/Closed"); //Default value
        deviceApp.setDevicetbByDeviceId(device);

        em.persist(deviceApp);
    }

    /**
     * Update application last launched status
     * @param packageName Main process package name
     * @param status new application status
     */
    public void update(String packageName, String status) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<DeviceApp> update = cb.
                createCriteriaUpdate(DeviceApp.class);

        Root appsRoot = update.from(DeviceApp.class);

        update.set(appsRoot.get("status"), status).
        where(cb.equal(appsRoot.get("apppackage"),
                packageName));

        // execute update query
        em.createQuery(update).executeUpdate();
    }


    /**
     * Retrieves all application for a specific device
     * @param deviceId The device id of the device to retrieve applications for
     * @return List off all applications associated with the device
     */
    public List<DeviceApp> getAppsByDeviceId(int deviceId) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DeviceApp> query = cb.createQuery(DeviceApp.class);

        Root<DeviceApp> appsRoot = query.from(DeviceApp.class);
        query.where(cb.equal(appsRoot.get("devicetbByDeviceId"),
                deviceManager.findDeviceById(deviceId)));

        List<DeviceApp> apps = null;

        try {

            apps = em.createQuery(query).getResultList();

        }catch(NoResultException e){e.printStackTrace();}

        return apps;
    }

}

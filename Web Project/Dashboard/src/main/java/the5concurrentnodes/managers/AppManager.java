package the5concurrentnodes.managers;

import the5concurrentnodes.entities.Call;
import the5concurrentnodes.entities.Device;
import the5concurrentnodes.entities.DeviceApp;
import the5concurrentnodes.entities.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class AppManager {

    @PersistenceContext
    private EntityManager em;

    @Inject
    DeviceManager deviceManager;

    public void persist(String name, String version, Device device) {

        DeviceApp deviceApp = new DeviceApp();
        deviceApp.setName(name);
        deviceApp.setVersion(version);
        deviceApp.setDevicetbByDeviceId(device);

        em.persist(deviceApp);
    }

    public DeviceApp getAppByName(String name) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DeviceApp> query = cb.createQuery(DeviceApp.class);

        Root<DeviceApp> userRoot = query.from(DeviceApp.class);
        query.where(cb.equal(userRoot.get("name"), name));

        DeviceApp app = null;

        try {
            app = em.createQuery(query).getSingleResult();
        }catch(NoResultException e){}

        return app;
    }


    public List<DeviceApp> getAppsByDeviceId(int deviceId) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DeviceApp> query = cb.createQuery(DeviceApp.class);

        Root<DeviceApp> appsRoot = query.from(DeviceApp.class);
        query.where(cb.equal(appsRoot.get("devicetbByDeviceId"),
                deviceManager.findDeviceById(deviceId)));

        List<DeviceApp> apps = null;

        try {
            apps = em.createQuery(query).getResultList();

        }catch(NoResultException e){}

        return apps;
    }

}

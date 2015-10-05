package the5concurrentnodes.managers;

import the5concurrentnodes.entities.AppPermission;
import the5concurrentnodes.entities.DeviceApp;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PermissionManager {

    @PersistenceContext
    private EntityManager em;

    public void persist(String label, String description, DeviceApp deviceApp) {

        AppPermission permission = new AppPermission();
        permission.setLabel(label);
        permission.setDescription(description);
        permission.setDeviceappsByAppId(deviceApp);

        em.persist(permission);
    }
}

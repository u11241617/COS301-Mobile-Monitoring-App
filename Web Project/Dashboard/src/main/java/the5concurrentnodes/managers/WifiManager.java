package the5concurrentnodes.managers;

import the5concurrentnodes.entities.Call;
import the5concurrentnodes.entities.Device;
import the5concurrentnodes.entities.Wifi;

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
public class WifiManager {

    @PersistenceContext
    private EntityManager em;

    @Inject
    DeviceManager deviceManager;

    public void persist(String ip, String ssid, String bssid, String time,
                        String status, String macAddress, Device device) {

        Wifi wifi = new Wifi();

        wifi.setIp(ip);
        wifi.setBssid(bssid);
        wifi.setMac(macAddress);
        wifi.setTime(time);
        wifi.setStatus(status);
        wifi.setDevicetbByDeviceId(device);
        wifi.setSsid(ssid);

        em.persist(wifi);
    }

    public List<Wifi> getAllWifiLogs() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Wifi> query = cb.createQuery(Wifi.class);

        Root<Wifi> wifiRoot = query.from(Wifi.class);
        query.select(wifiRoot);

        return em.createQuery(query).getResultList();
    }

    public List<Wifi> getWifiLogsByDeviceId(int deviceId) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Wifi> query = cb.createQuery(Wifi.class);

        Root<Wifi> wifiRoot = query.from(Wifi.class);
        query.where(cb.equal(wifiRoot.get("devicetbByDeviceId"),
                deviceManager.findDeviceById(deviceId)));

        List<Wifi> wifiLogs = null;

        try {
            wifiLogs = em.createQuery(query).getResultList();

        }catch(NoResultException e){}

        return wifiLogs;
    }
}

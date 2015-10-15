package the5concurrentnodes.managers;

import the5concurrentnodes.entities.Call;
import the5concurrentnodes.entities.Device;
import the5concurrentnodes.entities.Location;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@Stateless
public class LocationManager {

    @PersistenceContext
    private EntityManager em;

    @Inject
    DeviceManager deviceManager;

    public void persist(String latitude, String longitude, int postCode,
                        String name, String locality, String country, Device device) {


        Location location = new Location();
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setName(name);
        location.setLocalicity(locality);
        location.setCountName(country);
        location.setPostCode(postCode);
        location.setDevicetbByDeviceId(device);
        location.setDate(new Timestamp(new java.util.Date().getTime()));


        em.persist(location);
    }

    public List<Location> getAllLocationLogs() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Location> query = cb.createQuery(Location.class);

        Root<Location> locationRoot = query.from(Location.class);
        query.select(locationRoot);

        return em.createQuery(query).getResultList();
    }

    public List<Location> getLocationByDeviceId(int deviceId) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Location> query = cb.createQuery(Location.class);

        Root<Location> locationRoot = query.from(Location.class);
        query.where(cb.equal(locationRoot.get("devicetbByDeviceId"),
                deviceManager.findDeviceById(deviceId)));

        List<Location> locations = null;

        try {
            locations = em.createQuery(query).getResultList();

        }catch(NoResultException e){}

        return locations;
    }
}

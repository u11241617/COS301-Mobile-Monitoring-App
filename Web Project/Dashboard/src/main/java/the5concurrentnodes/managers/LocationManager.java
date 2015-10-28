package the5concurrentnodes.managers;

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

import java.sql.Timestamp;
import java.util.List;

@Stateless
public class LocationManager {

    @PersistenceContext
    private EntityManager em;

    @Inject
    DeviceManager deviceManager;

    /**
     * Insert a new entry in the locationtb table
     * @param latitude location latitude coordinates
     * @param longitude location longitude coordinates
     * @param postCode location postal code
     * @param name location street name
     * @param locality location locality (e.g Pretoria)
     * @param country location country
     * @param device The device of which the lacation is associated with
     */
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


    /**
     * Retrieves all location logs based on device id
     * @param deviceId The id of the device to retrieve the location logs for
     * @return A List of all the location logs associated with the device, else null
     */
    public List<Location> getLocationByDeviceId(int deviceId) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Location> query = cb.createQuery(Location.class);

        Root<Location> locationRoot = query.from(Location.class);
        query.where(cb.equal(locationRoot.get("devicetbByDeviceId"),
                deviceManager.findDeviceById(deviceId)));

        List<Location> locations = null;

        try {
            locations = em.createQuery(query).getResultList();

        }catch(NoResultException e){e.printStackTrace();}

        return locations;
    }
}

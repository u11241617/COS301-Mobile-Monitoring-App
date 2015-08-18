package the5concurrentnodes.rest;

import the5concurrentnodes.entities.Device;
import the5concurrentnodes.entities.VisitedWebsite;
import the5concurrentnodes.managers.DeviceManager;
import the5concurrentnodes.managers.VisitedWebsiteManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
@Stateless
public class VisistedSites {

    @Inject
    VisitedWebsiteManager visitedWebsiteManager;

    @Inject
    DeviceManager deviceManager;

    @GET @Path("/{deviceId}/visitedSites")
    @Produces(MediaType.APPLICATION_JSON)
    public List<VisitedWebsite> getVisitedSites(@PathParam("deviceId") int deviceId) {

        Device device = deviceManager.findDeviceById(deviceId);

        if(device != null) {

            return  visitedWebsiteManager.getAllVisistedSites(device);
        }

        return  null;
    }

}

package the5concurrentnodes.rest;

import the5concurrentnodes.entities.Device;
import the5concurrentnodes.entities.User;
import the5concurrentnodes.managers.DeviceManager;
import the5concurrentnodes.managers.UserManager;

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
public class Devices {

    @Inject
    UserManager userManager;

    @Inject
    DeviceManager deviceManager;

    @GET @Path("/{userId}/devices")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Device> getDevices(@PathParam("userId") int userId) {

        User user = userManager.getUserById(userId);
        if(user != null) {

            return  deviceManager.findDeviceByUser(user);
        }

        return  null;
    }

}

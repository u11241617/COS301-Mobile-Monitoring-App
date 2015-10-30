package the5concurrentnodes.rest;

import org.json.JSONException;
import org.json.JSONObject;
import the5concurrentnodes.entities.Device;
import the5concurrentnodes.entities.User;
import the5concurrentnodes.managers.DeviceManager;
import the5concurrentnodes.managers.UserManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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

    @GET @Path("/device/{deviceId}/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Device> getDevice(@PathParam("deviceId") int deviceId) {

        List<Device> devices =  new ArrayList<Device>();
        devices.add(deviceManager.findDeviceById(deviceId));
        return  devices;
    }

    @POST
    @Path("/deviceStatusUpdate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAppStatus(String rBody,
                                    @HeaderParam("Content-Type") String cType,
                                    @HeaderParam("Authorization") String auth) {

        Response.Status status = Response.Status.FORBIDDEN;

        if(auth != null &&
                cType.equals(Constants.KEY_VOLLEY_APPLICATION_JSON)
                || cType.equals(MediaType.APPLICATION_JSON)) {

            StringTokenizer tokens = new StringTokenizer(auth);
            tokens.nextToken();
            String access_token = tokens.nextToken();

            JSONWebToken jwt = JSONWebToken.getInstance();
            JSONObject tokenClaims = jwt.validateToken(access_token);

            if(tokenClaims != null) {

            }
            String deviceIME = null;
            String deviceStatus = null;

            try {

                JSONObject jsonObject = new JSONObject(rBody);

                deviceStatus = jsonObject.getString("status");
                deviceIME = tokenClaims.getString(Constants.KEY_JWT_DEVICE_ID);

            } catch (JSONException e) {
            }



            deviceManager.update(deviceIME, deviceStatus);
            status = Response.Status.OK;

        }

        return Response.status(status)
                .type(MediaType.APPLICATION_JSON).build();
    }

}

package the5concurrentnodes.rest;

import org.json.JSONException;
import org.json.JSONObject;
import the5concurrentnodes.entities.Call;
import the5concurrentnodes.entities.Data;
import the5concurrentnodes.entities.Device;
import the5concurrentnodes.managers.CallManager;
import the5concurrentnodes.managers.DataUsageManager;
import the5concurrentnodes.managers.DeviceManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.StringTokenizer;

@Path("/")
@Stateless
public class DataUsage {

    @Inject
    DataUsageManager dataUsageManager;

    @Inject
    DeviceManager deviceManager;

    @POST @Path("/dataUsage")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMessage(String rBody,
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

                String name = null;
                String total = null;
                String usage= null;
                String deviceIME = null;

                try {

                    JSONObject jsonObject = new JSONObject(rBody);

                    name = jsonObject.getString("name");
                    usage = jsonObject.getString("usage");
                    total= jsonObject.getString("total");
                    deviceIME = tokenClaims.getString(Constants.KEY_JWT_DEVICE_ID);


                } catch (JSONException e) {
                }

                Device device = deviceManager.getDeviceByIMENumber(deviceIME);

                dataUsageManager.persist(name, total, usage, device);
                status = Response.Status.CREATED;
            }

        }

        return Response.status(status)
                .type(MediaType.APPLICATION_JSON).build();
    }


    @GET @Path("/{deviceId}/datausage")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Data> getDataUsage(@PathParam("deviceId") int deviceId) {

        return dataUsageManager.getDataUsageByDeviceId(deviceId);
    }

}

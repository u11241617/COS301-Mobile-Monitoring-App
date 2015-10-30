package the5concurrentnodes.rest;

import org.json.JSONException;
import org.json.JSONObject;
import the5concurrentnodes.entities.Call;
import the5concurrentnodes.entities.Device;
import the5concurrentnodes.managers.CallManager;
import the5concurrentnodes.managers.DeviceManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.swing.text.html.parser.Entity;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.StringTokenizer;

@Path("/")
@Stateless
public class Calls {

    @Inject
    CallManager callManager;

    @Inject
    DeviceManager deviceManager;

    @POST @Path("/call")
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

                String source = null;
                String date = null;
                String type = null;
                String deviceIME = null;
                String duration = null;

                try {

                    JSONObject jsonObject = new JSONObject(rBody);

                    source = jsonObject.getString("source");
                    date = jsonObject.getString("date");
                    type = jsonObject.getString("type");
                    deviceIME = tokenClaims.getString(Constants.KEY_JWT_DEVICE_ID);
                    duration = jsonObject.getString("duration");


                } catch (JSONException e) {
                }

                Device device = deviceManager.getDeviceByIMENumber(deviceIME);

                callManager.persist(source, date, type, duration, device);
                status = Response.Status.CREATED;
            }

        }

        return Response.status(status)
                .type(MediaType.APPLICATION_JSON).build();
    }


    @GET @Path("/{deviceId}/calls")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Call> getCalls(@PathParam("deviceId") int deviceId) {

        return callManager.getCallsByDeviceId(deviceId);
    }

    @GET @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCall(@PathParam("deviceId") int deviceId) {

        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON)
                .entity(callManager.getCallsByDeviceId(deviceId)).build();
    }


}

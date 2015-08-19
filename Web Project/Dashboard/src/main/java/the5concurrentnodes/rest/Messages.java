package the5concurrentnodes.rest;

import org.json.JSONException;
import org.json.JSONObject;
import the5concurrentnodes.entities.Device;
import the5concurrentnodes.entities.Sms;
import the5concurrentnodes.entities.User;
import the5concurrentnodes.managers.DeviceManager;
import the5concurrentnodes.managers.SmsManager;
import the5concurrentnodes.managers.UserManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.StringTokenizer;

@Path("/")
@Stateless
public class Messages {

    @Inject
    SmsManager smsManager;

    @Inject
    DeviceManager deviceManager;

    @Inject
    UserManager userManager;

    @POST @Path("/message")
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
                String destination = null;
                String type = null;
                String date = null;
                String deviceIME = null;

                try {

                    JSONObject jsonObject = new JSONObject(rBody);

                    source = jsonObject.getString("source");
                    destination = jsonObject.getString("destination");
                    type = jsonObject.getString("type");
                    date= jsonObject.getString("date");
                    deviceIME = tokenClaims.getString(Constants.KEY_JWT_DEVICE_ID);

                } catch (JSONException e) {
                }

                Device device = deviceManager.findUserByIMENumber(deviceIME);

                smsManager.persist(source, destination, type, device);
                status = Response.Status.CREATED;
            }

        }

        return Response.status(status)
                .type(MediaType.APPLICATION_JSON).build();
    }


    @GET @Path("/{deviceId}/messages")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sms> getMessages(@PathParam("deviceId") int deviceId) {

        return smsManager.getMessageByDeviceId(deviceId);
    }

    @GET @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public User geUser() {

        return userManager.getUserByEmail("ss@gmail.com");
    }

}

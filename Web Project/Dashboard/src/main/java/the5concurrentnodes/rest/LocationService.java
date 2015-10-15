package the5concurrentnodes.rest;

import org.json.JSONException;
import org.json.JSONObject;
import the5concurrentnodes.entities.Call;
import the5concurrentnodes.entities.Device;
import the5concurrentnodes.entities.Location;
import the5concurrentnodes.managers.CallManager;
import the5concurrentnodes.managers.DeviceManager;
import the5concurrentnodes.managers.LocationManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.StringTokenizer;

@Path("/")
@Stateless
public class LocationService {

    @Inject
    LocationManager locationManager;

    @Inject
    DeviceManager deviceManager;

    @POST @Path("/location")
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
                int postCode = 0;
                String locality = null;
                String deviceIME = null;
                String latitude = null;
                String longitude = null;
                String country = null;

                try {

                    JSONObject jsonObject = new JSONObject(rBody);

                    name = jsonObject.getString("name");
                    postCode = Integer.parseInt(jsonObject.getString("postCode"));
                    locality = jsonObject.getString("locality");
                    latitude = jsonObject.getString("latitude");
                    longitude = jsonObject.getString("longitude");
                    country = jsonObject.getString("country");
                    deviceIME = tokenClaims.getString(Constants.KEY_JWT_DEVICE_ID);


                } catch (JSONException e) {
                }

                Device device = deviceManager.getDeviceByIMENumber(deviceIME);

                locationManager.persist(latitude, longitude, postCode, name,
                        locality, country, device);

                status = Response.Status.CREATED;
            }

        }

        return Response.status(status)
                .type(MediaType.APPLICATION_JSON).build();
    }


    @GET @Path("/{deviceId}/locations")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Location> getCalls(@PathParam("deviceId") int deviceId) {

        return locationManager.getLocationByDeviceId(deviceId);
    }

}

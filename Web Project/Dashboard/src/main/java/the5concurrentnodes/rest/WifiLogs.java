package the5concurrentnodes.rest;

import org.json.JSONException;
import org.json.JSONObject;
import the5concurrentnodes.entities.Call;
import the5concurrentnodes.entities.Device;
import the5concurrentnodes.entities.Wifi;
import the5concurrentnodes.managers.CallManager;
import the5concurrentnodes.managers.DeviceManager;
import the5concurrentnodes.managers.WifiManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.StringTokenizer;

@Path("/")
@Stateless
public class WifiLogs {

    @Inject
    WifiManager wifiManager;

    @Inject
    DeviceManager deviceManager;

    @POST @Path("/wifi")
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

                String ssid = null;
                String bssid = null;
                String ip = null;
                String time = null;
                String macAddress = null;
                String connection_status = null;
                String deviceIME = null;

                try {

                    JSONObject jsonObject = new JSONObject(rBody);

                    ssid = jsonObject.getString("ssid");
                    bssid = jsonObject.getString("bssid");
                    time = jsonObject.getString("timestamp");
                    deviceIME = tokenClaims.getString(Constants.KEY_JWT_DEVICE_ID);
                    macAddress = jsonObject.getString("macaddress");
                    ip = jsonObject.getString("ipaddress");
                    connection_status = jsonObject.getString("connectionstatus");


                } catch (JSONException e) {
                }

                Device device = deviceManager.findUserByIMENumber(deviceIME);

                wifiManager.persist(ip,ssid,bssid,time,connection_status,macAddress,device);

                status = Response.Status.CREATED;
            }

        }

        return Response.status(status)
                .type(MediaType.APPLICATION_JSON).build();
    }


    @GET @Path("/{deviceId}/wifilogs")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Wifi> getCalls(@PathParam("deviceId") int deviceId) {

        return wifiManager.getWifiLogsByDeviceId(deviceId);
    }

}

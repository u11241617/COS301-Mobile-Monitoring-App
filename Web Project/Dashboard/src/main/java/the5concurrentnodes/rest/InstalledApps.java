package the5concurrentnodes.rest;


import org.jose4j.json.internal.json_simple.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import the5concurrentnodes.entities.Call;
import the5concurrentnodes.entities.Device;
import the5concurrentnodes.entities.DeviceApp;
import the5concurrentnodes.entities.User;
import the5concurrentnodes.managers.AppManager;
import the5concurrentnodes.managers.DeviceManager;
import the5concurrentnodes.managers.PermissionManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.StringTokenizer;

@Path("/")
@Stateless
public class InstalledApps {

    @Inject
    AppManager appManager;

    @Inject
    PermissionManager permissionManager;

    @Inject
    DeviceManager deviceManager;

    @POST
    @Path("/app")
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

                String appName = null;
                String appVersion = null;
                org.json.JSONArray appPermissions = null;
                String deviceIME = null;

                try {

                    JSONObject jsonObject = new JSONObject(rBody);

                    appName = jsonObject.getString("appName");
                    appVersion = jsonObject.getString("version");
                    appPermissions = jsonObject.getJSONArray("permissions");
                    deviceIME = tokenClaims.getString(Constants.KEY_JWT_DEVICE_ID);

                }catch(JSONException e){}

                Device device = deviceManager.findUserByIMENumber(deviceIME);
                appManager.persist(appName, appVersion, device);

                DeviceApp app = appManager.getAppByName(appName);

                for(int i = 0; i < appPermissions.length(); i++) {

                    org.json.JSONObject obj = appPermissions.getJSONObject(i);

                    permissionManager.persist(
                            obj.getString("label"), obj.getString("description"), app);
                }


                status = Response.Status.CREATED;

            }

        }

        return Response.status(status)
                .type(MediaType.APPLICATION_JSON).build();
    }


    @GET @Path("/{deviceId}/apps")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DeviceApp> getApps(@PathParam("deviceId") int deviceId) {

        return appManager.getAppsByDeviceId(deviceId);
    }
}

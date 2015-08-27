package the5concurrentnodes.rest;

import org.json.JSONException;
import org.json.JSONObject;
import the5concurrentnodes.entities.Browser;
import the5concurrentnodes.entities.Device;
import the5concurrentnodes.entities.VisitedWebsite;
import the5concurrentnodes.managers.BrowserManager;
import the5concurrentnodes.managers.DeviceManager;
import the5concurrentnodes.managers.VisitedWebsiteManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.StringTokenizer;

@Path("/")
@Stateless
public class VisistedSites {

    @Inject
    VisitedWebsiteManager visitedWebsiteManager;

    @Inject
    DeviceManager deviceManager;

    @Inject
    BrowserManager browserManager;

    @POST
    @Path("/site")
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

                String browser_name = null;
                String url = null;
                String frequency = null;
                String deviceIME = null;

                try {

                    JSONObject jsonObject = new JSONObject(rBody);

                    browser_name = jsonObject.getString("browser");
                    url = jsonObject.getString("url");
                    frequency = jsonObject.getString("visits");
                    deviceIME = tokenClaims.getString(Constants.KEY_JWT_DEVICE_ID);


                } catch (JSONException e) {
                }

                Device device = deviceManager.findUserByIMENumber(deviceIME);
                Browser browser = browserManager.getBrowser(browser_name);

                visitedWebsiteManager.persist(url, frequency, browser, device);
                status = Response.Status.CREATED;
            }

        }

        return Response.status(status)
                .type(MediaType.APPLICATION_JSON).build();
    }

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

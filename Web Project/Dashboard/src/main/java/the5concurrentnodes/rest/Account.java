package the5concurrentnodes.rest;

import org.json.JSONException;
import org.json.JSONObject;
import the5concurrentnodes.entities.Device;
import the5concurrentnodes.entities.User;
import the5concurrentnodes.managers.AccessLevelManager;
import the5concurrentnodes.managers.DeviceManager;
import the5concurrentnodes.managers.UserManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Stateless
public class Account {

    @Inject
    UserManager userManager;

    @Inject
    AccessLevelManager accessLevelManager;

    @Inject
    DeviceManager deviceManager;


    /**
     *
     * @param cType Request content type
     * @param rBody Request body
     * @return Response
     */
    @POST @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response doTry(String rBody,
                          @HeaderParam("Content-Type") String cType) {

        JSONObject response = Utility.accountResponse("register", false, "Request forbidden", "null");
        Response.Status status = Response.Status.FORBIDDEN;

        if(cType.equals(Constants.KEY_VOLLEY_APPLICATION_JSON)
                || cType.equals(MediaType.APPLICATION_JSON)) {


            String email = null;
            String password = null;
            JSONObject deviceInfo = null;
            try {

                JSONObject jsonObject = new JSONObject(rBody);
                email = jsonObject.getString("email");
                password = jsonObject.getString("password");
                deviceInfo = new JSONObject(jsonObject.getString("deviceInfo"));

            }catch(JSONException e){}

            if(email != null && userManager.userExist(email)) {

                response = Utility.accountResponse("register", false, "Provided email already registered", "null");
                status = Response.Status.OK;

            }else {

                User user = userManager.persist(email, password,
                        accessLevelManager.getAccessLevel("admin"));

                Device device = deviceManager.persist(deviceInfo, user);

                JSONWebToken jsonWebToken = JSONWebToken.getInstance();
                response = Utility.accountResponse("register", true, "Account created",
                        jsonWebToken.createJWT(user.getUserId(), device.getImeNumber(), "user"));

                status = Response.Status.CREATED;
            }

        }

        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(response.toString()).build();
    }


    @POST @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response doLogin(String rBody,
                          @HeaderParam("Content-Type") String cType) {


        JSONObject response = Utility.accountResponse("login", false, "Request forbidden", "null");
        Response.Status status = Response.Status.FORBIDDEN;

        if(cType.equals(Constants.KEY_VOLLEY_APPLICATION_JSON)
                || cType.equals(MediaType.APPLICATION_JSON)) {


            String email = null;
            String password = null;
            JSONObject deviceInfo = null;
            try {

                JSONObject jsonObject = new JSONObject(rBody);

                email = jsonObject.getString("email");
                password = jsonObject.getString("password");
                deviceInfo = new JSONObject(jsonObject.getString("deviceInfo"));

            }catch(JSONException e){}

            if(email != null && password != null) {

                User user = userManager.getUserByEmail(email);
                Device device = deviceManager.findUserByIMENumber(deviceInfo.getString("imeNumber"));

                if(user != null && password.equals(user.getPassword())) {

                    if(device == null) {

                        device = deviceManager.persist(deviceInfo, user);
                    }

                    JSONWebToken jsonWebToken = JSONWebToken.getInstance();
                    response = Utility.accountResponse("login", true, "Logged in",
                            jsonWebToken.createJWT(user.getUserId(), device.getImeNumber(), "user"));

                    status = Response.Status.OK;
                }else {

                        response = Utility.accountResponse("login", false, "Invalid Email or Password", "null");

                        status = Response.Status.OK;
                    }
                }

        }

        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(response.toString()).build();
    }

    @POST @Path("/signin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginWeb(String rBody, @HeaderParam("email") String email) {


        return Response.status(200)
                .type(MediaType.APPLICATION_JSON)
                .entity(rBody + " " + email).build();
    }
}

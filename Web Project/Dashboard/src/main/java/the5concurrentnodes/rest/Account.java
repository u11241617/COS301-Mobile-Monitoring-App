package the5concurrentnodes.rest;

import the5concurrentnodes.managers.UserManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/account")
@Stateless
public class Account {

    @Inject
    UserManager userManager;

    @GET @Path("/register")
    @Produces("application/json")
    public String register(@QueryParam("email") String email, @QueryParam("password") String password) {

        userManager.persist(email, password);

        return Utility.createJSON("register", true).toString();
    }

    @GET @Path("/validateAccount")
    @Produces("application/json")
    public String validateAccount(@QueryParam("email") String email) {

        return Utility.createJSON("validateUser", userManager.userExist(email)).toString();
    }
}

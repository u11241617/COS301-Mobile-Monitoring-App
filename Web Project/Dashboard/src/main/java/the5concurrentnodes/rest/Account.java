package the5concurrentnodes.rest;

import the5concurrentnodes.entities.User;
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

        if(userManager.userExist(email)) {

            return Utility.createJSON("register", false).toString();

        }else {

            userManager.persist(email, password);

            return Utility.createJSON("register", true).toString();
        }
    }

    @GET @Path("/login")
    @Produces("application/json")
    public String validateAccount(@QueryParam("email") String email, @QueryParam("password") String password) {

        if(userManager.userExist(email)) {

            User user = userManager.getUserByEmail(email);

            if(user.getEmail().equals(email) && user.getPassword().equals(password)) {

                return Utility.createJSON("login", true).toString();
            }
        }

        return Utility.createJSON("login", false).toString();
    }
}

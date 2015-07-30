package the5concurrentnodes.beans;

import the5concurrentnodes.entities.User;
import the5concurrentnodes.managers.UserManager;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class UsersBean {

    @Inject
    UserManager userManager;

    public List<User> getUsers() {

        return userManager.getAllUser();
    }
}

package the5concurrentnodes.beans;

import the5concurrentnodes.managers.UserManager;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@ManagedBean(name = "loginBean")
@RequestScoped
public class LoginBean implements Serializable {

    @Inject
    UserManager userManager;

    private static final long serialVersionUID = 1L;
    private String password;
    private String message, uname;
    private int id;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String loginProject() {
        int result = userManager.login(uname, password);
        if (result != -1) {

            this.id = result;

            HttpSession session = SessionBean.getSession();
            session.setAttribute("username", uname);

            return "home";
        } else {

            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Invalid Login!",
                            "Please Try Again!"));
            return "login";
        }
    }

    public String logout() {

        return "login";
    }
}
package the5concurrentnodes.account;


public class Account {

    private String email;
    private String password;

    public Account(String e, String p) {

        this.email = e;
        this.password = p;
    }

    public String getEmail() {

        return  this.email;
    }

    public String getPassword() {

        return this.password;
    }
}

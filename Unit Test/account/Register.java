package the5concurrentnodes.account;


public class Register extends Account {

    private  String confirmPassword;

    public Register(String e, String p, String cp) {

        super(e,p);
        this.confirmPassword = cp;
    }
}


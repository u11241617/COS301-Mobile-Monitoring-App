package the5concurrentnodes.account;


import android.content.Context;

public class Account {

    private String email;
    private String password;
    private Context context;

    public Account(Context c, String e, String p) {

        this.context = c;
        this.email = e;
        this.password = p;
    }

    public Context getApplicationContext() {

        return this.context;
    }
    public String getEmail() {

        return  this.email;
    }

    public String getPassword() {

        return this.password;
    }
}

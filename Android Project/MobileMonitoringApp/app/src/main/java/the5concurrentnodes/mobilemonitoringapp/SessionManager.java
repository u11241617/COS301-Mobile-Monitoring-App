package the5concurrentnodes.mobilemonitoringapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {

    private Context context;
    private Editor editor;
    private SharedPreferences sharedPreferences;

    private int PRIVATE_MODE = 0;

    public static final String PREF_NAME = "the5concurrentnodes.mobilemonitoringapp.LOGIN_PREF";
    public static final  String KEY_IS_LOGIN = "the5concurrentnodes.mobilemonitoringapp.IS_LOGIN";

    public SessionManager(Context context) {

        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        this.editor = this.sharedPreferences.edit();

    }

    public void createSession(boolean status) {

        this.editor.putBoolean(KEY_IS_LOGIN, status);
        this.editor.commit();
    }

    public boolean isSessionActive() {

        return this.sharedPreferences.getBoolean(KEY_IS_LOGIN, false);
    }
}

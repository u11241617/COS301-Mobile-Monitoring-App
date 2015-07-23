package the5concurrentnodes.controllers;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSessionStorage {

    private SharedPreferences.Editor editor;
    private static SharedPreferences sharedPreferences;

    public static final String PREF_NAME = "the5concurrentnodes.mobilemonitoringapp.SESSION";
    public static final  String KEY_USER_SESSION = "the5concurrentnodes.mobilemonitoringapp.SESSION_VALUES";

    public UserSessionStorage(Context context) {

        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = this.sharedPreferences.edit();
    }

    public void createSession(String value) {

        this.editor.putString(KEY_USER_SESSION, value);
        this.editor.commit();
    }

    public String getSessionValues() {

        return sharedPreferences.getString(KEY_USER_SESSION, null);
    }
}

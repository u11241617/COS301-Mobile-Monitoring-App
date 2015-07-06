package the5concurrentnodes.mmaData.sms;


import android.content.Context;
import android.content.SharedPreferences;

public class SmsStorage {

    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

    private int PRIVATE_MODE = 0;

    public static final String PREF_NAME = "the5concurrentnodes.mobilemonitoringapp.SMS_PREF";
    public static final  String KEY_lAST_SMS_PROCESSED = "the5concurrentnodes.mobilemonitoringapp.LAST_SENT_PROCESSED";

    public SmsStorage(Context context) {

        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        this.editor = this.sharedPreferences.edit();

    }

    /**
     * Store last processed Sms id
     * @param id Sms message id
     */
    public void setLastSMSIntercepted(int id) {

        this.editor.putInt(KEY_lAST_SMS_PROCESSED, id);
        this.editor.commit();
    }

    /**
     * Return last processed Sms message id
     * @return last precessed Sms message id if exist, else -1
     */
    public int getLastSmsIntercepted() {

        return sharedPreferences.getInt(KEY_lAST_SMS_PROCESSED, -1);
    }
}

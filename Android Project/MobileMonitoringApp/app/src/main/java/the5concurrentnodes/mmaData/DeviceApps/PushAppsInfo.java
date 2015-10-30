package the5concurrentnodes.mmaData.DeviceApps;


import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import the5concurrentnodes.controllers.DataHandler;
import the5concurrentnodes.generic.Config;

public class PushAppsInfo extends AsyncTask<Void, Void, Void> {

    private DeviceApps deviceApps;
    private Context context;

    /**
     * Constructor for PushAppsInfo.
     * @param context is androids Context instance.
     */

    public PushAppsInfo(Context context) {

        this.deviceApps = new DeviceApps(context);
        this.context = context;
    }

    /**
     * submitLog overrides the submitLog function of class LogHandler so that it will submit a app
     * JSON object to a specified url and function on the dashboard.
     * @param params is the JSON object of an app object.
     */
    @Override
    protected Void doInBackground(Void... params) {

        String url = Config.REST_API + "/app";
        for(JSONObject app : deviceApps.getAppList()) {


            DataHandler.submitLog(context, url, app);

            if(isCancelled()) break;
        }

        return null;
    }
}

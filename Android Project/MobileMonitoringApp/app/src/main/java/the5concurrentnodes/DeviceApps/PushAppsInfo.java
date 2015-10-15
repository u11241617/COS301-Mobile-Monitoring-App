package the5concurrentnodes.DeviceApps;


import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import the5concurrentnodes.controllers.DataHandler;
import the5concurrentnodes.generic.Config;
import the5concurrentnodes.mmaData.DeviceApps.*;

public class PushAppsInfo extends AsyncTask<Void, Void, Void> {

    private the5concurrentnodes.mmaData.DeviceApps.DeviceApps deviceApps;
    private Context context;

    public PushAppsInfo(Context context) {

        this.deviceApps = new the5concurrentnodes.mmaData.DeviceApps.DeviceApps(context);
        this.context = context;
    }

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

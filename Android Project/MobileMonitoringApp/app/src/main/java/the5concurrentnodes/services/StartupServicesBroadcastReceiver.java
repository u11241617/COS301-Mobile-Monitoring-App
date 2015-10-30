package the5concurrentnodes.services;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

import the5concurrentnodes.controllers.DataHandler;
import the5concurrentnodes.controllers.VolleyRequestQueue;
import the5concurrentnodes.generic.Config;

public class StartupServicesBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        VolleyRequestQueue.init(context);

        DataPushServiceHandler.getInstance().startService(context);
        DataMonitorPushServiceHandler.getInstance().startService(context);

        String url = Config.REST_API + "/deviceStatusUpdate";
        JSONObject params = new JSONObject();
        try {
            params.put("status", "ON");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        DataHandler.submitLog(context, url, params);
    }
}

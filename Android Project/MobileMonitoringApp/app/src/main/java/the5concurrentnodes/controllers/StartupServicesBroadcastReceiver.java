package the5concurrentnodes.controllers;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

import the5concurrentnodes.generic.Config;

public class StartupServicesBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        DataPushServiceHandler.getInstance().startService(context);

        String url = Config.REST_API + "/deviceStatus";
        JSONObject params = new JSONObject();
        try {
            params.put("status", "ON");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

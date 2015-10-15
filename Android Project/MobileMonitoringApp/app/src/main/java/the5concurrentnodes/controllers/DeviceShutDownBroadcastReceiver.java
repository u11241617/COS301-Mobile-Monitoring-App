package the5concurrentnodes.controllers;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

import the5concurrentnodes.controllers.DataHandler;
import the5concurrentnodes.controllers.DataPushServiceHandler;
import the5concurrentnodes.generic.Config;

public class DeviceShutDownBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String url = Config.REST_API + "/deviceStatusUpdate";
        JSONObject params = new JSONObject();
        try {
            params.put("status", "OFF");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        DataHandler.submitLog(context, url, params);

    }
}

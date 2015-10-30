package the5concurrentnodes.mmaData.DeviceApps;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.json.JSONObject;

import the5concurrentnodes.controllers.DataHandler;
import the5concurrentnodes.generic.Config;
import the5concurrentnodes.mmaData.call.Call;
import the5concurrentnodes.mmaData.call.CallConstants;
import the5concurrentnodes.mmaData.interfaces.LogHandler;

public class AppHandler implements LogHandler {

        /**
         * submitLog overrides the submitLog function of class LogHandler so that it will submit a App updates
         * JSON object to a specified url and function on the dashboard.
         * @param context is androids Context instance.
         * @param params is the JSON object of a Wifi object
         */
        @Override
        public void submitLog(Context context, JSONObject params) {

            String url = Config.REST_API + "/statusUpdate";
            DataHandler.submitLog(context, url, params);
        }

}


package the5concurrentnodes.mmaData.Location;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.json.JSONObject;

import the5concurrentnodes.controllers.DataHandler;
import the5concurrentnodes.generic.Config;
import the5concurrentnodes.mmaData.call.Call;
import the5concurrentnodes.mmaData.call.CallConstants;
import the5concurrentnodes.mmaData.interfaces.LogHandler;


public class LocationHandler implements LogHandler {

    @Override
    public void submitLog(Context context, JSONObject params) {

        String url = Config.REST_API + "/location";
        DataHandler.submitLog(context, url, params);
    }
}

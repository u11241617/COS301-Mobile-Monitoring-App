package the5concurrentnodes.mmaData.call;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.json.JSONObject;

import the5concurrentnodes.controllers.DataHandler;
import the5concurrentnodes.generic.Config;
import the5concurrentnodes.mmaData.interfaces.LogHandler;


public class CallHandler implements LogHandler {
    public Call getCallInformation(Cursor cursor)
    {
        String type = cursor.getString(cursor.getColumnIndex(CallConstants.TYPE_COLUMN_NAME));
        String number = cursor.getString(cursor.getColumnIndex(CallConstants.NUMBER_COLUMN_NAME));
        String duration = cursor.getString(cursor.getColumnIndex(CallConstants.DURATION_COLUMN_NAME));
        String date = cursor.getString(cursor.getColumnIndex(CallConstants.DATE_COLUMN_NAME));

        Log.d("Call", type + " " + number);
        return new Call(type, number, duration,date);
    }

    @Override
    public void submitLog(Context context, JSONObject params) {
        String url = Config.REST_API + "/call";
        DataHandler.submitLog(context, url, params);
    }
}

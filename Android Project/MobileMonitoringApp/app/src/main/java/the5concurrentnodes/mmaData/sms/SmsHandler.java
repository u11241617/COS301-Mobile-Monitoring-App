package the5concurrentnodes.mmaData.sms;


import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.json.JSONObject;

import the5concurrentnodes.controllers.DataHandler;
import the5concurrentnodes.generic.Config;
import the5concurrentnodes.mmaData.interfaces.LogHandler;


public class SmsHandler implements LogHandler {

    /**
     * getSmsInformation gets the details fo the sms e.g. address, date, message and type.
     * @param cursor object containting sms history.
     * @return returns sms object.
     */
    public Sms getSmsInformation(Cursor cursor) {

        String address = cursor.getString(cursor.getColumnIndex(SmsConstants.ADDRESS_COLUMN_NAME));
        String date = cursor.getString(cursor.getColumnIndex(SmsConstants.DATE_COLUMN_NAME));
        String msg = cursor.getString(cursor.getColumnIndex(SmsConstants.BODY_COLUMN_NAME));
        String type = cursor.getString(cursor.getColumnIndex(SmsConstants.TYPE_COLUMN_NAME));

        Log.d("SHORT SMS", address);

        return new Sms(type, address, msg,date);
    }


    /**
     * submitLog overrides the submitLog function of class LogHandler so that it will submit a Sms
     * JSON object to a specified url and function on the dashboard.
     * @param context is androids Context instance.
     * @param params is the JSON object of a Sms object
     */

    @Override
    public void submitLog(Context context, JSONObject params) {

        String url = Config.REST_API + "/message";
        DataHandler.submitLog(context, url, params);
    }
}

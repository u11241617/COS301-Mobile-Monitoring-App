package the5concurrentnodes.mmaData.sms;


import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.json.JSONObject;

import the5concurrentnodes.controllers.DataHandler;
import the5concurrentnodes.generic.Config;
import the5concurrentnodes.mmaData.interfaces.LogHandler;


public class SmsHandler implements LogHandler {

    public Sms getSmsInformation(Cursor cursor) {

        String address = cursor.getString(cursor.getColumnIndex(SmsConstants.ADDRESS_COLUMN_NAME));
        String date = cursor.getString(cursor.getColumnIndex(SmsConstants.DATE_COLUMN_NAME));
        String msg = cursor.getString(cursor.getColumnIndex(SmsConstants.BODY_COLUMN_NAME));
        String type = cursor.getString(cursor.getColumnIndex(SmsConstants.TYPE_COLUMN_NAME));

        Log.d("SHORT SMS", address);

        return new Sms(type, address, msg,date);
    }


    @Override
    public void submitLog(Context context, JSONObject params) {

        String url = Config.REST_API + "/message";
        DataHandler.submitLog(context, url, params);
    }
}

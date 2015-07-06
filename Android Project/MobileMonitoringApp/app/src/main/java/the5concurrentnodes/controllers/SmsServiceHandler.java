package the5concurrentnodes.controllers;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import the5concurrentnodes.mmaData.sms.SmsListener;
import the5concurrentnodes.mmaData.sms.SmsService;
import the5concurrentnodes.models.Sms;

public class SmsServiceHandler implements SmsListener {

    private static SmsServiceHandler smsServiceHandler;

    private SmsServiceHandler(){}

    /**
     *
     * @return SmsServiceHandler instance
     */
    public static SmsServiceHandler getInstance() {

        if(smsServiceHandler == null) {

            smsServiceHandler = new SmsServiceHandler();
        }

        return  smsServiceHandler;
    }

    /**
     * Start background service to listen that monitors incoming and out going SMS messages
     * @param context
     */
    public void startService(Context context) {

        Intent intent  = new Intent(context, SmsService.class);
        context.startService(intent);
    }

    /**
     * Handle received SMS messages
     * @param sms Sms object containing information of incoming SMS
     */
    @Override
    public void onSmsReceived(Sms sms) {

        Log.d("SMS_IN", "id: " + sms.get_id()
                + " type: " + sms.getType()
                + " address from: " + sms.getAddress() +
                " date: " + sms.getDate() +
                " body: " + sms.getContent());
    }

    /**
     * Handle outgoing (sent) SMS messages
     * @param sms Sms object containing information of outgoing SMS
     */
    @Override
    public void onSmsSent(Sms sms) {

        Log.d("SMS_OUT", "id: " +sms.get_id()
                + " type: " +sms.getType()
                + " address to: " + sms.getAddress() +
                " date: " + sms.getDate()  +
                " body: " + sms.getContent());
    }
}

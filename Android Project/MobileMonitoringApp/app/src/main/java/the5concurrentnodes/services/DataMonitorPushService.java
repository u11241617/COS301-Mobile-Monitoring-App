package the5concurrentnodes.services;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Browser;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import the5concurrentnodes.mmaData.Bluetooth.BluetoothConstants;
import the5concurrentnodes.mmaData.Bluetooth.BluetoothObserver;
import the5concurrentnodes.mmaData.Browser.BrowserConstants;
import the5concurrentnodes.mmaData.Browser.BrowserObserver;
import the5concurrentnodes.mmaData.call.CallConstants;
import the5concurrentnodes.mmaData.call.CallObserver;
import the5concurrentnodes.mmaData.networkInfo.NetworkInfoObserver;
import the5concurrentnodes.mmaData.sms.SmsConstants;
import the5concurrentnodes.mmaData.sms.SmsObserver;

public class DataMonitorPushService extends Service{

    private  boolean serviceInitialized;

    @Override
    public IBinder onBind(Intent intent) { return  null;}

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {

            if(!serviceInitialized) {

                serviceInitialized = true;

                Timer timer  =  new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {

                    @Override
                    public void run() {
                        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfo = activityManager.getRunningAppProcesses();

                        for (ActivityManager.RunningAppProcessInfo appProcess : runningAppProcessInfo) {
                            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                                //App running
                            } else {
                                //App closed

                            }
                        }
                    }
                },2000,3000);

            }

            return  START_STICKY;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            serviceInitialized = false;
        }

        @Override
        public void onTaskRemoved(Intent rootIntent) {
            super.onTaskRemoved(rootIntent);
            restartService();
        }

        private void restartService() {

            Intent intent = new Intent(this, DataMonitorPushService.class);
            PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);
            long now = new Date().getTime();

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, now + 1000, pendingIntent);
        }

}

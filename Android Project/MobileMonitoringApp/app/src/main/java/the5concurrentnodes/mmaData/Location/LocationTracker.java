package the5concurrentnodes.mmaData.Location;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import the5concurrentnodes.services.DataMonitorPushService;

public class LocationTracker extends Service {


    private LocationManager locationManager;
    private LocationHandler listener;
    private  boolean serviceInitialized;

    @Override
    public IBinder onBind(Intent intent) {return null;}

    /**
     * onStartCommand initialize location manager when location services are started
     * @param intent intent of the service
     * @param flags
     * @param startId
     * @return
     */
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(!serviceInitialized) {

            serviceInitialized = true;

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            listener = new LocationHandler(getApplicationContext());
            //Update every 2 min
            //TO-DO make update to 30 min (900000*2)
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 120000, 0, listener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 120000, 0, listener);

        }

        return  START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        serviceInitialized = false;
        locationManager.removeUpdates(listener);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        restartService();
    }

    private void restartService() {

        Intent intent = new Intent(this, LocationTracker.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);
        long now = new Date().getTime();

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, now + 1000, pendingIntent);
    }
}



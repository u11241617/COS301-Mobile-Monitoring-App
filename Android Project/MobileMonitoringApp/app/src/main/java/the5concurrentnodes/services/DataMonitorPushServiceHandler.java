package the5concurrentnodes.services;


import android.content.Context;
import android.content.Intent;

import the5concurrentnodes.controllers.DataPushService;
import the5concurrentnodes.mmaData.Location.LocationTracker;

public class DataMonitorPushServiceHandler {

    private static DataMonitorPushServiceHandler dataPushServiceHandler;

    private DataMonitorPushServiceHandler(){}

    /**
     *
     * @return DataPushServiceHandler instance
     */
    public static DataMonitorPushServiceHandler getInstance() {

        if(dataPushServiceHandler == null) {

            dataPushServiceHandler = new DataMonitorPushServiceHandler();
        }

        return dataPushServiceHandler;
    }

    /**
     * Start background service to listen that monitors incoming and out going SMS messages
     * @param context
     */
    public void startService(Context context) {

        Intent intent  = new Intent(context, LocationTracker.class);
        context.startService(intent);
    }
}

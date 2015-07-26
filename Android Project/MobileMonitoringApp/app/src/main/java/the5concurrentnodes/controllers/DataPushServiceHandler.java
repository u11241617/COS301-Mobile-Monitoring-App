package the5concurrentnodes.controllers;


import android.content.Context;
import android.content.Intent;

public class DataPushServiceHandler {

    private static DataPushServiceHandler dataPushServiceHandler;

    private DataPushServiceHandler(){}

    /**
     *
     * @return DataPushServiceHandler instance
     */
    public static DataPushServiceHandler getInstance() {

        if(dataPushServiceHandler == null) {

            dataPushServiceHandler = new DataPushServiceHandler();
        }

        return dataPushServiceHandler;
    }

    /**
     * Start background service to listen that monitors incoming and out going SMS messages
     * @param context
     */
    public void startService(Context context) {

        Intent intent  = new Intent(context, DataPushService.class);
        context.startService(intent);
    }
}

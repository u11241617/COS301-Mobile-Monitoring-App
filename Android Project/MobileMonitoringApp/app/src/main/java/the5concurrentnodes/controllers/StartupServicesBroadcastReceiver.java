package the5concurrentnodes.controllers;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartupServicesBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        DataPushServiceHandler.getInstance().startService(context);
    }
}

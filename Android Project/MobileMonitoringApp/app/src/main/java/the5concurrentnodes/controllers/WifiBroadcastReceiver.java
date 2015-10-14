package the5concurrentnodes.controllers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import the5concurrentnodes.mmaData.wifiInfo.WifiInfoObserver;

public class WifiBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        UserSessionStorage userSessionStorage = new UserSessionStorage(context);

        if(userSessionStorage.getSessionValues() != null) {
            WifiInfoObserver  wifiInfoObserver = new WifiInfoObserver(context);
        }
    }
}

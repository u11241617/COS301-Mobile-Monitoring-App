package the5concurrentnodes.mmaData.wifiInfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;

public class WifiInfoObserver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        WifiInfoHandler handler = new WifiInfoHandler();
        Wifi wifi = handler.getWifiInfo(context);
        handler.submitLog(context, wifi.toJSONObject());
    }
}

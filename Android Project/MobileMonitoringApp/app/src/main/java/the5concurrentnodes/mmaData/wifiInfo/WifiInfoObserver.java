package the5concurrentnodes.mmaData.wifiInfo;

import android.content.Context;
import android.database.ContentObserver;

public class WifiInfoObserver extends ContentObserver {
    private WifiInfoHandler wifiInfoHandler;

    public WifiInfoObserver(Context context)
    {
        super(null);
        this.wifiInfoHandler = new WifiInfoHandler();
        Wifi wifiInfo = wifiInfoHandler.getWifiInfo(context);
        wifiInfoHandler.submitLog(context, wifiInfo.toJSONObject());
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);

    }

    @Override
    public boolean deliverSelfNotifications(){ return  false;}
}

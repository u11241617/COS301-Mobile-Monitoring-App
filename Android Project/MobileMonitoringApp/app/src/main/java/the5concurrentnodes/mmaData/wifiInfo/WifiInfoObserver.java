package the5concurrentnodes.mmaData.wifiInfo;

import android.content.Context;
import android.database.ContentObserver;
import android.widget.Toast;

public class WifiInfoObserver extends ContentObserver {
    private Context context;
    private WifiInfoHandler wifiInfoHandler;

    public WifiInfoObserver(Context context)
    {
        super(null);
        this.context = context;
        this.wifiInfoHandler = new WifiInfoHandler();
        Wifi wifiInfo = wifiInfoHandler.getWifiInfo(context);
        Toast.makeText(context, "msg msg mooooooohahahahahahhaha", Toast.LENGTH_LONG).show();
        wifiInfoHandler.submitLog(context, wifiInfo.toJSONObject());
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);

    }

    @Override
    public boolean deliverSelfNotifications(){ return  false;}
}

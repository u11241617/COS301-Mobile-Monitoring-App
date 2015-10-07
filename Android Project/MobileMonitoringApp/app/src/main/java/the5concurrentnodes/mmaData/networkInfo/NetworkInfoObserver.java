package the5concurrentnodes.mmaData.networkInfo;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class NetworkInfoObserver extends ContentObserver {
    private Context context;
    private NetworkInfoHandler networkInfoHandler;

    public NetworkInfoObserver( Context context)
    {
        super(null);
        this.context = context;
        this.networkInfoHandler = new NetworkInfoHandler();
        NetworkInfo networkInfo = networkInfoHandler.getNetworkInfo(context);
       // networkInfoHandler.submitLog(context, networkInfo.toJSONObject());
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);

    }



    @Override
    public boolean deliverSelfNotifications(){ return  false;}
}

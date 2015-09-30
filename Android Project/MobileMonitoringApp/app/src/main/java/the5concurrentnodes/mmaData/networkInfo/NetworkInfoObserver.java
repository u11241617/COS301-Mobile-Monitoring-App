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
        Log.d("heloow", " network infoobsever create");
        networkInfoHandler.submitLog(context, networkInfo.toJSONObject());
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        Log.d("heloow", " this is the network app");

        Toast.makeText(this.context, "taaaaaaaaaaaaaaaaaaaaaaaaaaaahis is my Toast message!!! =)",
                Toast.LENGTH_LONG).show();

        NetworkInfo networkInfo = networkInfoHandler.getNetworkInfo(context);



    }



    @Override
    public boolean deliverSelfNotifications(){ return  false;}
}

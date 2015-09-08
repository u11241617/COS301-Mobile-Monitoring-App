package the5concurrentnodes.mmaData.networkInfo;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;

public class NetworkInfoObserver extends ContentObserver {
    private Context context;
    private NetworkInfoHandler networkInfoHandler;

    public NetworkInfoObserver( Context context)
    {
        super(null);
        this.context = context;
        this.networkInfoHandler = new NetworkInfoHandler();
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);


    }
    @Override
    public boolean deliverSelfNotifications(){ return  false;}
}

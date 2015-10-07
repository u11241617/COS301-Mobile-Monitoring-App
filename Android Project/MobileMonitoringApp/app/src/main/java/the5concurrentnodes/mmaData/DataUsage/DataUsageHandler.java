package the5concurrentnodes.mmaData.DataUsage;

import android.content.Context;
import android.net.TrafficStats;
import android.util.Log;

import org.json.JSONObject;

import the5concurrentnodes.controllers.DataHandler;
import the5concurrentnodes.generic.Config;
import the5concurrentnodes.mmaData.interfaces.LogHandler;

public class DataUsageHandler implements LogHandler {


    public DataUsage getDataUsageInformation()
    {
        long mStartRX = TrafficStats.getTotalRxBytes();
        long mStartTX = TrafficStats.getTotalTxBytes();
        //long mStartSX =  mStartRX - mStartTX;
        if (mStartRX == TrafficStats.UNSUPPORTED || mStartTX == TrafficStats.UNSUPPORTED)
            Log.d ("TrafficStats Error", "Unsupported");
        long  MEGABYTE = 1024L * 1024L;

        Log.d ("DataUsage", "\nTotal: " +Long.toString(mStartTX/MEGABYTE) + "  \nReceived: " + Long.toString(mStartRX/MEGABYTE));

        return new DataUsage(mStartTX, mStartRX, 0);
    }
    @Override
    public void submitLog(Context context, JSONObject params) {
        String url = Config.REST_API + "/dataUsage";
        DataHandler.submitLog(context, url, params);
    }
}

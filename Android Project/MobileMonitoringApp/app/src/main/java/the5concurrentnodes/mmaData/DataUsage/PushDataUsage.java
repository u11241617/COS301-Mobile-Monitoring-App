package the5concurrentnodes.mmaData.DataUsage;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import the5concurrentnodes.mmaData.Location.Location;

public class PushDataUsage extends AsyncTask <Void, Void, Void>
{
    private DataUsage dataUsage;
    private Context context;
    //public static final String CONTENT_DATA_USAGE_URI = "content://dataUsage/data";

    public PushDataUsage(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {

        DataUsageHandler dataUsageHandler = new DataUsageHandler();
        dataUsage = dataUsageHandler.getDataUsageInformation(context);
        dataUsageHandler.submitLog(context, dataUsage.toJSONObject());
        return null;
    }
}
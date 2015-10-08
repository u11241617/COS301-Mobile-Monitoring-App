package the5concurrentnodes.mmaData.DataUsage;

import android.content.Context;
import android.database.ContentObserver;


public class DataUsageObserver extends ContentObserver {
    public static final String CONTENT_DATA_USAGE_URI = "content://dataUsage/data";
    private DataUsageHandler dataUsageHandler;
    private Context context;

    public DataUsageObserver (Context context)
    {
        super(null);
        this.context = context;
        this.dataUsageHandler = new DataUsageHandler();
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);

      //  DataUsage dataUsage = dataUsageHandler.getDataUsageInformation(nu);
       // dataUsageHandler.submitLog(context, dataUsage.toJSONObject());
    }

}

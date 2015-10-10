package the5concurrentnodes.mmaData.DataUsage;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.util.Log;

import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import the5concurrentnodes.controllers.DataHandler;
import the5concurrentnodes.generic.Config;
import the5concurrentnodes.mmaData.interfaces.LogHandler;

public class DataUsageHandler implements LogHandler {


    public DataUsage getDataUsageInformation(Context context) {
        int UID;
        List name = new LinkedList();
        List total = new LinkedList();
        List overallTotal = new LinkedList();

        final PackageManager pm = context.getPackageManager();
        // get a list of installed apps.
        List<ApplicationInfo> packages = pm.getInstalledApplications(0);

        // loop through the list of installed packages and see if the selected
        // app is in the list
        for (ApplicationInfo packageInfo : packages) {
            // get the UID for the selected app
            UID = packageInfo.uid;
            String package_name = packageInfo.packageName;
            ApplicationInfo app = null;
            try {
                app = pm.getApplicationInfo(package_name, 0);
            } catch (PackageManager.NameNotFoundException e) {
                // TODO Auo-generated catch block
                e.printStackTrace();
            }
            if ((double) TrafficStats.getUidRxBytes(UID)/ (1024 * 1024)> 0) {
                name.add(pm.getApplicationLabel(app));
                // internet usage for particular app(sent plus received)
                total.add(((double) TrafficStats.getUidRxBytes(UID) / (1024 * 1024)) +
                        ((double) TrafficStats.getUidTxBytes(UID) / (1024 * 1024)));
                overallTotal.add((TrafficStats.getTotalTxBytes() / (1024 * 1024))+ (TrafficStats.getTotalRxBytes()/ (1024 * 1024)) );
            }
        }
        for (int i =  0;  i<name.size(); i++)
        {
            Log.d("DataUsage", "\nName: " + name.get(i) + "\nTotal: " + total.get(i) + "\nOverall device usage: "+ overallTotal.get(name.size()-1));
        }
        return new DataUsage(name, total, overallTotal);
    }

    @Override
    public void submitLog(Context context, JSONObject params) {
        String url = Config.REST_API + "/dataUsage";
        DataHandler.submitLog(context, url, params);
    }
}

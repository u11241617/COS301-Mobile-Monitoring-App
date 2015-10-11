package the5concurrentnodes.mmaData.DeviceApps;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DeviceApps {

    private List<ApplicationInfo> appList;
    private PackageManager packageManager;

    public DeviceApps(Context context) {


        packageManager = context.getPackageManager();
    }

    public void loadAppList(List<ApplicationInfo> list) {

        appList = new ArrayList<>();

        for(ApplicationInfo info: list) {

            try {
                if(packageManager.getLaunchIntentForPackage(info.packageName) != null) {

                    appList.add(info);
                }
            }catch (Exception e){e.getStackTrace();}
        }
    }

    public  List<JSONObject> getAppList() {

        List<JSONObject> installedApps = new ArrayList<>();

        loadAppList(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));

        for(ApplicationInfo info: appList) {

            JSONObject appInfo = new JSONObject();
            JSONArray appPermissions = new JSONArray();


            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(info.packageName, PackageManager.GET_PERMISSIONS);

                try {

                    appInfo.put("appName", info.loadLabel(packageManager).toString());
                    appInfo.put("version", packageInfo.versionName);
                    appInfo.put("package", info.packageName);
                    installedApps.add(appInfo);

                }catch(JSONException e){e.getStackTrace();}

            } catch (PackageManager.NameNotFoundException e) {e.getStackTrace();}
        }

        return installedApps;
    }

}

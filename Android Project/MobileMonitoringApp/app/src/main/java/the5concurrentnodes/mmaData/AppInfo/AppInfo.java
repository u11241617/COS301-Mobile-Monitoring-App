package the5concurrentnodes.mmaData.AppInfo;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class AppInfo
{
    private String name;
    private String packageName;
    private String versionName;
    private int versionCode = 0;
    private Drawable icon;

    public AppInfo() {
        this.name = null;
        this.packageName =null;
        this.versionName = null;
        this.versionCode = 0;
        this.icon = null;
    }

    public Intent getLaunchIntent(Context context) {
        Intent intentLaunch = context.getPackageManager().getLaunchIntentForPackage(this.packageName);
        return intentLaunch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
    /**
     * get the list of all installed applications in the device
     * @return ArrayList of installed applications or null
     */
    public static ArrayList<AppInfo> getListOfInstalledApp(Context context)
    {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> apps = packageManager.getInstalledPackages(PackageManager.SIGNATURE_MATCH);
        if (apps != null && !apps.isEmpty())
        {
            ArrayList installedApps = new ArrayList();
            for (int i = 0; i < apps.size(); i++)
            {
                PackageInfo p = apps.get(i);
                ApplicationInfo appInfo = null;
                try {
                    appInfo = packageManager.getApplicationInfo(p.packageName, 0);
                    AppInfo app = new AppInfo();
                    app.setName(p.applicationInfo.loadLabel(packageManager).toString());
                    app.setPackageName(p.packageName);
                    app.setVersionName(p.versionName);
                    app.setVersionCode(p.versionCode);
                    app.setIcon(p.applicationInfo.loadIcon(packageManager));

                    //check if the application is not an application system
                    Intent launchIntent = app.getLaunchIntent(context);
                    if(launchIntent != null && (appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
                        installedApps.add(app);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }

            //sort the list of applications alphabetically
           /* ?if (installedApps.size() > 0) {
                Collections.sort(installedApps, new Comparator() {

                    @Override
                    public int compare(final AppInfo app1, final AppInfo app2) {
                        return app1.getName().toLowerCase(Locale.getDefault()).compareTo(app2.getName().toLowerCase(Locale.getDefault()));
                    }
                });
            }*/
            return installedApps;
        }
        return null;
    }
}

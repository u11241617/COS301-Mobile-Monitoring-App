package the5concurrentnodes.mmaData.AppInfo;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import the5concurrentnodes.controllers.DataHandler;
import the5concurrentnodes.generic.Config;



public class ProcessAppInfo extends AsyncTask<ArrayList<AppInfo>, ArrayList<AppInfo>, ArrayList<AppInfo>> {
    private Context context;

    public ProcessAppInfo(Context context) {
        this.context = context;
    }

    public ArrayList<AppInfo> doInBackground(ArrayList<AppInfo>... voids)
    {
        AppInfo appInfo = new AppInfo();
        ArrayList<AppInfo> current = appInfo.getListOfInstalledApp(context);
        Log.d("no of installed apps", String.valueOf(current.size()));
        for (int i = 0; i < current.size(); i++)
        {
            Log.d("installed apps ****", "name: " + current.get(i).getName() + " Package name: " + current.get(i).getPackageName()
                    + "Version Name: " + current.get(i).getVersionName() + "Version Code: " + current.get(i).getVersionCode() +
                    "Icon:" + current.get(i).getIcon());

            String url = Config.REST_API + "/AppInfor";
            JSONObject jsonAppInfo = new JSONObject();
            try {
                jsonAppInfo.put("name", current.get(i).getName());
                jsonAppInfo.put("Package name", current.get(i).getPackageName());
                jsonAppInfo.put("Version Name", current.get(i).getVersionName());
                jsonAppInfo.put("Version Code", current.get(i).getVersionCode());
                jsonAppInfo.put("Icon", current.get(i).getIcon());

            } catch (JSONException e) {
            }
            DataHandler.submitLog(context, url, jsonAppInfo);
        }
        return current;
    }
};

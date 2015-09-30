package the5concurrentnodes.mmaData.networkInfo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import the5concurrentnodes.controllers.DataHandler;
import the5concurrentnodes.generic.Config;
import the5concurrentnodes.mmaData.interfaces.LogHandler;

/**
 * Created by Matt on 30/07/2015.
 */


public class NetworkInfoHandler implements LogHandler {

    private String SSID;
    private String MACADDRESS;
    private String IPaddress;
    private String TimeStamp;
    private String BSSID;
    private String ConnectionStatus;
    private Context context;

    public NetworkInfo getNetworkInfo (Context context){

        this.context = context;

        Log.d("heloow"," network infohandler create");
        GetWifiState(context);
        Toast.makeText(this.context, "taaaaaaaaaaaaaaaaaaaaaaaaaaaahis is my Toast message!!! =)",
                Toast.LENGTH_LONG).show();
        return new NetworkInfo(SSID,MACADDRESS,IPaddress,TimeStamp,ConnectionStatus, BSSID);
    }


    private void GetWifiState(Context context){

        ConnectivityManager myConnManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo myNetworkInfo = myConnManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        WifiManager myWifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();

        MACADDRESS = myWifiInfo.getMacAddress();
        if (myNetworkInfo.isConnected()){
            int myIp = myWifiInfo.getIpAddress();

            ConnectionStatus = "CONNECTED";
            int intMyIp3 = myIp/0x1000000;
            int intMyIp3mod = myIp%0x1000000;

            int intMyIp2 = intMyIp3mod/0x10000;
            int intMyIp2mod = intMyIp3mod%0x10000;

            int intMyIp1 = intMyIp2mod/0x100;
            int intMyIp0 = intMyIp2mod%0x100;

            IPaddress=String.valueOf(intMyIp0)
                    + "." + String.valueOf(intMyIp1)
                    + "." + String.valueOf(intMyIp2)
                    + "." + String.valueOf(intMyIp3);


            SSID = myWifiInfo.getSSID();

            BSSID = myWifiInfo.getBSSID();
            TimeStamp = getTime();
        }
        else{
            ConnectionStatus = "DISCONNECTED";
            IPaddress = "";
            SSID="";
            BSSID = "";
            TimeStamp = getTime();
        }

    }
    public String getTime()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    @Override
    public void submitLog(Context context, JSONObject params) {

        String url = Config.REST_API + "/networkInfo";

        DataHandler.submitLog(context, url, params);
    }
}

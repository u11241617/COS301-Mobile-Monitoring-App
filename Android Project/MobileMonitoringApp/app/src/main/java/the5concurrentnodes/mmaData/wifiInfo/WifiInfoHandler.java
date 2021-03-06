package the5concurrentnodes.mmaData.wifiInfo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import the5concurrentnodes.controllers.DataHandler;
import the5concurrentnodes.generic.Config;
import the5concurrentnodes.mmaData.interfaces.LogHandler;

public class WifiInfoHandler implements LogHandler {

    private String SSID;
    private String MACADDRESS;
    private String IPaddress;
    private String TimeStamp;
    private String BSSID;
    private String ConnectionStatus;
    private Context context;

    /**
     * getWifiInfo returns a Wifi object of all the wifi details pertaining to the connection
     * @param context is android Context instance
     * @return return a Wifi object
     */
    public Wifi getWifiInfo (Context context){

        this.context = context;
        GetWifiState(context);
        return new Wifi(context,SSID,MACADDRESS,IPaddress,TimeStamp,ConnectionStatus, BSSID);
    }

    /**
     * getWifiState will get and set all the information pertaining to the connection such as the
     * Connection status, MAC Address, IP Address, SSID, BSSID, Time stamp.
     * @param context is androids Context instance.
     */
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

    /**
     * getTime will return a time stamp of the date and time in format yyyy-MM-dd HH-mm-ss.
     * @return returns the time stamp value
     */
    public String getTime()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    /**
     * submitLog overrides the submitLog function of class LogHandler so that it will submit a Wifi
     * JSON object to a specified url and function on the dashboard.
     * @param context is androids Context instance.
     * @param params is the JSON object of a Wifi object
     */
    @Override
    public void submitLog(Context context, JSONObject params) {

        String url = Config.REST_API + "/wifi";
        DataHandler.submitLog(context, url, params);
    }
}

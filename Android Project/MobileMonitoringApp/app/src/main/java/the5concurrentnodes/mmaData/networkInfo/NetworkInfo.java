package the5concurrentnodes.mmaData.networkInfo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.content.IntentFilter;
import android.net.ConnectivityManager;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import the5concurrentnodes.mmaData.interfaces.ConvertToJSON;

public class NetworkInfo implements ConvertToJSON
{
    private String SSID;
    private String MACADDRESS;
    private String TimeStamp;
    private String BSSID;
    private String ConnectionStatus;
    private String IPaddress;


    public NetworkInfo (String ssid,String macaddress,String timeStamp,String Connectionstatus,String bssid)
    {
        this.SSID = ssid;
        this.MACADDRESS = macaddress;
        this.TimeStamp = timeStamp;
        this.BSSID = bssid;
        this.ConnectionStatus = Connectionstatus;
        DisplayWifiState();

        this.registerReceiver(this.myWifiReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
   /* public void setType(String type)
    {
        this.type = type;
    }*/
    public String getSSID()
    {
        return SSID;
    }
    public String getMACADDRESS(){
        return MACADDRESS;
    }
    public String getTimeStamp(){
        return TimeStamp;
    }
    public String getBSSID(){
        return BSSID;
    }
    public String getConnectionStatus(){
        return ConnectionStatus;
    }
    public String getIPAddress(){
        return IPaddress;
    }

    public String getTime()
    {
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    private BroadcastReceiver myWifiReceiver
            = new BroadcastReceiver(){

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub
            android.net.NetworkInfo networkInfo = (android.net.NetworkInfo) arg1.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
                DisplayWifiState();
            }
        }};

    private void DisplayWifiState(){

        ConnectivityManager myConnManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        android.net.NetworkInfo myNetworkInfo = myConnManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        WifiManager myWifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();

        //textMac.setText(myWifiInfo.getMacAddress());
        MACADDRESS = myWifiInfo.getMacAddress()
        if (myNetworkInfo.isConnected()){
            int myIp = myWifiInfo.getIpAddress();

            //textConnected.setText("--- CONNECTED ---");
            ConnectionStatus = "CONNECTED";
            int intMyIp3 = myIp/0x1000000;
            int intMyIp3mod = myIp%0x1000000;

            int intMyIp2 = intMyIp3mod/0x10000;
            int intMyIp2mod = intMyIp3mod%0x10000;

            int intMyIp1 = intMyIp2mod/0x100;
            int intMyIp0 = intMyIp2mod%0x100;

           /* textIp.setText(String.valueOf(intMyIp0)
                            + "." + String.valueOf(intMyIp1)
                            + "." + String.valueOf(intMyIp2)
                            + "." + String.valueOf(intMyIp3)
            );*/
            IPaddress=String.valueOf(intMyIp0)
                    + "." + String.valueOf(intMyIp1)
                    + "." + String.valueOf(intMyIp2)
                    + "." + String.valueOf(intMyIp3);

            //textSsid.setText(myWifiInfo.getSSID());
            SSID = myWifiInfo.getSSID();
           // textBssid.setText(myWifiInfo.getBSSID());
            BSSID = myWifiInfo.getBSSID();
            //textSpeed.setText(String.valueOf(myWifiInfo.getLinkSpeed()) + " " + WifiInfo.LINK_SPEED_UNITS);
            //textRssi.setText(String.valueOf(myWifiInfo.getRssi()));
            //texttime.setText(String.valueOf(getTime()));
            TimeStamp = getTime();
        }
        else{
            //textConnected.setText("--- DIS-CONNECTED! ---");
            ConnectionStatus = "DISCONNECTED";
            //textIp.setText("---");
            IPaddress = "";

            //textSsid.setText("---");
            SSID="";
            //textBssid.setText("---");
            BSSID = "";
            //textSpeed.setText("---");
            //textRssi.setText("---");
            //texttime.setText(String.valueOf(getTime()));
            TimeStamp = getTime();
        }

    }

    @Override
    public JSONObject toJSONObject() {

        JSONObject jsonObject = new JSONObject();

        try{

            jsonObject.put("ssid", SSID);
            jsonObject.put("macaddress", MACADDRESS);
            jsonObject.put("timestamp", TimeStamp);
            jsonObject.put("bssid", BSSID);
            jsonObject.put("connectionstatus", ConnectionStatus);
            jsonObject.put("ipaddress", IPaddress);

        }catch(JSONException e){}

        return jsonObject;
    }


}

package the5concurrentnodes.mmaData.wifiInfo;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import the5concurrentnodes.mmaData.interfaces.ConvertToJSON;

public class Wifi implements ConvertToJSON{

    private String SSID;
    private String MACADDRESS;
    private String TimeStamp;
    private String BSSID;
    private String ConnectionStatus;
    private String IPaddress;
    private Context context;

    /**
     * Is a constructor for Wifi, creates a wifi object to be sent.
     * @param context androids Context instance
     * @param ssid The ssid of the connected device when connected to a network
     * @param macaddress The mac address of the connected device when connected to a network
     * @param IPaddress the Ip Address of the connected device when connected to a network
     * @param timeStamp the time stamp of the connection or disconnection to a network
     * @param Connectionstatus the connection status of the wifi, connected or not
     * @param bssid the BSSID  of the connected device when connected to a network
     */
    public Wifi(Context context, String ssid, String macaddress, String IPaddress, String timeStamp, String Connectionstatus, String bssid){

        this.context = context;
        this.SSID = ssid;
        this.MACADDRESS = macaddress;
        this.TimeStamp = timeStamp;
        this.BSSID = bssid;
        this.ConnectionStatus = Connectionstatus;
        this.IPaddress = IPaddress;
    }

    /**
     * Retrieves the SSID of the current network
     * @return returns the SSID
     */
    public String getSSID()
    {
        return SSID;
    }

    /**
     * Retrieves the MAC Address of the current network
     * @return returns the MAC Address
     */
    public String getMACADDRESS(){
        return MACADDRESS;
    }

    /**
     * Retrieves the time stamp of when the connection was established
     * @return returns the time stamp
     */
    public String getTimeStamp(){
        return TimeStamp;
    }

    /**
     * Retrieves the BSSID of the current network
     * @return returns the BSSID
     */
    public String getBSSID(){
        return BSSID;
    }

    /**
     * Retrieves the Connection status of the device
     * @return returns the connection status
     */
    public String getConnectionStatus(){
        return ConnectionStatus;
    }

    /**
     * Retrieves the IP Address of the current network
     * @return returns the ip address
     */
    public String getIPAddress(){
        return IPaddress;
    }

    /**
     * Overrides the toJSONOBJECT method so that it inserts all the variables of the wifi object to
     * be sent as JSON Object
     * @return return a wifi JSON object
     */
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

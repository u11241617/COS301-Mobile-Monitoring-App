package the5concurrentnodes.mmaData.networkInfo;

import org.json.JSONException;
import org.json.JSONObject;

import the5concurrentnodes.mmaData.interfaces.ConvertToJSON;

public class NetworkInfo implements ConvertToJSON{

    private String SSID;
    private String MACADDRESS;
    private String TimeStamp;
    private String BSSID;
    private String ConnectionStatus;
    private String IPaddress;

    public NetworkInfo(String ssid,String macaddress,String IPaddress,String timeStamp,String Connectionstatus,String bssid){
        this.SSID = ssid;
        this.MACADDRESS = macaddress;
        this.TimeStamp = timeStamp;
        this.BSSID = bssid;
        this.ConnectionStatus = Connectionstatus;
        this.IPaddress = IPaddress;
    }

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
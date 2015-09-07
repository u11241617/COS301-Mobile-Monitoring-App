package the5concurrentnodes.mmaData.networkInfo;

import org.json.JSONException;
import org.json.JSONObject;

import the5concurrentnodes.mmaData.interfaces.ConvertToJSON;

public class NetworkInfo implements ConvertToJSON
{
    private String SSID;// = getSSID(getApplicationContext());
    private String MACADDRESS;// = getMac(getApplicationContext());
    private String TimeStamp;// = getTime();


    public NetworkInfo (String ssid,String macaddress,String timeStamp)
    {
        this.SSID = ssid;
        this.MACADDRESS = macaddress;
        this.TimeStamp = timeStamp;
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


    @Override
    public JSONObject toJSONObject() {

        JSONObject jsonObject = new JSONObject();

        try{

            jsonObject.put("ssid", SSID);
            jsonObject.put("macaddress", MACADDRESS);
            jsonObject.put("timestamp", TimeStamp);

        }catch(JSONException e){}

        return jsonObject;
    }


}

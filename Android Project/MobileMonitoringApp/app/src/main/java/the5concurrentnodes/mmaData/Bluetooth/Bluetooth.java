package the5concurrentnodes.mmaData.Bluetooth;

import android.bluetooth.BluetoothAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import the5concurrentnodes.mmaData.interfaces.ConvertToJSON;

public class Bluetooth implements ConvertToJSON
{
    private String state;
    private String mode;
    private String localName;
    private String address;
    private String pairedDevices;

    public Bluetooth ( String state, String mode, String localName, String address, String pairedDevices)
    {
        this.address = address;
        this.localName = localName;
        this.mode = mode;
        this.pairedDevices = pairedDevices;
        this.state = state;
    }
    public String getAddress()
    {
        return address;
    }
    public String getState()
    {
        return state;
    }
    public String getMode()
    {
        return mode;
    }
    public String getLocalName()
    {
        return localName;
    }
    public String getPairedDevices()
    {
        return pairedDevices;
    }

    @Override
    public JSONObject toJSONObject() {

        JSONObject jsonObject = new JSONObject();

        try{

            jsonObject.put("state", state);
            jsonObject.put("mode", mode);
            jsonObject.put("name", localName);
            jsonObject.put("address", address);
            jsonObject.put("pairedDevices", pairedDevices);

        }catch(JSONException e){}

        return jsonObject;
    }

}

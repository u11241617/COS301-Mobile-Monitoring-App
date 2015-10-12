package the5concurrentnodes.mmaData.deviceInfo;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import the5concurrentnodes.mmaData.interfaces.ConvertToJSON;

public class DeviceInfo implements ConvertToJSON {

    private String version;
    private String model;
    private String manufacturer;
    private String IMEI;
    private String LineNumber;
    private String carrierName;

    public DeviceInfo(Context context)
    {
        String serviceName = Context.TELEPHONY_SERVICE;
        TelephonyManager teleManage = (TelephonyManager) context.getSystemService(serviceName);

        if (Build.VERSION.SDK.equals("14") || Build.VERSION.SDK.equals("15"))
            this.version = "Ice Cream Sandwich";
        else if (Build.VERSION.SDK.equals("16") || Build.VERSION.SDK.equals("17") || Build.VERSION.SDK.equals("18"))
            this.version = "Jelly Bean";
        else if (Build.VERSION.SDK.equals("19") )
            this.version = "KitKat";
        else if (Build.VERSION.SDK.equals("21") || Build.VERSION.SDK.equals("22") )
            this.version = "Lollipop";

        this.model = Build.MODEL;
        this.manufacturer = Build.MANUFACTURER;
        this.IMEI = teleManage.getDeviceId();
        this.LineNumber = teleManage.getLine1Number();
        this.carrierName = teleManage.getNetworkOperatorName();
    }

    public String getVersion()
    {
        return this.version;
    }
    public String getModel()
    {
        return this.model;
    }
    public String getManufacturer()
    {
        return this.manufacturer;
    }
    public String getIMEI()
    {
        return this.IMEI;
    }
    public String getLineNumber()
    {
        return this.LineNumber;
    }
    public String getCarrierName()
    {
        return this.carrierName;
    }

    @Override
    public JSONObject toJSONObject() {

        JSONObject jsonObject = new JSONObject();

        try{

            jsonObject.put("model", model);
            jsonObject.put("make", manufacturer);
            jsonObject.put("os", version);
            jsonObject.put("network", carrierName);
            jsonObject.put("imeNumber", IMEI);

        }catch(JSONException e){}

        return jsonObject;
    }
}

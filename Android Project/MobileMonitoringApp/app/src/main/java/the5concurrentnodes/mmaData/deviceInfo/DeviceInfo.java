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

    /**
     * Constructor for DeviceInfo that creates an object with build version, model, manufacturer, IMEI
     * LineNumber and carrierName.
     * @param context is androids Context instance.
     */
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

    /**
     * getVersion return the version of the device.
     * @return returns version.
     */
    public String getVersion()
    {
        return this.version;
    }

    /**
     * getModel returns the model of the device.
     * @return returns the model.
     */
    public String getModel()
    {
        return this.model;
    }

    /**
     * getManufacturer returns the manufacturer of the device.
     * @return returns the manufacturer.
     */
    public String getManufacturer()
    {
        return this.manufacturer;
    }

    /**
     * getIMEI returns the IMEI number of the device.
     * @return returns IMEI.
     */
    public String getIMEI()
    {
        return this.IMEI;
    }
    /**
     * getLineNumber returns the line number of the device.
     * @return returns LineNumber.
     */
    public String getLineNumber()
    {
        return this.LineNumber;
    }
    /**
     * getCarrierName returns the carrier name of the device.
     * @return returns LineNumber.
     */
    public String getCarrierName()
    {
        return this.carrierName;
    }

    /**
     * Overrides the toJSONOBJECT method so that it inserts all the variables of the deviceInfo
     * object to be sent as JSON Object
     * @return return a deviceInfo JSON object
     */
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

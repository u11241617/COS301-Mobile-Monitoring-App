package com.example.matt.myapplication;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

public class DeviceInfo {

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

        this.version = Build.VERSION.SDK;
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
}

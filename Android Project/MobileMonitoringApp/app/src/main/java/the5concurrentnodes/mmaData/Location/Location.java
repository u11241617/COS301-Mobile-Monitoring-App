package the5concurrentnodes.mmaData.Location;

import android.app.Activity;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import the5concurrentnodes.mmaData.DeviceApps.LocationTracker;
import the5concurrentnodes.mmaData.interfaces.ConvertToJSON;
import the5concurrentnodes.mobilemonitoringapp.LoginActivity;

public class Location implements ConvertToJSON
{
    private LocationTracker locationTracker;
    double latitude;
    double longitude;

    public Location(Context context)
    {
        this.locationTracker = new LocationTracker(context);
       this.latitude = locationTracker.getLatitude();
        this.longitude = locationTracker.getLongitude();
    }
    public double getLatitude()
    {
        return latitude;
    }
    public double getLongitude(){
        return longitude;
    }




    @Override
    public JSONObject toJSONObject() {

        JSONObject jsonObject = new JSONObject();

        try{

            jsonObject.put("latitude", latitude);
            jsonObject.put("longitude", longitude);

        }catch(JSONException e){}

        return jsonObject;
    }


}

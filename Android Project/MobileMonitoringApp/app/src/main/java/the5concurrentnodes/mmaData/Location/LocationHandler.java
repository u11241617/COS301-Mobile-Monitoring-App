package the5concurrentnodes.mmaData.Location;

import android.content.Context;
import android.database.Cursor;
import android.location.*;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import the5concurrentnodes.controllers.DataHandler;
import the5concurrentnodes.generic.Config;
import the5concurrentnodes.account.Utility;
import the5concurrentnodes.mmaData.call.CallConstants;
import the5concurrentnodes.mmaData.interfaces.LogHandler;


public class LocationHandler implements LocationListener, LogHandler {

    private Context context;

    public LocationHandler(Context context) {

        this.context = context;
    }

    @Override
    public void onLocationChanged(Location location) {

        try {

            Geocoder geo = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            JSONObject params = new JSONObject();
            params.put("latitude", Double.toString(location.getLatitude()));
            params.put("longitude", Double.toString(location.getLongitude()));


            if(!addresses.isEmpty()) {

                params.put("name", addresses.get(0).getAddressLine(0));
                params.put("locality", addresses.get(0).getAddressLine(1));
                params.put("postCode", addresses.get(0).getPostalCode());
                params.put("country", addresses.get(0).getCountryName());
                Log.d("Location", params.toString() + "");



            }

           // String  data  = Utility.encryptData(params.toString());

            submitLog(context, params);

        } catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void submitLog(Context context, JSONObject params) {

        String url = Config.REST_API + "/location";
        DataHandler.submitLog(context, url, params);
    }
}

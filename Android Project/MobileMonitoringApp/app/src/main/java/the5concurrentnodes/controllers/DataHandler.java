package the5concurrentnodes.controllers;

<<<<<<< HEAD
import android.content.Context;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
=======

import android.content.Context;
>>>>>>> af3afddafefa3572aee816e4377b5215ee18dd15
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
<<<<<<< HEAD

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import the5concurrentnodes.mobilemonitoringapp.MainActivity;

public class DataHandler {

    private DataHandler(){}

    private static DataHandler dataHandler;

    public static DataHandler getInstance() {

        if(dataHandler == null) {

            dataHandler = new DataHandler();
        }

        return dataHandler;
    }

    public void submitLog(String url, JSONObject jsonObject) {



        try {

            final Context context = MainActivity.class.newInstance().getBaseContext();

            VolleyJsonObjectRequest jsonObjectRequest = new VolleyJsonObjectRequest(context, Request.Method.POST,
                    url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {

                    boolean status = false;
                    String message = null;
                    try {
                        status = jsonObject.getBoolean("status");
                    }catch (JSONException e){}

                    if(status == true) {

                        UserSessionStorage sessionStorage = new UserSessionStorage(context);
                        sessionStorage.createSession(jsonObject.toString());
                    }else  {
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });


            RequestQueue requestQueue = VolleyRequestQueue.getRequestQueue();
            requestQueue.add(jsonObjectRequest);

        }catch(Exception e){}

=======
import org.json.JSONObject;

public class DataHandler {

    public static void submitLog(final Context context, String url, JSONObject params) {

        VolleyJsonObjectRequest jsonObjectRequest = new VolleyJsonObjectRequest(context, Request.Method.POST,
                url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        });

        RequestQueue requestQueue = VolleyRequestQueue.getRequestQueue();
        requestQueue.add(jsonObjectRequest);
>>>>>>> af3afddafefa3572aee816e4377b5215ee18dd15

    }
}

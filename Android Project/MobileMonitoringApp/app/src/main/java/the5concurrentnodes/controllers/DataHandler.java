package the5concurrentnodes.controllers;


import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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

    }
}

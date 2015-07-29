package the5concurrentnodes.controllers;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GASwipper Gcc on 7/14/2015.
 */
public class VolleyJsonObjectRequest extends JsonObjectRequest {

    private Context context;

    public VolleyJsonObjectRequest(Context context, int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);

        this.context = context;
    }
    
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {


        HashMap<String,String> headers = new HashMap<>();

        UserSessionStorage userSessionStorage = new UserSessionStorage(context);

        String token = userSessionStorage.getSessionValues();

        headers.put("Authorization", "Bearer "+ token);

        return headers;
    }

}

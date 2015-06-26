package the5concurrentnodes.account;


import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import the5concurrentnodes.controllers.VolleyRequestQueue;
import the5concurrentnodes.generic.Config;
import the5concurrentnodes.mobilemonitoringapp.SessionManager;

public class Register extends Account {

    private  String confirmPassword;

    public Register(Context c, String e, String p, String cp) {

        super(c, e,p);
        this.confirmPassword = cp;

        VolleyRequestQueue.init(getApplicationContext());
    }

    public boolean passwordsMatch() {

        return (confirmPassword.equals(getPassword())? true : false);
    }

    public void registerUser() {

        String url = String.format(Config.REST_API + "/account/register?email=%s&password=%s",
                getEmail(), getPassword());

        RequestQueue requestQueue = VolleyRequestQueue.getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,requestSuccessListener(), requestErrorListener());
        requestQueue.add(stringRequest);

    }

    private Response.Listener<String> requestSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    if(jsonObject.getBoolean("status")) {

                        Toast.makeText(getApplicationContext(), "Account registered!: " , Toast.LENGTH_LONG).show();
                    }else {

                        Toast.makeText(getApplicationContext(), "Provided email is already registered, please login" , Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e){}
            }
        };
    }


    private Response.ErrorListener requestErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Error while making request", Toast.LENGTH_LONG).show();
            }
        };
    }

}


package the5concurrentnodes.controllers;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyRequestQueue {

    private static RequestQueue requestQueue;

    private VolleyRequestQueue(){}

    public static void init(Context context) {

        requestQueue = Volley.newRequestQueue(context);
    }

    public static RequestQueue getRequestQueue() {

        return requestQueue;
    }

}

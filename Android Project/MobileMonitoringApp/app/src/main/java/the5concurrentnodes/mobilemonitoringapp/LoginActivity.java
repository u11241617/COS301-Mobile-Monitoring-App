package the5concurrentnodes.mobilemonitoringapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import the5concurrentnodes.account.Utility;
import the5concurrentnodes.controllers.DataPushServiceHandler;
import the5concurrentnodes.controllers.UserSessionStorage;
import the5concurrentnodes.controllers.VolleyRequestQueue;
import the5concurrentnodes.dialogs.LoginRegisterDialog;
import the5concurrentnodes.generic.Config;

import the5concurrentnodes.mmaData.DeviceApps.PushAppsInfo;

import the5concurrentnodes.mmaData.DataUsage.DataUsage;
import the5concurrentnodes.mmaData.DataUsage.DataUsageHandler;
import the5concurrentnodes.mmaData.DataUsage.PushDataUsage;

import the5concurrentnodes.mmaData.Location.Location;
import the5concurrentnodes.mmaData.deviceInfo.DeviceInfo;
import the5concurrentnodes.services.DataMonitorPushService;
import the5concurrentnodes.services.DataMonitorPushServiceHandler;


public class LoginActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private TextInputLayout emailWrapper;
    private TextInputLayout passwordWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.animator.activity_open_transition, R.animator.activity_close_scale);

        VolleyRequestQueue.init(getApplicationContext());

        emailWrapper = (TextInputLayout)  findViewById(R.id.loginEmailWrapper);
        passwordWrapper = (TextInputLayout) findViewById(R.id.loginPasswordWrapper);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(LoginActivity.this.getString(R.string.progress_signing_in));
        progressDialog.setCancelable(false);

        emailWrapper.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                emailWrapper.getEditText().setError(null);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordWrapper.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                passwordWrapper.getEditText().setError(null);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    /**
     * Consume REST api to login user account
     * @param email user email
     * @param password user password
     */
    public void loginUser(final String email, final String password) {

        String url = Config.REST_API + "/login";

        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("email", email);
            jsonParams.put("password", password);
            JSONObject deviceInfoParams = new JSONObject();
            DeviceInfo deviceInfo = new DeviceInfo(getApplicationContext());
            deviceInfoParams.put("model", deviceInfo.getModel());
            deviceInfoParams.put("make", deviceInfo.getManufacturer());
            deviceInfoParams.put("os", "Samsung");
            deviceInfoParams.put("network", deviceInfo.getCarrierName());
            deviceInfoParams.put("imeNumber", deviceInfo.getIMEI());

            jsonParams .put("deviceInfo", deviceInfoParams.toString());

        }catch (JSONException e){}

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url, jsonParams , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                progressDialog.dismiss();
                try {
                    if (jsonObject.getBoolean("status"))
                    {
                        UserSessionStorage userSessionStorage = new UserSessionStorage(getApplicationContext());
                        userSessionStorage.createSession(jsonObject.getString("access_token"));

                        LoginRegisterDialog dialog = new LoginRegisterDialog();
                        dialog.show(getFragmentManager(), null);

                        DataPushServiceHandler.getInstance().startService(getApplicationContext());
                        DataMonitorPushServiceHandler.getInstance().startService(getApplicationContext());

                        //new PushAppsInfo(getApplicationContext()).execute();


                    } else {
                        CoordinatorLayout coordinatorLayout =  (CoordinatorLayout) findViewById(R.id
                                .coordinatorLayout);
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout,  jsonObject.getString("message")
                                        , Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }


                } catch (JSONException e){}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
               // Toast.makeText(getApplicationContext(),
                        //LoginActivity.this.getResources().getString(R.string.request_unknown_error), Toast.LENGTH_LONG).show();

                CoordinatorLayout coordinatorLayout =(CoordinatorLayout) findViewById(R.id
                        .coordinatorLayout);
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, LoginActivity.this.getResources().getString(R.string.request_unknown_error)
                                , Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        RequestQueue requestQueue = VolleyRequestQueue.getRequestQueue();
        requestQueue.add(jsonObjectRequest);

    }


    @Override
    protected void onPause() {

        super.onPause();
        overridePendingTransition(R.animator.activity_open_scale, R.animator.activity_close_transition);
    }



    public void onLoginButtonClicked(View view) {

        String email = emailWrapper.getEditText().getText().toString();
        String password = passwordWrapper.getEditText().getText().toString();

        if (Utility.isEmpty(email) || Utility.isEmpty(password)) {

            if(Utility.isEmpty(email)) {

                emailWrapper.getEditText().setError(LoginActivity.this.getString(R.string.empty_input_error_message), null);
                emailWrapper.getEditText().requestFocus();

            }else if(Utility.isEmpty(password)) {

                passwordWrapper.getEditText().setError(LoginActivity.this.getString(R.string.empty_input_error_message), null);
                passwordWrapper.getEditText().requestFocus();
            }

        }else {

            if(!Utility.validateEmail(email)) {

                emailWrapper.getEditText().setError("Invalid email address", null);
                emailWrapper.getEditText().requestFocus();
            }else {

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(
                        Activity.INPUT_METHOD_SERVICE);

                inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                progressDialog.show();
                loginUser(email, password);
            }
        }
    }

    public void toSignUp(View view) {

        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

}

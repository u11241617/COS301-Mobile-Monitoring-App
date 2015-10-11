package the5concurrentnodes.mobilemonitoringapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import the5concurrentnodes.account.Register;
import the5concurrentnodes.account.Utility;
import the5concurrentnodes.controllers.DataPushServiceHandler;
import the5concurrentnodes.controllers.InternetConnectionDetector;
import the5concurrentnodes.controllers.UserSessionStorage;
import the5concurrentnodes.controllers.VolleyRequestQueue;
import the5concurrentnodes.dialogs.LoginRegisterDialog;
import the5concurrentnodes.generic.Config;
import the5concurrentnodes.mmaData.AppInfo.AppInfo;
import the5concurrentnodes.mmaData.AppInfo.ProcessAppInfo;
import the5concurrentnodes.mmaData.Browser.BrowserHandler;
import the5concurrentnodes.mmaData.Browser.BrowserObserver;
import the5concurrentnodes.mmaData.deviceInfo.DeviceInfo;


public class RegisterActivity extends ActionBarActivity {

    private boolean passwordVisible;
    private Button registerButton;
    private EditText registerEmail;
    private EditText registerPassword;
    private EditText confirmRegisterPassword;
    private ImageButton passwordOkImageButton;
    private ProgressDialog progressDialog;

    private TextInputLayout emailWrapper;
    private TextInputLayout passwordWrapper;
    private TextInputLayout confirmPasswordWrapper;
    boolean pError = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        overridePendingTransition(R.animator.activity_open_transition, R.animator.activity_close_scale);

        //Initialize RequestQueue
        VolleyRequestQueue.init(getApplicationContext());

        emailWrapper = (TextInputLayout)  findViewById(R.id.registerEmailWrapper);
        passwordWrapper = (TextInputLayout) findViewById(R.id.registerPasswordWrapper);
        confirmPasswordWrapper = (TextInputLayout) findViewById(R.id.confirmPasswordWrapper);


        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        emailWrapper.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                emailWrapper.setError(null);
                passwordWrapper.setError(null);

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


                    passwordWrapper.getEditText().setError(RegisterActivity.this.getString(R.string.password_hint), null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        confirmPasswordWrapper.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                confirmPasswordWrapper.setError(null);
                passwordWrapper.setError(null);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage(RegisterActivity.this.getString(R.string.progress_signing_up));
    }


    private void updateRegisterButtonState() {

        if((Utility.validateEmail(registerEmail.getText().toString())
                && Utility.validatePassword(registerPassword.getText().toString()))
                && (registerPassword.getText().toString().equals(confirmRegisterPassword.getText().toString()))) {

            registerButton.setEnabled(true);

        }else {

            registerButton.setEnabled(false);
        }
    }

    private void updatePasswordOkImageButtonState() {

        if (confirmRegisterPassword.getText().toString()
                .equals(registerPassword.getText().toString())
                && !Utility.isEmpty(registerPassword.getText().toString())) {

            passwordOkImageButton.setVisibility(View.VISIBLE);
            passwordOkImageButton.setImageResource(R.mipmap.ic_checkbox_marked_green);

        } else {

            passwordOkImageButton.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * Consume REST api to register new user account
     * @param email user email
     * @param password user password
     */
    public void registerUser(final String email, final String password) {

        String url = Config.REST_API + "/register";

        JSONObject jsonParams = new JSONObject();

        try{

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

        }catch(JSONException e){}



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url, jsonParams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                progressDialog.dismiss();

                try{

                    if(jsonObject.getBoolean("status")) {

                        UserSessionStorage userSessionStorage = new UserSessionStorage(getApplicationContext());
                        userSessionStorage.createSession(jsonObject.getString("access_token"));

                        LoginRegisterDialog dialog = new LoginRegisterDialog();
                        dialog.show(getFragmentManager(), null);

                        DataPushServiceHandler.getInstance().startService(getApplicationContext());

                    }else {

                        Toast.makeText(getApplicationContext(),
                                jsonObject.getString("message"),
                                Toast.LENGTH_LONG).show();
                    }

                }catch(JSONException e){}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),
                        RegisterActivity.this.getResources().getString(R.string.request_unknown_error),
                        Toast.LENGTH_LONG).show();
            }
        });


        RequestQueue requestQueue = VolleyRequestQueue.getRequestQueue();

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        
        requestQueue.add(jsonObjectRequest);

    }

    @Override
    protected void onPause() {

        super.onPause();
        overridePendingTransition(R.animator.activity_open_scale, R.animator.activity_close_transition);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }


    public void onRegisterButtonClicked(View view) {

        String email = emailWrapper.getEditText().getText().toString();
        String password = passwordWrapper.getEditText().getText().toString();
        String cPassword = confirmPasswordWrapper.getEditText().getText().toString();

        if (Utility.isEmpty(email) || Utility.isEmpty(password) || Utility.isEmpty(cPassword)) {

            if(Utility.isEmpty(email)) {

                emailWrapper.setError(RegisterActivity.this.getString(R.string.empty_input_error_message));

            }else if(Utility.isEmpty(password)) {

                passwordWrapper.setError(RegisterActivity.this.getString(R.string.empty_input_error_message));
            }else if(Utility.isEmpty(cPassword)) {

                confirmPasswordWrapper.setError(RegisterActivity.this.getString(R.string.empty_input_error_message));
            }

        }else {

            if(!Utility.validateEmail(email)) {

                emailWrapper.setError("Invalid email address");

            }else if(password.length() < 6) {
                passwordWrapper.setError(RegisterActivity.this.getString(R.string.password_hint));
            } else if(!password.equals(cPassword)) {

                confirmPasswordWrapper.setError("Passwords do not match");

            }else {

                registerUser(email, password);
            }
        }
    }


    public void toSignIn(View view) {

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}


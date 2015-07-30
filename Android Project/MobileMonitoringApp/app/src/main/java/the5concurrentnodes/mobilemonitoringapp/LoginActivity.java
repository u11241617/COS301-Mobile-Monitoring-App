package the5concurrentnodes.mobilemonitoringapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import the5concurrentnodes.account.Utility;
import the5concurrentnodes.controllers.InternetConnectionDetector;
import the5concurrentnodes.controllers.UserSessionStorage;
import the5concurrentnodes.controllers.VolleyRequestQueue;
import the5concurrentnodes.dialogs.LoginRegisterDialog;
import the5concurrentnodes.dialogs.RecoverPasswordDialog;
import the5concurrentnodes.generic.Config;
import the5concurrentnodes.mmaData.deviceInfo.DeviceInfo;


public class LoginActivity extends Activity {

    private boolean passwordVisible;
    private SessionManager sessionManager;
    private Button loginButton;
    private EditText loginEmail;
    private EditText loginPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.animator.activity_open_transition, R.animator.activity_close_scale);

        final Button registerButton;
        final ImageButton showPasswordButton;


        passwordVisible = false;
        sessionManager = new SessionManager(getApplicationContext());

        loginEmail = (EditText) findViewById(R.id.login_email);
        loginPassword = (EditText) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.login_button);
        registerButton = (Button) findViewById(R.id.register_button);
        showPasswordButton = (ImageButton) findViewById(R.id.show_password_button);
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage(LoginActivity.this.getString(R.string.progress_signing_in));
        progressDialog.setCancelable(false);

        loginEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                updateLoginInButtonState();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        loginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateLoginInButtonState();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (sessionManager.isSessionActive())
        {
            Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(intent);
            finish();
        }

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String userEmail = loginEmail.getText().toString();
                String userPassword = loginPassword.getText().toString();



                InternetConnectionDetector internetConnectionDetector = new InternetConnectionDetector(getApplicationContext());
                if (internetConnectionDetector.isConnectedToInternet() == false) {

                    Toast.makeText(getApplicationContext(), LoginActivity.this.getResources().getString(R.string.request_unknown_error), Toast.LENGTH_LONG).show();
                }else {

                    progressDialog.show();
                    loginUser(userEmail, userPassword);
                }

            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if(sessionManager.isSessionActive()) {

            Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(intent);
            finish();
        }

        showPasswordButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(passwordVisible) {

                    loginPassword.setInputType(129);
                    passwordVisible = false;
                    showPasswordButton.setImageResource(R.mipmap.ic_eye);

                } else {
                    loginPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordVisible = true;
                    showPasswordButton.setImageResource(R.mipmap.ic_eye_off);
                }

            }
        });
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


                    } else
                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"),Toast.LENGTH_LONG).show();


                } catch (JSONException e){}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),
                        LoginActivity.this.getResources().getString(R.string.request_unknown_error), Toast.LENGTH_LONG).show();
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

    /**
     * Updates login button state
     */
    private void updateLoginInButtonState() {
        loginButton.setEnabled
                (Utility.validateEmail(loginEmail.getText().toString()) &&
                        Utility.validatePassword(loginPassword.getText().toString()));
    }

    public void recoverPassword(View view) {

        RecoverPasswordDialog dialog = new RecoverPasswordDialog();
        dialog.show(getFragmentManager(), null);
    }

}

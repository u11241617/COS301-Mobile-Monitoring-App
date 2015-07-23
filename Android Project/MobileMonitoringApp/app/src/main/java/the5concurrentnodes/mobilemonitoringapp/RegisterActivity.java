package the5concurrentnodes.mobilemonitoringapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import the5concurrentnodes.account.Register;
import the5concurrentnodes.account.Utility;
import the5concurrentnodes.controllers.InternetConnectionDetector;
import the5concurrentnodes.controllers.UserSessionStorage;
import the5concurrentnodes.controllers.VolleyRequestQueue;
import the5concurrentnodes.generic.Config;
import the5concurrentnodes.models.DeviceInfo;


public class RegisterActivity extends Activity {

    private boolean passwordVisible;
    private Button registerButton;
    private EditText registerEmail;
    private EditText registerPassword;
    private EditText confirmRegisterPassword;
    private ImageButton passwordOkImageButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        overridePendingTransition(R.animator.activity_open_transition, R.animator.activity_close_scale);

        //Initialize RequestQueue
        VolleyRequestQueue.init(getApplicationContext());


        final ShowcaseView showcaseView;
        final Target target;
        final TextView registerTitle;
        final ImageButton showPasswordImageButton;
        final Button loginButton;
        final Typeface bookmarkOldStyle;

        passwordVisible = false;

        registerEmail = (EditText) findViewById(R.id.register_email);
        registerPassword = (EditText) findViewById(R.id.register_password);
        confirmRegisterPassword = (EditText) findViewById(R.id.confirm_register_password);
        showPasswordImageButton = (ImageButton) findViewById(R.id.show_password_button);
        passwordOkImageButton = (ImageButton) findViewById(R.id.password_ok);
        loginButton = (Button) findViewById(R.id.login_button);
        registerButton = (Button) findViewById(R.id.register_button);
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage(RegisterActivity.this.getString(R.string.progress_signing_up));

        target = new ViewTarget(R.id.icon, this);

            showcaseView = new ShowcaseView.Builder(this)
                    .setTarget(target)
                    .setContentTitle("New Account")
                    .setContentText("Please fill in all the fields to create new MMA account.\n  Password must contain: \n - at least 1 digit\n - 1 lowercase letter\n - 1 uppercase letter \n - must be at least 6 characters long.")
                    .setStyle(R.style.CustomShowViewStyle)
                    .build();


        registerEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateRegisterButtonState();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

            //Setup password EditText event listener
            registerPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    updatePasswordOkImageButtonState();
                    updateRegisterButtonState();

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            confirmRegisterPassword.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable editable) {
                }

                @Override
                public void beforeTextChanged(CharSequence charSequence, int start,
                                              int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start,
                                          int before, int count) {
                    updatePasswordOkImageButtonState();
                    updateRegisterButtonState();
                }
            });

            //Setup login button (link) event listener
            loginButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            //Setup register button event listener
            registerButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    String email = registerEmail.getText().toString();
                    String password = registerPassword.getText().toString();

                    InternetConnectionDetector internetConnectionDetector = new InternetConnectionDetector(getApplicationContext());

                    if (internetConnectionDetector.isConnectedToInternet() == false) {
                        Toast.makeText(getApplicationContext(), "You are not connected to the internet, check your internet connection.", Toast.LENGTH_LONG).show();
                    }else{

                        progressDialog.show();
                        registerUser(email, password);
                    }
                }
            });

            showPasswordImageButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (passwordVisible) {

                        registerPassword.setInputType(129);
                        passwordVisible = false;
                        showPasswordImageButton.setImageResource(R.mipmap.ic_eye);

                    } else {

                        registerPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        passwordVisible = true;
                        showPasswordImageButton.setImageResource(R.mipmap.ic_eye_off);
                    }

                }
            });
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

            jsonParams.put("deviceInfo", deviceInfoParams.toString());


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
        requestQueue.add(jsonObjectRequest);

    }

    @Override
    protected void onPause() {

        super.onPause();
        overridePendingTransition(R.animator.activity_open_scale, R.animator.activity_close_transition);
    }

    /**
     * Handle successful user registration responses
     * @return request response
     */
    private Response.Listener<String> requestSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    //Account successfully created
                    if(jsonObject.getBoolean("status")) {

                        Toast.makeText(getApplicationContext(), "Account registered!: " , Toast.LENGTH_LONG).show();
                    }else { //Provided email already registered

                        Toast.makeText(getApplicationContext(),
                                RegisterActivity.this.getResources().getString(R.string.request_unknown_error),
                                Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e){}
            }
        };
    }


    /**
     * Handle errors occurred while trying to register a new user account
     * @return request response
     */
    private Response.ErrorListener requestErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Error while making request", Toast.LENGTH_LONG).show();
            }
        };
    }
}


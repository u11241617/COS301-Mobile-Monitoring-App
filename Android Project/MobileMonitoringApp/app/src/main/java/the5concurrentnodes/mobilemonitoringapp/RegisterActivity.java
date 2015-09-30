package the5concurrentnodes.mobilemonitoringapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import the5concurrentnodes.account.Utility;
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

        final ImageButton showPasswordImageButton;
        final Button loginButton;

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


       // ProcessAppInfo processAppInfo = new ProcessAppInfo(this.getApplicationContext());
        //processAppInfo.doInBackground();
       /* AppInfo appInfo = new AppInfo();
        ArrayList<AppInfo> current = appInfo.getListOfInstalledApp(getApplicationContext());
        Log.d("no of installed apps", String.valueOf(current.size()));
        for (int i = 0; i < current.size(); i++) {
            Log.d("installed apps ****", "name: " + current.get(i).getName() + " Package name: " + current.get(i).getPackageName()
                    + "Version Name: " + current.get(i).getVersionName() + "Version Code: " + current.get(i).getVersionCode() +
                    "Icon:" + current.get(i).getIcon());

                ImageView imageView = new ImageView(getApplicationContext());
                //Drawable d = getPackageManager().getApplicationIcon("com.AdhamiPiranJhandukhel.com");
                imageView.setImageDrawable(current.get(i).getIcon());
        }*/

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
                    if (!Utility.validatePassword(registerPassword.getText().toString()))
                    {
                        registerPassword.setError(RegisterActivity.this.getResources().getString(R.string.help_dialog_content) , null);
                    }

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

                    if (!internetConnectionDetector.isConnectedToInternet()) {

                        Toast.makeText(getApplicationContext(),
                                RegisterActivity.this.getString(R.string.request_unknown_error), Toast.LENGTH_LONG).show();
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


            jsonParams.put("deviceInfo",deviceInfo.toJSONObject().toString());



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


}


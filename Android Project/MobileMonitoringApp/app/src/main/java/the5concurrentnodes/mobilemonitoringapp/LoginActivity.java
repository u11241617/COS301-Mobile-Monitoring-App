package the5concurrentnodes.mobilemonitoringapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
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
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import the5concurrentnodes.account.Utility;
import the5concurrentnodes.controllers.InternetConnectionDetector;
import the5concurrentnodes.controllers.VolleyRequestQueue;
import the5concurrentnodes.generic.Config;


public class LoginActivity extends Activity {

    private boolean passwordVisible;
    private SessionManager sessionManager;
    private Button loginButton;
    private EditText loginEmail;
    private EditText loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.animator.activity_open_transition, R.animator.activity_close_scale);

        final Typeface bookmarkOldStyle = Typeface.createFromAsset(getAssets(), "fonts/BOOKOS.TTF");


        final Button registerButton;
        final ImageButton showPasswordButton;



        final TextView logonTitle = (TextView) findViewById(R.id.login_title);

        passwordVisible = false;
        sessionManager = new SessionManager(getApplicationContext());

        loginEmail = (EditText) findViewById(R.id.login_email);
        loginPassword = (EditText) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.login_button);
        registerButton = (Button) findViewById(R.id.register_button);
        showPasswordButton = (ImageButton) findViewById(R.id.show_password_button);

        logonTitle.setTypeface(bookmarkOldStyle);

        loginButton.setTypeface(bookmarkOldStyle);
        registerButton.setTypeface(bookmarkOldStyle);

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

            String email = loginEmail.getText().toString();
            String password = loginPassword.getText().toString();

            @Override
            public void onClick(View v) {

                String userEmail = loginEmail.getText().toString();
                String userPassword = loginPassword.getText().toString();

                if (Utility.isEmpty(userEmail) || Utility.isEmpty(userPassword)) {

                    if (Utility.isEmpty(userEmail)) {

                        loginEmail.requestFocus();
                        loginEmail.setError(
                                LoginActivity.this.getString(R.string.empty_input_error_message));
                    } else if (Utility.isEmpty(userPassword)) {

                        loginPassword.requestFocus();
                        loginPassword.setError(
                                LoginActivity.this.getString(R.string.empty_input_error_message));
                    }

                } else
                {
                    InternetConnectionDetector internetConnectionDetector = new InternetConnectionDetector(getApplicationContext());
                    if (internetConnectionDetector.isConnectedToInternet() == false)
                        Toast.makeText(getApplicationContext(), "You are not connected to the internet, check your internet connection.", Toast.LENGTH_LONG).show();
                    else
                        loginUser(email, password);

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

    @Override
    protected void onPause() {

        super.onPause();
        overridePendingTransition(R.animator.activity_open_scale, R.animator.activity_close_transition);
    }


    private void updateLoginInButtonState() {
        loginButton.setEnabled
                (Utility.validateEmail(loginEmail.getText().toString()) &&
                        Utility.validatePassword(loginPassword.getText().toString()));
    }
    /**
     * Consume REST api to login user
     * @param email user email
     * @param password user password
     */
    public void loginUser(final String email, final String password) {

        String url = String.format(Config.REST_API + "/account/login?email=%s&password=%s",
                email, password);

        RequestQueue requestQueue = VolleyRequestQueue.getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,requestSuccessListener(), requestErrorListener());
        requestQueue.add(stringRequest);

    }

    /**
     * Handle successful user login responses
     * @return request response
     */
    private Response.Listener<String> requestSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if (!error)
                    {
                        //successful login
                        Toast.makeText(getApplicationContext(), "Login successful " , Toast.LENGTH_LONG).show();
                        sessionManager.createSession(true);
                        Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                    //error in login
                        String errorMsg = jsonObject.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
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

                Toast.makeText(getApplicationContext(), "Error while making request.", Toast.LENGTH_LONG).show();
            }
        };
    }
}

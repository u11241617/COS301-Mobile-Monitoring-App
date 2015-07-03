package the5concurrentnodes.mobilemonitoringapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import org.json.JSONException;
import org.json.JSONObject;

import the5concurrentnodes.account.Register;
import the5concurrentnodes.account.Utility;
import the5concurrentnodes.controllers.VolleyRequestQueue;
import the5concurrentnodes.generic.Config;


public class RegisterActivity extends Activity {

    private boolean passwordVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        overridePendingTransition(R.animator.activity_open_transition, R.animator.activity_close_scale);

        //Initialize RequestQueue
        VolleyRequestQueue.init(getApplicationContext());


        final ShowcaseView showcaseView;
        final Target target;
        final EditText registerEmail;
        final EditText registerPassword;
        final EditText confirmRegisterPassword;
        final TextView registerTitle;
        final ImageButton showPasswordImageButton;
        final ImageButton passwordOkImageButton;
        final Button loginButton;
        final Button registerButton;
        final Typeface calibriFont;
        final Typeface bookmarkOldStyle;

        passwordVisible = false;

        registerEmail = (EditText) findViewById(R.id.register_email);
        registerPassword = (EditText) findViewById(R.id.register_password);
        confirmRegisterPassword = (EditText) findViewById(R.id.confirm_register_password);
        showPasswordImageButton = (ImageButton) findViewById(R.id.show_password_button);
        passwordOkImageButton= (ImageButton) findViewById(R.id.password_ok);
        loginButton = (Button) findViewById(R.id.login_button);
        registerButton = (Button) findViewById(R.id.register_button);
        registerTitle = (TextView) findViewById(R.id.register_title);

        calibriFont = Typeface.createFromAsset(getAssets(), "fonts/calibri.ttf");
        bookmarkOldStyle = Typeface.createFromAsset(getAssets(), "fonts/BOOKOS.TTF");

        //Set fonts
        registerEmail.setTypeface(calibriFont);
        registerPassword.setTypeface(calibriFont);
        confirmRegisterPassword.setTypeface(calibriFont);
        loginButton.setTypeface(bookmarkOldStyle);
        registerButton.setTypeface(bookmarkOldStyle);
        registerTitle.setTypeface(bookmarkOldStyle);

        target = new ViewTarget(R.id.icon, this);
        showcaseView = new ShowcaseView.Builder(this)
                .setTarget(target)
                .setContentTitle("New Account")
                .setContentText("Please fill in all the fields to create new MMA account.\n  Password must contain: \n - at least 1 digit\n - 1 lowercase letter\n - 1 uppercase letter \n - must be at least 6 characters long.")
                .setStyle(R.style.CustomShowViewStyle)
                .build();

        //Setup password EditText event listener
        confirmRegisterPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable editable) {}

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start,
                                      int before, int count) {

                if(confirmRegisterPassword.getText().toString()
                        .equals(registerPassword.getText().toString())) {

                    passwordOkImageButton.setVisibility(View.VISIBLE);
                    passwordOkImageButton.setImageResource(R.mipmap.ic_checkbox_marked_green);
                }else {

                    passwordOkImageButton.setVisibility(View.INVISIBLE);
                }
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
                String cPassword = confirmRegisterPassword.getText().toString();

                if (Utility.isEmpty(email) || Utility.isEmpty(password) || Utility.isEmpty(cPassword)) {

                    if (the5concurrentnodes.account.Utility.isEmpty(email)) {

                        registerEmail.requestFocus();
                        registerEmail.setError(
                                RegisterActivity.this.getString(R.string.empty_input_error_message));

                    } else if (the5concurrentnodes.account.Utility.isEmpty(password)) {

                        registerPassword.requestFocus();
                        registerPassword.setError(
                                RegisterActivity.this.getString(R.string.empty_input_error_message));

                    } else if (Utility.isEmpty(cPassword)) {

                        confirmRegisterPassword.requestFocus();
                        confirmRegisterPassword.setError(
                                RegisterActivity.this.getString(R.string.empty_input_error_message));
                    }
                } else {

                    if (Utility.validateEmail(email)) {

                        if (Utility.validatePassword(password)) {

                            if (password.equals(cPassword)) {

                                //make user registration request
                                registerUser(email, password);

                            } else {

                                Toast.makeText(getApplicationContext(), "Please make sure both password match", Toast.LENGTH_LONG).show();
                            }

                        } else {


                            Toast.makeText(getApplicationContext(), "Password must contain at least 1 digit, 1 lower case latter, 1 uppercase letter and be at least 6 characters long", Toast.LENGTH_LONG).show();
                        }

                    } else {

                        Toast.makeText(getApplicationContext(), "Please enter a valid email", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        showPasswordImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (passwordVisible) {

                    registerPassword.setInputType(129);
                    passwordVisible = false;
                    registerPassword.setTypeface(calibriFont);
                    showPasswordImageButton.setImageResource(R.mipmap.ic_eye);

                } else {

                    registerPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordVisible = true;
                    registerPassword.setTypeface(calibriFont);
                    showPasswordImageButton.setImageResource(R.mipmap.ic_eye_off);
                }

            }
        });
    }

    /**
     * Consume REST api to register new user account
     * @param email user email
     * @param password user password
     */
    public void registerUser(final String email, final String password) {

        String url = String.format(Config.REST_API + "/account/register?email=%s&password=%s",
                email, password);

        RequestQueue requestQueue = VolleyRequestQueue.getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,requestSuccessListener(), requestErrorListener());
        requestQueue.add(stringRequest);

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

                        Toast.makeText(getApplicationContext(), "Provided email is already registered, please login" , Toast.LENGTH_LONG).show();
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


package the5concurrentnodes.mobilemonitoringapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import the5concurrentnodes.account.Utility;


public class LoginActivity extends Activity {

    private boolean passwordVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.animator.activity_open_transition, R.animator.activity_close_scale);

        final Typeface calibriFont = Typeface.createFromAsset(getAssets(), "fonts/calibri.ttf");
        final Typeface bookmarkOldStyle = Typeface.createFromAsset(getAssets(), "fonts/BOOKOS.TTF");

        final Button loginButton;
        final Button registerButton;
        final ImageButton showPasswordButton;
        final EditText loginEmail;
        final EditText loginPassword;
        final SessionManager sessionManager;

        final TextView logonTitle = (TextView) findViewById(R.id.login_title);

        passwordVisible = false;
        sessionManager = new SessionManager(getApplicationContext());

        loginEmail = (EditText) findViewById(R.id.login_email);
        loginPassword = (EditText) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.login_button);
        registerButton = (Button) findViewById(R.id.register_button);
        showPasswordButton = (ImageButton) findViewById(R.id.show_password_button);

        logonTitle.setTypeface(bookmarkOldStyle);
        loginEmail.setTypeface(calibriFont);
        loginPassword.setTypeface(calibriFont);
        loginButton.setTypeface(bookmarkOldStyle);
        registerButton.setTypeface(bookmarkOldStyle);

        loginButton.setOnClickListener(new View.OnClickListener() {

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

                } else {

                    sessionManager.createSession(true);

                    Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                    startActivity(intent);
                    finish();
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
                    loginPassword.setTypeface(calibriFont);
                    showPasswordButton.setImageResource(R.mipmap.ic_eye);

                } else {
                    loginPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordVisible = true;
                    loginPassword.setTypeface(calibriFont);
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

}

package the5concurrentnodes.mobilemonitoringapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import the5concurrentnodes.account.Utility;


public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.animator.activity_open_transition, R.animator.activity_close_scale);

        final Typeface arialRoundedBoldFont = Typeface.createFromAsset(getAssets(), "fonts/Arial_Rounded_Bold.TTF");
        final Typeface calibriFont = Typeface.createFromAsset(getAssets(), "fonts/calibri.ttf");

        final Button loginButton;
        final Button registerButton;
        final EditText loginEmail;
        final EditText loginPassword;
        final SessionManager sessionManager;

        final TextView logonTitle = (TextView) findViewById(R.id.login_title);


        logonTitle.setTypeface(arialRoundedBoldFont);


        sessionManager = new SessionManager(getApplicationContext());

        loginEmail = (EditText) findViewById(R.id.login_email);
        loginPassword = (EditText) findViewById(R.id.login_password);

        loginEmail.setTypeface(calibriFont);
        loginPassword.setTypeface(calibriFont);

        loginButton = (Button) findViewById(R.id.login_button);
        registerButton = (Button) findViewById(R.id.register_button);

        loginButton.setTypeface(arialRoundedBoldFont);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String userEmail = loginEmail.getText().toString();
                String userPassword = loginPassword.getText().toString();

                if(Utility.isEmpty(userEmail) || Utility.isEmpty(userPassword)) {

                    Toast.makeText(getApplicationContext(), "Empty email or password", Toast.LENGTH_LONG).show();

                }else {

                    sessionManager.createSession(true);

                    Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

        registerButton.setTypeface(arialRoundedBoldFont);
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if(sessionManager.isSessionActive()) {

            Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onPause() {

        super.onPause();
        overridePendingTransition(R.animator.activity_open_scale, R.animator.activity_close_transition);
    }

}

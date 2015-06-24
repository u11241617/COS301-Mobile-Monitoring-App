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


public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        overridePendingTransition(R.animator.activity_open_transition, R.animator.activity_close_scale);

        final Typeface arialRoundedBoldFont = Typeface.createFromAsset(getAssets(), "fonts/Arial_Rounded_Bold.TTF");
        final Typeface calibriFont = Typeface.createFromAsset(getAssets(), "fonts/calibri.ttf");

        final EditText registerEmail;
        final EditText registerPassword;
        final EditText confirmRegisterPassword;
        final Button loginButton;
        final Button registerButton;
        final SessionManager sessionManager;

        final TextView registerTitle = (TextView) findViewById(R.id.register_title);

        registerTitle.setTypeface(arialRoundedBoldFont);


        sessionManager = new SessionManager(getApplicationContext());

        registerEmail = (EditText) findViewById(R.id.register_email);
        registerPassword = (EditText) findViewById(R.id.register_password);
        confirmRegisterPassword = (EditText) findViewById(R.id.confirm_register_password);
        loginButton = (Button) findViewById(R.id.login_button);
        registerButton = (Button) findViewById(R.id.register_button);

        registerEmail.setTypeface(calibriFont);
        registerPassword.setTypeface(calibriFont);
        confirmRegisterPassword.setTypeface(calibriFont);
        loginButton.setTypeface(arialRoundedBoldFont);
        registerButton.setTypeface(arialRoundedBoldFont);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                sessionManager.createSession(true);

                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);



                finish();
            }
        });
    }

    @Override
    protected void onPause() {

        super.onPause();
        overridePendingTransition(R.animator.activity_open_scale, R.animator.activity_close_transition);
    }

}

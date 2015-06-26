package the5concurrentnodes.mobilemonitoringapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import the5concurrentnodes.account.Register;
import the5concurrentnodes.account.Utility;


public class RegisterActivity extends Activity {

    private ShowcaseView showcaseView;
    private Target target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        overridePendingTransition(R.animator.activity_open_transition, R.animator.activity_close_scale);

        target = new ViewTarget(R.id.logo, this);
        showcaseView = new ShowcaseView.Builder(this)
                .setTarget(target)
                .setContentTitle("New Account")
                .setContentText("Please fill in all the fields to create new MMA account. Password must contain at least 1 digit, 1 lowercase letter, 1 uppercase letter and must be at least 6 characters long.")
                .setStyle(R.style.CustomShowViewStyle)
                .build();


        final EditText registerEmail;
        final EditText registerPassword;
        final EditText confirmRegisterPassword;
        final TextView registerTitle;
        final Button loginButton;
        final Button registerButton;
        final SessionManager sessionManager;
        final Typeface arialRoundedBoldFont;
        final Typeface calibriFont;


        registerEmail = (EditText) findViewById(R.id.register_email);
        registerPassword = (EditText) findViewById(R.id.register_password);
        confirmRegisterPassword = (EditText) findViewById(R.id.confirm_register_password);
        loginButton = (Button) findViewById(R.id.login_button);
        registerButton = (Button) findViewById(R.id.register_button);
        sessionManager = new SessionManager(getApplicationContext());
        registerTitle = (TextView) findViewById(R.id.register_title);

        arialRoundedBoldFont = Typeface.createFromAsset(getAssets(), "fonts/Arial_Rounded_Bold.TTF");
        calibriFont = Typeface.createFromAsset(getAssets(), "fonts/calibri.ttf");

        //Set fonts
        registerEmail.setTypeface(calibriFont);
        registerPassword.setTypeface(calibriFont);
        confirmRegisterPassword.setTypeface(calibriFont);
        loginButton.setTypeface(arialRoundedBoldFont);
        registerButton.setTypeface(arialRoundedBoldFont);
        registerTitle.setTypeface(arialRoundedBoldFont);

        //Setup login button event listener
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Setup login button event listener
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String email = registerEmail.getText().toString();
                String password = registerPassword.getText().toString();
                String cPassword = confirmRegisterPassword.getText().toString();

                if(Utility.isEmpty(email) || Utility.isEmpty(password) || Utility.isEmpty(cPassword)) {

                    Toast.makeText(getApplicationContext(), "Please fill in all input fields", Toast.LENGTH_LONG).show();
                }else {

                    if(Utility.validateEmail(email)) {

                        if(Utility.validatePassword(password)) {

                            Register register = new Register(getApplicationContext(), email, password, cPassword);

                            if(register.passwordsMatch()) {

                                register.registerUser();

                            }else {

                                Toast.makeText(getApplicationContext(), "Please make sure both password match", Toast.LENGTH_LONG).show();
                            }

                        }else {


                            Toast.makeText(getApplicationContext(), "Password must contain at least 1 digit, 1 lower case latter, 1 uppercase letter and be at least 6 characters long", Toast.LENGTH_LONG).show();
                        }

                    }else {
                        Toast.makeText(getApplicationContext(), "Please enter a valid email", Toast.LENGTH_LONG).show();
                    }
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

package the5concurrentnodes.mobilemonitoringapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CheckBox acceptTermsCheckbox;
        acceptTermsCheckbox = (CheckBox) findViewById(R.id.accept_terms_checkbox);

        final Button nextButton;
        nextButton = (Button) findViewById(R.id.next_button);

        acceptTermsCheckbox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(acceptTermsCheckbox.isChecked()) {

                    nextButton.setEnabled(true);

                }else {

                    nextButton.setEnabled(false);
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        SessionManager sessionManager;
        sessionManager = new SessionManager(getApplicationContext());

        if(sessionManager.isSessionActive()) {

            Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(intent);
            finish();
        }


    }

}

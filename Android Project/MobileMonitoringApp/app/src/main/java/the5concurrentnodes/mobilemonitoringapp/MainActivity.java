package the5concurrentnodes.mobilemonitoringapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import the5concurrentnodes.controllers.DataPushServiceHandler;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WebView webView;
        final CheckBox acceptTermsCheckbox;
        final Button nextButton;

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_action_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        webView = (WebView) findViewById(R.id.web_view);

       // nextButton = (Button) findViewById(R.id.next_button);


        webView.loadUrl("file:///android_asset/pages/terms.html");

       /* acceptTermsCheckbox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //if(acceptTermsCheckbox.isChecked()) {
                    
                    nextButton.setEnabled(true);

               // }else {

               //     nextButton.setEnabled(false);
               // }
            }
        });*/

       /* nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });*/

        SessionManager sessionManager;
        sessionManager = new SessionManager(getApplicationContext());

        if(sessionManager.isSessionActive()) {

            Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(intent);
            finish();
        }

        DataPushServiceHandler.getInstance().startService(getApplicationContext());
    }

}

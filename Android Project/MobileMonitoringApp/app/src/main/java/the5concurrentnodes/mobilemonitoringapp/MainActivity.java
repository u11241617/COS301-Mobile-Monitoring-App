package the5concurrentnodes.mobilemonitoringapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Typeface calibriFont;
        final Typeface arialRoundedBoldFont;
        final Typeface bookmarkOldStyle;
        final TextView termsTitle;
        final WebView webView;
        final CheckBox acceptTermsCheckbox;
        final Button nextButton;

        arialRoundedBoldFont = Typeface.createFromAsset(getAssets(), "fonts/Arial_Rounded_Bold.TTF");
        calibriFont = Typeface.createFromAsset(getAssets(), "fonts/calibri.ttf");
        bookmarkOldStyle = Typeface.createFromAsset(getAssets(), "fonts/BOOKOS.TTF");
        termsTitle =(TextView) findViewById(R.id.terms_title);
        webView = (WebView) findViewById(R.id.web_view);
        acceptTermsCheckbox = (CheckBox) findViewById(R.id.accept_terms_checkbox);
        nextButton = (Button) findViewById(R.id.next_button);

        //termsTitle.setTypeface(arialRoundedBoldFont);
        acceptTermsCheckbox.setTypeface(calibriFont);
        nextButton.setTypeface(bookmarkOldStyle);

        webView.loadUrl("file:///android_asset/pages/terms.html");

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

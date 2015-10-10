package the5concurrentnodes.mobilemonitoringapp;

import android.content.Intent;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;


public class WelcomeActivity extends AppIntro {

    @Override
    public void init(Bundle bundle) {

        addSlide(IntroFragment.newInstance(R.layout.intro_slide_1));
        addSlide(IntroFragment.newInstance(R.layout.intro_slide_2));
        addSlide(IntroFragment.newInstance(R.layout.intro_slide_3));
        addSlide(IntroFragment.newInstance(R.layout.intro_slide_4));
    }

    @Override
    public void onSkipPressed() {

        goToMainActivity();
    }

    @Override
    public void onDonePressed() {

        goToMainActivity();

    }

    private void goToMainActivity() {

        finish();
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }
}

package the5concurrentnodes.splash;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

import the5concurrentnodes.mobilemonitoringapp.MainActivity;
import the5concurrentnodes.mobilemonitoringapp.R;

public class SplashActivity extends Activity {


	VideoView videoHolder;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		try{    requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        videoHolder = new VideoView(this);
			setContentView(videoHolder);
			Uri video = Uri.parse("android.resource://" + getPackageName() + "/"
					+ R.raw.ss);
			videoHolder.setVideoURI(video);

			videoHolder.setOnCompletionListener(new OnCompletionListener() {

				public void onCompletion(MediaPlayer mp) {
					jump();
				}

			});
			videoHolder.start();
		} catch(Exception ex) {
			jump();
		}
    }
//  Uncomment this function if you want the user to be able to skip this screen
	@Override
    public boolean onTouchEvent(MotionEvent event) {
	  try {
    	videoHolder.stopPlayback();
	  } catch(Exception ex) {}
	  jump();
    	return true;
    }
private void jump() {
    if(isFinishing())
        return;
    startActivity(new Intent(this, MainActivity.class));
    finish();
}
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}

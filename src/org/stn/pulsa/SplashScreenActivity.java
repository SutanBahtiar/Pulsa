package org.stn.pulsa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SplashScreenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash_screen_activity);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
				SplashScreenActivity.this.startActivity(i);
				SplashScreenActivity.this.finish();
				overridePendingTransition(R.layout.fadein, R.layout.fadeout);
			}
		}, 1000);
	}
}

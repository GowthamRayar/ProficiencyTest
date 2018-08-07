package com.wipro.proficiency.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wipro.proficiency.R;

public class SplashActivity extends AppCompatActivity {

    /**
     * Splash shown duration in milliseconds
     */
    private final int SPLASH_DURATION = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(SPLASH_DURATION);

                    Intent nextIntent = new Intent(SplashActivity.this, DashboardActivity.class);
                    nextIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(nextIntent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.start();
    }

    protected void onPause() {
        super.onPause();

        finish();
    }

}

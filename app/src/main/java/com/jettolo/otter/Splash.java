package com.jettolo.otter;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {
    private static final String TAG = Splash.class.getName();
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otter);

        ImageView imageView = findViewById(R.id.loginImageView);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        imageView.startAnimation(animation);

        Thread timer = new Thread() {
            @Override
            public void run() {
             try {
                 sleep(3000);
                 if (SaveSharedPreference.getPrefUserName(getApplicationContext()).length() == 0) {
                     Log.d(TAG, "Save preference.");
                     Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                     startActivity(intent);
                     finish();
                 } else {
                     Log.d(TAG, "Already have preference.");
                     Intent intent = new Intent(getApplicationContext(), MainPage.class);
                     startActivity(intent);
                     finish();
                 }
                 //super.run();
             }
             catch (InterruptedException e){
                 e.printStackTrace();
             }
            }
        };
        timer.start();
    }
}

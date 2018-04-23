package com.jettolo.otter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                 Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                 startActivity(intent);
                 finish();
                 super.run();
             }
             catch (InterruptedException e){
                 e.printStackTrace();
             }
            }
        };
        timer.start();
    }
}

package com.jettolo.otter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import org.jboss.aerogear.security.otp.Totp;

public class CodeManually extends AppCompatActivity {
    private static final String TAG = CodeManually.class.getName();
    private static final int COUNTDOWN_DURATION = 30000;
    private static final int COUNTDOWN_STEP = 100;

    private TextView tOtp;
    private FloatingActionButton floatingActionButton;
    private ProgressBar progressBar;

    private Totp totp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_manually);

        tOtp = (TextView) findViewById(R.id.tOTP);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CodeManually.this, QRCodeActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        totp = new Totp(SaveSharedPreference.getPrefKey(this));
        try {
            updateOTP();
            new CountDownTimer(COUNTDOWN_DURATION, COUNTDOWN_STEP) {
                @Override
                public void onTick(long millisUntilFinished) {
                    progressBar.setProgress((int) (millisUntilFinished / COUNTDOWN_STEP));
                }

                @Override
                public void onFinish() {
                    updateOTP();
                    this.start();
                }
            }.start();
        } catch (Exception e) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case (1): {
                if (resultCode == Activity.RESULT_OK) {
                    String otpauth = data.getStringExtra("url");
                    Uri otpUri = Uri.parse(otpauth);

                    String secret = otpUri.getQueryParameter("secret");
                    SaveSharedPreference.setPrefKey(CodeManually.this, secret);

                    totp = new Totp(secret);

                    updateOTP();
                } else {
                    return;
                }
                break;
            }
        }

        progressBar.setMax(COUNTDOWN_DURATION / COUNTDOWN_STEP);

        // TODO: Timenow % COUNTDOWN_DURATION CountDownTimer

        new CountDownTimer(COUNTDOWN_DURATION, COUNTDOWN_STEP) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress((int) (millisUntilFinished / COUNTDOWN_STEP));
            }

            @Override
            public void onFinish() {
                updateOTP();
                this.start();
            }
        }.start();
    }

    private void updateOTP() {
        tOtp.setText(totp.now());
    }

    /** Called when user taps Receive Request */
    public void switchMode(View view) {
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("no", null).show();
    }
}

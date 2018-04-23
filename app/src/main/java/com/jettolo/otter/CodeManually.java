package com.jettolo.otter;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.jboss.aerogear.security.otp.Totp;

public class CodeManually extends AppCompatActivity {
    private static final int COUNTDOWN_DURATION = 30000;
    private static final int COUNTDOWN_STEP = 100;

    private TextView totpDisplay;
    private TextView nameDisplay;

    private Totp totp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_manually);

        totpDisplay = (TextView) findViewById(R.id.tOTP);

        FloatingActionButton withQRCode = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        withQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CodeManually.this, QRCodeActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String otpauth = data.getStringExtra("otpauth");
                Uri otpUri = Uri.parse(otpauth);

                String name = otpUri.getQueryParameter("issuer");
                String secret = otpUri.getQueryParameter("secret");

                /*if(name != null) {
                    nameDisplay.setText(name);
                }*/

                totp = new Totp(secret);

                updateOTP();
            }
        }

        new CountDownTimer(COUNTDOWN_DURATION, COUNTDOWN_STEP) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                updateOTP();
                this.start();
            }
        }.start();
    }

    private void updateOTP() {
        totpDisplay.setText(totp.now());
    }

    /** Called when user taps Receive Request */
    public void switchMode(View view) {
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
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

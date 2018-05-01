package com.jettolo.otter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

public class MainPage2 extends AppCompatActivity {
    private static final String TAG = MainPage2.class.getName();
    final String url = "verify-push/";
    TextView textView;
    TextView refercode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page2);

        textView = (TextView) findViewById(R.id.mainPageTextView1);
        refercode = (TextView) findViewById(R.id.Showtext);

        refercode.setText(getIntent().getStringExtra("refer_code"));
    }

    public void accept(View view) {
        JSONObject json = new JSONObject();
        try {
            json.put("username", SaveSharedPreference.getPrefUserName(this));
            json.put("refer_code", refercode.getText().toString());
            json.put("accept", true);
        } catch (JSONException e) {

        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                getResources().getString(R.string.url) + url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "The end! You're pretty good!");
                Intent intent = new Intent(MainPage2.this, MainPage.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Damn it!");
            }
        });

        RequestQueueSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public void denied(View view) {
        JSONObject json = new JSONObject();
        try {
            json.put("username", SaveSharedPreference.getPrefUserName(this));
            json.put("refer_code", refercode.getText().toString());
            json.put("accept", false);
        } catch (JSONException e) {

        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                getResources().getString(R.string.url) + url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "The end! You're pretty good!");
                Intent intent = new Intent(MainPage2.this, MainPage.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Damn it!");
            }
        });

        RequestQueueSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public void switchMode(View view) {
        Intent intent = new Intent(this, CodeManually.class);
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
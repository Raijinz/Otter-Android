package com.jettolo.otter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = RegisterActivity.class.getName();
    private static final String url = "rest-auth/registration/";
    EditText username;
    EditText email;
    EditText password1;
    EditText password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.registerUsername);
        password1 = (EditText) findViewById(R.id.registerPassword1);
        password2 = (EditText) findViewById(R.id.registerPassword2);
        email = (EditText) findViewById(R.id.registerEmail);
    }

    public void register(View view) {
        getResources().getString(R.string.url);
        JSONObject json = new JSONObject();
        try {
            json.put("username", username.getText().toString());
            json.put("email", email.getText().toString());
            json.put("password1", password1.getText().toString());
            json.put ("password2", password2.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                getResources().getString(R.string.url) + url,
                json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                SaveSharedPreference.setPrefUserName(RegisterActivity.this, username.getText().toString());
                try {
                    SaveSharedPreference.setPrefAuthToken(RegisterActivity.this, response.getString("key"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueueSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
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
package com.jettolo.otter;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private String url = "mobile-push/";
    private static final String TAG = MyFirebaseInstanceIdService.class.getName();

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscription on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        JSONObject json = new JSONObject();
        try {
            json.put("username", SaveSharedPreference.getPrefUserName(this));
            json.put("registration_id", refreshedToken);
        } catch (JSONException e) {

        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                getResources().getString(R.string.url) + url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "Success!");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Fail!");
            }
        });

        RequestQueueSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
package com.biodata.biodatamaker.Firebase;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = FirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);

        Log.e(TAG, "token " + refreshedToken);
        registerForPush();

    }

    private void registerForPush() {

    }

    private void storeRegIdInPref(String token) {

        Log.e(TAG, "token " + token);
        // Prefs.putString(PrefsKeys.TOKEN,token);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Pref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.commit();


    }
}

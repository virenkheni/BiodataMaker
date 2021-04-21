package com.biodata.biodatamaker.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.biodata.biodatamaker.R;
import com.biodata.biodatamaker.Utils.App;
import com.biodata.biodatamaker.Utils.Constants;
import com.biodata.biodatamaker.Utils.Global_Class;
import com.biodata.biodatamaker.Utils.TinyDB;
import com.biodata.biodatamaker.Utils.User;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class HomeActivity extends AppCompatActivity {
    Button btn_p, btn_f, btn_e, btn_m, btn_darkmode;
    TinyDB db;
    Toolbar toolbar;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    App app;
    public String currentVersion = "";
    String NewVersion;
    Dialog dialog;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    AlertDialog.Builder builder;
    String Isalert, alert_desc, alert_link;

    SharedPreferences.Editor editor;
    boolean isDarkModeOn;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        builder = new AlertDialog.Builder(this);


        FirebaseMessaging.getInstance().subscribeToTopic("all");
        if (getIntent().getExtras() != null) {

            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);

                if (value != null) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(value));
                    startActivity(browserIntent);
                }

            }
        }


        getData();
        FirstTimeDialog();

        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        try {
            new GetVersionCode().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        checkAndRequestPermissions();
        app = (App) getApplication();
        ads();


        btn_p = findViewById(R.id.btn_personal);
        btn_f = findViewById(R.id.btn_family);
        btn_e = findViewById(R.id.btn_education);
        btn_m = findViewById(R.id.btn_maternal);
        btn_darkmode = findViewById(R.id.btn_darkmode);


        checkDarkMode();
        db = new TinyDB(this);


        btn_p.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                app.showInterstitialAdGoogle();
                startActivity(new Intent(HomeActivity.this, PersonalDetailsActivity.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            }
        });

        btn_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.showInterstitialAdGoogle();
                startActivity(new Intent(HomeActivity.this, FamilyBackActivity.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            }
        });
        btn_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.showInterstitialAdGoogle();
                startActivity(new Intent(HomeActivity.this, EducationActivity.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            }
        });
        btn_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.showInterstitialAdGoogle();
                startActivity(new Intent(HomeActivity.this, MaternalDetailsActivity.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            }
        });

        btn_darkmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When user taps the enable/disable
                // dark mode button
                if (isDarkModeOn) {

                    // if dark mode is on it
                    // will turn it off
                    AppCompatDelegate
                            .setDefaultNightMode(
                                    AppCompatDelegate
                                            .MODE_NIGHT_NO);
                    // it will set isDarkModeOn
                    // boolean to false
                    editor.putBoolean(
                            "isDarkModeOn", false);
                    editor.apply();

                    // change text of Button
                    btn_darkmode.setText(
                            "Enable Dark Mode");
                } else {

                    // if dark mode is off
                    // it will turn it on
                    AppCompatDelegate
                            .setDefaultNightMode(
                                    AppCompatDelegate
                                            .MODE_NIGHT_YES);

                    // it will set isDarkModeOn
                    // boolean to true
                    editor.putBoolean(
                            "isDarkModeOn", true);
                    editor.apply();

                    // change text of Button
                    btn_darkmode.setText(
                            "Enable Light Mode");
                }
            }
        });


    }


    private void getData() {

        if (Global_Class.CheckInternetConnection(this)) {

            Global_Class.Array_List_Main.clear();

            SharedPreferences prefs = getSharedPreferences("Data", MODE_PRIVATE);
            int i = prefs.getInt("Counter", 0);


            mFirebaseInstance = FirebaseDatabase.getInstance();

            mFirebaseDatabase = mFirebaseInstance.getReference("biodata");

            mFirebaseInstance.getReference("app_title").setValue("Realtime DataBase");

            mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String appTitle = dataSnapshot.getValue(String.class);
                    getSupportActionBar().setTitle(appTitle);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                }

            });

            mFirebaseDatabase.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    int totalRecord = (int) dataSnapshot.getChildrenCount();

                    Global_Class.Array_List_Main.clear();


                    for (int j = 0; j < totalRecord; j++) {

                        mFirebaseDatabase.child(String.valueOf(j)).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                User user = dataSnapshot.getValue(User.class);
                                Isalert = user.getAl();
                                alert_desc = user.getAl_desc();
                                alert_link = user.getAl_link();

                                Log.i("---IsAlert", Isalert + "");
                                Log.i("---IsAlert_desc", alert_desc + "");
                                Log.i("---IsAlert_link", alert_link + "");

                                if (Isalert.equalsIgnoreCase("yes")) {
                                    NoticeDialog();
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {

        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        ads();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public void ads() {
        MobileAds.initialize(this);
        AudienceNetworkAds.initialize(this);
        LinearLayout lay_google = findViewById(R.id.lay_google);
        app.loadAd(lay_google);

        LinearLayout lay_fb = findViewById(R.id.lay_fb);
        app.loadAdFb(lay_fb);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Share:
                Intent shareIntent = new Intent("android.intent.action.SEND");
                shareIntent.setType("text/plain");
                shareIntent.putExtra("android.intent.extra.TEXT", getResources().getString(R.string.app_name) + "\n" + "https://play.google.com/store/apps/details?id=" + getPackageName());
                shareIntent.putExtra("android.intent.extra.SUBJECT", "");
                startActivity(Intent.createChooser(shareIntent, "Share this app with your Friends..."));
                return true;
            case R.id.Rate:
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                return true;
            case R.id.More:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:" + getResources().getString(R.string.developer_details))));
                } catch (ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=" + getResources().getString(R.string.developer_details))));
                }
                return true;
            case R.id.Privacy:
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getResources().getString(R.string.pp))));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean checkAndRequestPermissions() {
        int permissionInternet = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        int permissionStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionReadStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (permissionInternet != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.INTERNET);
        }

        if (permissionStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionReadStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<>();
                perms.put(Manifest.permission.INTERNET, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);

                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    if (perms.get(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

                    ) {
                    } else {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)

                        ) {
                            showDialogOK("Internet & Storage Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    break;
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                }
            }
        }
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    @SuppressLint("NewApi")
    class GetVersionCode extends AsyncTask<Void, String, String> {

        @Override

        protected String doInBackground(Void... voids) {

            String newVersion = null;

            Document document = null;
            try {
                document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + HomeActivity.this.getPackageName() + "&hl=en")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (document != null) {
                Elements element = document.getElementsContainingOwnText("Current Version");
                for (org.jsoup.nodes.Element ele : element) {
                    if (ele.siblingElements() != null) {
                        Elements sibElemets = ele.siblingElements();
                        for (org.jsoup.nodes.Element sibElemet : sibElemets) {
                            newVersion = sibElemet.text();
                        }
                    }
                }
            }
            return newVersion;

        }


        @SuppressLint("NewApi")
        @Override

        protected void onPostExecute(String onlineVersion) {

            super.onPostExecute(onlineVersion);

            if (onlineVersion != null && !onlineVersion.isEmpty()) {

                if (Float.valueOf(currentVersion) < Float.valueOf(onlineVersion)) {
                    NewVersion = onlineVersion;
                    showUpdateDialog();
                }

            }

            //  Log.d("---update", "Current version " + currentVersion + "playstore version " + onlineVersion);

        }

    }

    private void showUpdateDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Update is Available");
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse
                        ("https://play.google.com/store/apps/details?id=" + getPackageName() + "&hl=en")));
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.setCancelable(false);
        builder.show();
    }

    private boolean isFirstTime() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }
        return !ranBefore;
    }

    private boolean isRegistered() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREF, MODE_PRIVATE);
        return sharedPreferences.getBoolean(Constants.REGISTERED, false);
    }

    /* private void registerDevice() {
         Firebase firebase = new Firebase(Constants.FIREBASE_APP);
         Firebase newFirebase = firebase.push();

         Map<String, String> val = new HashMap<>();
         val.put("msg", "none");
         newFirebase.setValue(val);
         String uniqueId = newFirebase.getKey();

         //Finally we need to implement a method to store this unique id to our server
         *//* sendIdToServer(uniqueId);*//*
    }*/
    public void NoticeDialog() {
        //  builder.setMessage("alert_link").setTitle("alert_desc");

        //Setting message manually and performing action on button click
        builder.setMessage(alert_link)
                .setTitle(alert_desc)
                .setCancelable(false)
                .setPositiveButton("Download", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(alert_link)));
                    }
                })
                .setNegativeButton("Later", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        finish();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void FirstTimeDialog() {
        if (Build.VERSION.SDK_INT >= 16) {
            if (isFirstTime()) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Privacy Policy");
                builder.setMessage(getResources().getString(R.string.dialog_privacypolicy));
                builder.setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("RanBefore", false);
                        editor.commit();
                        finish();
                    }
                });

                builder.setCancelable(false);
                dialog = builder.show();
            }
        }

    }

    public void checkDarkMode() {
        // Saving state of our app
        // using SharedPreferences
        sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);

        // When user reopens the app
        // after applying dark/light mode
        if (isDarkModeOn) {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES);
            btn_darkmode.setText("Enable Light Mode");
        } else {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_NO);
            btn_darkmode.setText("Enable Dark Mode");
        }
    }


}

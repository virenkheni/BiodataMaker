package com.biodata.biodatamaker.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.biodata.biodatamaker.R;
import com.biodata.biodatamaker.Utils.App;
import com.biodata.biodatamaker.Utils.TinyDB;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;

public class MaternalDetailsActivity extends AppCompatActivity {
    EditText mname1, mname2, mname3, mvillage, maddress;
    String smname1, smname2, smname3, smvillage, smaddress;
    Button next, reset, btn_back;
    TinyDB db;
    Toolbar toolbar;
    App app;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maternal_details);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Maternal Details");
        app = (App) getApplication();
        ads();
        builder = new AlertDialog.Builder(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.showInterstitialAdFb();
                finish();

            }
        });
        mname1 = (EditText)findViewById(R.id.et_mname1);
        mname2 = (EditText)findViewById(R.id.et_mname2);
        mname3 = (EditText)findViewById(R.id.et_mname3);
        mvillage = (EditText)findViewById(R.id.et_mvillage);
        maddress = (EditText)findViewById(R.id.et_maddress);
        next = (Button)findViewById(R.id.mnext);
        reset = (Button)findViewById(R.id.mreset);
        btn_back = (Button)findViewById(R.id.btn_back);
        db = new TinyDB(this);


        if (!db.getString("mname1").isEmpty()) {
            mname1.setText(db.getString("mname1"));
            mname2.setText(db.getString("mname2"));
            mname3.setText(db.getString("mname3"));
            mvillage.setText(db.getString("mvillage"));
            maddress.setText(db.getString("maddress"));
        } else {

        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smname1 = mname1.getText().toString();
                smname2 = mname2.getText().toString();
                smname3 = mname3.getText().toString();
                smvillage = mvillage.getText().toString();
                smaddress = maddress.getText().toString();

                if (!smname1.isEmpty() || !smname2.isEmpty() || !smname3.isEmpty() || !smvillage.isEmpty() || !smaddress.isEmpty()) {
                    db.putString("mname1", smname1);
                    db.putString("mname2", smname2);
                    db.putString("mname3", smname3);
                    db.putString("mvillage", smvillage);
                    db.putString("maddress", smaddress);
                } else {

                }

                startActivity(new Intent(MaternalDetailsActivity.this, TemplateActivity.class));
                app.showInterstitialAdGoogle();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearDataDialog();

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                app.showInterstitialAdFb();
            }
        });
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

    public void ads() {
        MobileAds.initialize(this);
        AudienceNetworkAds.initialize(this);
        App app = (App) getApplication();
        LinearLayout lay_google = findViewById(R.id.lay_google);
        app.loadAd(lay_google);

        LinearLayout lay_fb = findViewById(R.id.lay_fb);
        app.loadAdFb(lay_fb);

       /* ll_adview_fb = (LinearLayout) findViewById(R.id.lay_fb);
        adView_fb = new com.facebook.ads.AdView(this, "YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50);
        ll_adview_fb.addView(adView_fb);
        adView_fb.loadAd();*/

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
        app.showInterstitialAdFb();
    }

    public void ClearDataDialog() {
        //  builder.setMessage("alert_link").setTitle("alert_desc");

        //Setting message manually and performing action on button click
        builder.setTitle("Are you sure you want to Clear Data?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ResetData();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();

                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void ResetData() {
        db.putString("mname1", "");
        db.putString("mname2", "");
        db.putString("mname3", "");
        db.putString("mvillage", "");
        db.putString("maddress", "");

        mname1.setText("");
        mname2.setText("");
        mname3.setText("");
        mvillage.setText("");
        maddress.setText("");
        Toast.makeText(MaternalDetailsActivity.this, "Clear Data", Toast.LENGTH_LONG).show();
    }
}

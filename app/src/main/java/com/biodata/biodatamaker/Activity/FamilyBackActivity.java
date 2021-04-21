package com.biodata.biodatamaker.Activity;

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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.biodata.biodatamaker.R;
import com.biodata.biodatamaker.Utils.App;
import com.biodata.biodatamaker.Utils.TinyDB;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;

public class FamilyBackActivity extends AppCompatActivity {
    Button next, reset, btn_back;
    EditText nplace, father, mother, foccuption, moccuption, bro1, bro2;
    String snplace, sfather, smother, sfoccuption, smoccuption, sbro1, sbro2;
    TinyDB db;
    Toolbar toolbar;
    App app;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_back);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Family Background");
        app = (App) getApplication();
        ads();
        builder = new AlertDialog.Builder(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                app.showInterstitialAdFb();
            }
        });
        next = (Button) findViewById(R.id.fnext);
        reset = (Button) findViewById(R.id.freset);
        btn_back = (Button) findViewById(R.id.btn_back);

        nplace = (EditText) findViewById(R.id.et_nativeplaace);
        father = (EditText) findViewById(R.id.et_father);
        mother = (EditText) findViewById(R.id.et_mother);
        foccuption = (EditText) findViewById(R.id.et_occ_father);
        moccuption = (EditText) findViewById(R.id.et_occ_mother);
        bro1 = (EditText) findViewById(R.id.et_bro1);
        bro2 = (EditText) findViewById(R.id.et_bro2);
        db = new TinyDB(this);

        if (!db.getString("father").isEmpty()) {
            nplace.setText(db.getString("np"));
            father.setText(db.getString("father"));
            mother.setText(db.getString("mother"));
            foccuption.setText(db.getString("foccuption"));
            moccuption.setText(db.getString("moccuption"));
            bro1.setText(db.getString("bro1"));
            bro2.setText(db.getString("bro2"));

        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.showInterstitialAdGoogle();
                snplace = nplace.getText().toString();
                sfather = father.getText().toString();
                smother = mother.getText().toString();
                sfoccuption = foccuption.getText().toString();
                smoccuption = moccuption.getText().toString();
                sbro1 = bro1.getText().toString();
                sbro2 = bro2.getText().toString();

                if (!snplace.isEmpty() || !sfather.isEmpty() || !smother.isEmpty() || !sfoccuption.isEmpty()
                        || !smoccuption.isEmpty() || !sbro1.isEmpty() || !sbro2.isEmpty()) {
                    db.putString("np", snplace);
                    db.putString("father", sfather);
                    db.putString("mother", smother);
                    db.putString("foccuption", sfoccuption);
                    db.putString("moccuption", smoccuption);
                    db.putString("bro1", sbro1);
                    db.putString("bro2", sbro2);
                } else {

                }
                startActivity(new Intent(FamilyBackActivity.this, MaternalDetailsActivity.class));
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

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        app.showInterstitialAdFb();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ads();
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
        db.putString("np", "");
        db.putString("father", "");
        db.putString("mother", "");
        db.putString("foccuption", "");
        db.putString("moccuption", "");
        db.putString("bro1", "");
        db.putString("bro2", "");

        nplace.setText("");
        father.setText("");
        mother.setText("");
        foccuption.setText("");
        moccuption.setText("");
        bro1.setText("");
        bro2.setText("");
        Toast.makeText(FamilyBackActivity.this, "Clear Data", Toast.LENGTH_LONG).show();
    }
}

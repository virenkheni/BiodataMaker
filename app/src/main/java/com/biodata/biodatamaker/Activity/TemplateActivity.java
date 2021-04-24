package com.biodata.biodatamaker.Activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.biodata.biodatamaker.Adapter.Grid_Adapter;
import com.biodata.biodatamaker.R;
import com.biodata.biodatamaker.Utils.App;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;

public class TemplateActivity extends AppCompatActivity {
    public static String[] web = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22",
            "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45"

    };
    public static int[] imageId = {
            R.drawable.ic_back1, R.drawable.ic_back2, R.drawable.ic_back3, R.drawable.ic_back4, R.drawable.ic_back5, R.drawable.ic_back6, R.drawable.ic_back7,
            R.drawable.ic_back8, R.drawable.ic_back9, R.drawable.ic_back10, R.drawable.ic_back11, R.drawable.ic_back12, R.drawable.ic_back13, R.drawable.ic_back14,
            R.drawable.ic_back15, R.drawable.ic_back16, R.drawable.ic_back17, R.drawable.ic_back18, R.drawable.ic_back19, R.drawable.ic_back20, R.drawable.ic_back21,
            R.drawable.ic_back_22, R.drawable.ic_back_23, R.drawable.ic_back_24, R.drawable.ic_back_25, R.drawable.ic_back_26, R.drawable.ic_back_27, R.drawable.ic_back_28,
            R.drawable.ic_back_29, R.drawable.ic_back_30, R.drawable.ic_back_31, R.drawable.ic_back_32, R.drawable.ic_back_33, R.drawable.ic_back_34, R.drawable.ic_back_35,
            R.drawable.ic_back_36, R.drawable.ic_back_37, R.drawable.ic_back_38, R.drawable.ic_back_39, R.drawable.ic_back_40, R.drawable.ic_back_41,
            R.drawable.ic_back_42, R.drawable.ic_back_43, R.drawable.ic_back_44, R.drawable.ic_back_45

    };
    //R.drawable.ic_back_39, R.drawable.ic_back_40, R.drawable.ic_back_41,R.drawable.ic_back_42, R.drawable.ic_back_43, R.drawable.ic_back_44, R.drawable.ic_back_45
    GridView grid;
    int pos;
    Toolbar toolbar;
    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Select Background");
        app = (App) getApplication();
        ads();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                app.showInterstitialAdFb();

            }
        });
        Grid_Adapter adapter = new Grid_Adapter(this, imageId);
        grid = findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                pos = position + 1;
                if (pos == 1) {
                    app.showInterstitialAdGoogle();
                    Intent i = new Intent(TemplateActivity.this, Template1.class);
                    startActivity(i);
                } else {
                    app.showInterstitialAdGoogle();
                    Intent i = new Intent(TemplateActivity.this, Template2.class);
                    i.putExtra("position", pos);
                    startActivity(i);
                }

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
}

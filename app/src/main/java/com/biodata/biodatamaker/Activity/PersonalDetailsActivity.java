package com.biodata.biodatamaker.Activity;

import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PersonalDetailsActivity extends AppCompatActivity {
    Button next, reset, btn_back;
    EditText name, dob, height, weight, hobby, occuption, mobile, address;
    String sname, sdob, sheight, sweight, shobby, soccuption, smobile, saddress;
    TinyDB db;

    Toolbar toolbar;
    App app;
    AlertDialog.Builder builder;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Personal Details");
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


        name = (EditText) findViewById(R.id.et_name);
        dob = (EditText) findViewById(R.id.et_dob);
        height = (EditText) findViewById(R.id.et_height);
        weight = (EditText) findViewById(R.id.et_weight);
        hobby = (EditText) findViewById(R.id.et_hobby);
        occuption = (EditText) findViewById(R.id.et_occuption);
        mobile = (EditText) findViewById(R.id.et_mobile);
        address = (EditText) findViewById(R.id.et_address);
        btn_back = (Button) findViewById(R.id.btn_back);
        db = new TinyDB(this);

        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                dob.setText(sdf.format(myCalendar.getTime()));
            }

        };

        next = findViewById(R.id.pnext);
        reset = findViewById(R.id.preset);
        // SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        if (!db.getString("name").isEmpty()) {
            name.setText(db.getString("name"));
            dob.setText(db.getString("dob"));
            height.setText(db.getString("height"));
            weight.setText(db.getString("weight"));
            hobby.setText(db.getString("hobby"));
            occuption.setText(db.getString("occuption"));
            mobile.setText(db.getString("mobile"));
            address.setText(db.getString("address"));

        } else {
            //Log.i("SharedPref", " is Empty/-/-/-/-/-/-/-/-/-/-/-/");
        }
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(PersonalDetailsActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.showInterstitialAdGoogle();

                sname = name.getText().toString();
                sdob = dob.getText().toString();
                sheight = height.getText().toString();
                sweight = weight.getText().toString();
                shobby = hobby.getText().toString();
                soccuption = occuption.getText().toString();
                smobile = mobile.getText().toString();
                saddress = address.getText().toString();


               /* if (!sname.isEmpty() || !sdob.isEmpty() || !sheight.isEmpty() || !sweight.isEmpty() || !shobby.isEmpty()
                        || !soccuption.isEmpty() || !smobile.isEmpty() || !saddress.isEmpty()) {*/
                if (!sdob.isEmpty()) {
                    db.putString("name", sname);
                    db.putString("dob", sdob);
                    db.putString("height", sheight);
                    db.putString("weight", sweight);
                    db.putString("hobby", shobby);
                    db.putString("occuption", soccuption);
                    db.putString("mobile", smobile);
                    db.putString("address", saddress);


                   /* SharedPreferences settings = getApplicationContext().getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("name", sname);
                    editor.putString("dob", sdob);
                    editor.putString("height", sheight);
                    editor.putString("weight", sweight);
                    editor.putString("hobby", shobby);
                    editor.putString("occuption", soccuption);
                    editor.putString("mobile", smobile);
                    editor.putString("address", saddress);
                    editor.apply();*/
                    startActivity(new Intent(PersonalDetailsActivity.this, EducationActivity.class));

                } else {
                    dob.setError("Date of Birth is Empty");
                }
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
        db.putString("name", "");
        db.putString("dob", "");
        db.putString("height", "");
        db.putString("weight", "");
        db.putString("hobby", "");
        db.putString("occuption", "");
        db.putString("mobile", "");
        db.putString("address", "");

        name.setText("");
        dob.setText("");
        height.setText("");
        weight.setText("");
        hobby.setText("");
        occuption.setText("");
        mobile.setText("");
        address.setText("");
        Toast.makeText(PersonalDetailsActivity.this, "Clear Data", Toast.LENGTH_LONG).show();
    }
}

package com.biodata.biodatamaker.Activity;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.biodata.biodatamaker.R;
import com.biodata.biodatamaker.Utils.App;
import com.biodata.biodatamaker.Utils.TinyDB;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class EducationActivity extends AppCompatActivity {
    Button next, reset, btn_back;
    EditText college1, college2;
    TextView tv_addphoto;
    String scollege1, scollege2;
    TinyDB db;
    ImageView iv;
    private int PICK_IMAGE_REQUEST = 1;
    Toolbar toolbar;
    App app;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText("Education");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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


        next = (Button) findViewById(R.id.enext);
        reset = (Button) findViewById(R.id.ereset);
        btn_back = (Button) findViewById(R.id.btn_back);
        college1 = (EditText) findViewById(R.id.et_college1);
        college2 = (EditText) findViewById(R.id.et_college2);
        tv_addphoto = (TextView) findViewById(R.id.tv_addphoto);
        iv = (ImageView) findViewById(R.id.iv_photo_main);
        db = new TinyDB(this);

        if (!db.getString("college1").isEmpty()) {
            college1.setText(db.getString("college1"));
            college2.setText(db.getString("college2"));

            iv.setImageBitmap(StringToBitMap(db.getString("pic")));
        } else {
            Log.i("SharePref", "Empty/-/-/-/-/-/-/-/-/-/");
        }
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.showInterstitialAdGoogle();
                scollege1 = college1.getText().toString();
                scollege2 = college2.getText().toString();
                if (!scollege1.isEmpty() || !scollege2.isEmpty()) {
                    db.putString("college1", scollege1);
                    db.putString("college2", scollege2);
                } else {

                }
                startActivity(new Intent(EducationActivity.this, FamilyBackActivity.class));
                app.showInterstitialAdGoogle();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearDataDialog();
               /* db.putString("college1", "");
                db.putString("college2", "");
                db.putString("pic", "");

                college1.setText("");
                college2.setText("");
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(EducationActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("photo", "");
                editor.commit();
                iv.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher_round));
                Toast.makeText(EducationActivity.this, "Clear Data", Toast.LENGTH_LONG).show();*/
            }
        });
        tv_addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
                iv.setImageBitmap(StringToBitMap(db.getString("pic")));
            }
        });
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
                iv.setImageBitmap(StringToBitMap(db.getString("pic")));
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


    public void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri); // Log.d(TAG, String.valueOf(bitmap));
                iv.setImageBitmap(bitmap);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();

                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("photo", encodedImage);
                editor.commit();


                db.putString("pic", encodedImage);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Bitmap StringToBitMap(String image) {
        try {
            byte[] encodeByte = Base64.decode(image, Base64.DEFAULT);

            InputStream inputStream = new ByteArrayInputStream(encodeByte);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
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
        app = (App) getApplication();
        LinearLayout lay_google = findViewById(R.id.lay_google);
        app.loadAd(lay_google);

        LinearLayout lay_fb = findViewById(R.id.lay_fb);
        app.loadAdFb(lay_fb);

        /*ll_adview_fb = (LinearLayout) findViewById(R.id.lay_fb);
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
        db.putString("college1", "");
        db.putString("college2", "");
        db.putString("pic", "");

        college1.setText("");
        college2.setText("");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(EducationActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("photo", "");
        editor.commit();
        iv.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher_round));
        Toast.makeText(EducationActivity.this, "Clear Data", Toast.LENGTH_LONG).show();
    }
}

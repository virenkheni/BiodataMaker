package com.biodata.biodatamaker.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.biodata.biodatamaker.R;
import com.biodata.biodatamaker.Utils.TinyDB;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Template2 extends AppCompatActivity {
    TextView tv_name, tv_dob, tv_height, tv_weight, tv_hobby, tv_occ, tv_mobile, tv_address,
            tv_college, tv_np, tv_father, tv_mother, tv_focc, tv_mocc, tv_bro, tv_bro_title, tv_mname, tv_mname_title, tv_mvillage, tv_maddress;

    TinyDB db;
    Button btn_pdf;

    private FrameLayout llPdf;
    private Bitmap bitmap;


    ImageView iv_photo;
    Bitmap bitmap1;

    File path;
    private ViewGroup mainLayout;

    RelativeLayout relative;
    LinearLayout ll_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_template2);

        Bundle bundle = getIntent().getExtras();
        int pos = bundle.getInt("position");

        if (pos == 2) {
            setContentView(R.layout.activity_template2);
        } else if (pos == 3) {
            setContentView(R.layout.activity_template3);
        } else if (pos == 4) {
            setContentView(R.layout.activity_template4);
        } else if (pos == 5) {
            setContentView(R.layout.activity_template5);
        } else if (pos == 6) {
            setContentView(R.layout.activity_template6);
        } else if (pos == 7) {
            setContentView(R.layout.activity_template7);
        } else if (pos == 8) {
            setContentView(R.layout.activity_template8);
        } else if (pos == 9) {
            setContentView(R.layout.activity_template9);
        } else if (pos == 10) {
            setContentView(R.layout.activity_template10);
        } else if (pos == 11) {
            setContentView(R.layout.activity_template11);
        } else if (pos == 12) {
            setContentView(R.layout.activity_template12);
        } else if (pos == 13) {
            setContentView(R.layout.activity_template13);
        } else if (pos == 14) {
            setContentView(R.layout.activity_template14);
        } else if (pos == 15) {
            setContentView(R.layout.activity_template15);
        } else if (pos == 16) {
            setContentView(R.layout.activity_template16);
        } else if (pos == 17) {
            setContentView(R.layout.activity_template17);
        } else if (pos == 18) {
            setContentView(R.layout.activity_template18);
        } else if (pos == 19) {
            setContentView(R.layout.activity_template19);
        } else if (pos == 20) {
            setContentView(R.layout.activity_template20);
        } else if (pos == 21) {
            setContentView(R.layout.activity_template21);
        } else if (pos == 22) {
            setContentView(R.layout.activity_template22);
        } else if (pos == 23) {
            setContentView(R.layout.activity_template23);
        } else if (pos == 24) {
            setContentView(R.layout.activity_template24);
        } else if (pos == 25) {
            setContentView(R.layout.activity_template25);
        } else if (pos == 26) {
            setContentView(R.layout.activity_template26);
        } else if (pos == 27) {
            setContentView(R.layout.activity_template27);
        } else if (pos == 28) {
            setContentView(R.layout.activity_template28);
        } else if (pos == 29) {
            setContentView(R.layout.activity_template29);
        } else if (pos == 30) {
            setContentView(R.layout.activity_template30);
        } else if (pos == 31) {
            setContentView(R.layout.activity_template31);
        } else if (pos == 32) {
            setContentView(R.layout.activity_template32);
        } else if (pos == 33) {
            setContentView(R.layout.activity_template33);
        } else if (pos == 34) {
            setContentView(R.layout.activity_template34);
        } else if (pos == 35) {
            setContentView(R.layout.activity_template35);
        } else if (pos == 36) {
            setContentView(R.layout.activity_template36);
        } else if (pos == 37) {
            setContentView(R.layout.activity_template37);
        } else if (pos == 38) {
            setContentView(R.layout.activity_template38);
        } else if (pos == 39) {
            setContentView(R.layout.activity_template39);
        } else if (pos == 40) {
            setContentView(R.layout.activity_template40);
        } else if (pos == 41) {
            setContentView(R.layout.activity_template41);
        } else if (pos == 42) {
            setContentView(R.layout.activity_template42);
        } else if (pos == 43) {
            setContentView(R.layout.activity_template43);
        } else if (pos == 44) {
            setContentView(R.layout.activity_template44);
        } else if (pos == 45) {
            setContentView(R.layout.activity_template45);
        }


        tv_name = findViewById(R.id.tv_name);
        // tv_name1 = (TextView) findViewById(R.id.tv_name1);
        tv_dob = findViewById(R.id.tv_dob);
        tv_height = findViewById(R.id.tv_height);
        tv_weight = findViewById(R.id.tv_weight);
        tv_hobby = findViewById(R.id.tv_hobby);
        tv_occ = findViewById(R.id.tv_occuption);
        tv_mobile = findViewById(R.id.tv_mobile);
        tv_address = findViewById(R.id.tv_address);
        tv_college = findViewById(R.id.tv_college);
        tv_np = findViewById(R.id.tv_np);
        tv_father = findViewById(R.id.tv_father);
        tv_mother = findViewById(R.id.tv_mother);
        tv_focc = findViewById(R.id.tv_foccuption);
        tv_mocc = findViewById(R.id.tv_moccuption);
        tv_bro = findViewById(R.id.tv_brother);
        tv_bro_title = findViewById(R.id.tv_brother_title);
        tv_mname = findViewById(R.id.tv_mname);
        tv_mname_title = findViewById(R.id.tv_mname_title);
        tv_mvillage = findViewById(R.id.tv_mvillage);
        tv_maddress = findViewById(R.id.tv_maddress);
        btn_pdf = findViewById(R.id.btn_pdf);
        llPdf = findViewById(R.id.framelayout);
        iv_photo = findViewById(R.id.iv_photo);
        mainLayout = (RelativeLayout) findViewById(R.id.relative);
        relative = findViewById(R.id.relativee);
        //mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());


        db = new TinyDB(this);


        tv_name.setText(db.getString("name"));
        tv_dob.setText(db.getString("dob"));
        tv_height.setText(db.getString("height") + " ft");
        tv_weight.setText(db.getString("weight") + " KG");
        tv_hobby.setText(db.getString("hobby"));
        tv_occ.setText(db.getString("occuption"));
        if (!db.getString("mobile").isEmpty()) {
            tv_mobile.setText(db.getString("mobile"));
        } else {
            tv_mobile.setVisibility(View.GONE);
        }

        tv_address.setText(db.getString("address"));
        if (!db.getString("college2").isEmpty()) {
            tv_college.setText("▸ " + db.getString("college1") + "\n▸ " + db.getString("college2"));
        } else {
            tv_college.setText(db.getString("college1"));
        }
        tv_np.setText(db.getString("np"));
        tv_father.setText(db.getString("father"));
        tv_mother.setText(db.getString("mother"));
        tv_focc.setText(db.getString("foccuption"));
        tv_mocc.setText(db.getString("moccuption"));
        if (!db.getString("bro2").isEmpty()) {
            tv_bro.setText("▸ " + db.getString("bro1") + "\n▸ " + db.getString("bro2"));
        } else {
            tv_bro.setText(db.getString("bro1"));
        }
        if (db.getString("bro1").isEmpty()) {
            tv_bro.setVisibility(View.GONE);
            tv_bro_title.setVisibility(View.GONE);
        }
        if (!db.getString("mname2").isEmpty()) {
            tv_mname.setText("▸ " + db.getString("mname1") + "\n▸ " + db.getString("mname2"));
        } else if (!db.getString("mname3").isEmpty()) {
            tv_mname.setText("▸ " + db.getString("mname1") + "\n▸ " + db.getString("mname2") + "\n▸ " + db.getString("mname3"));
        } else {
            tv_mname.setText(db.getString("mname1"));
        }
        if (db.getString("mname1").isEmpty()) {
            tv_mname.setVisibility(View.GONE);
            tv_mname_title.setVisibility(View.GONE);
        }
        tv_mvillage.setText(db.getString("mvillage"));
        tv_maddress.setText(db.getString("maddress"));

       /* ll_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });*/


        btn_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bitmap = loadBitmapFromView(llPdf, llPdf.getWidth(), llPdf.getHeight());

                try {
                    getScreenshotBmp();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(this);
        String previouslyEncodedImage = shre.getString("photo", "");

        if (!previouslyEncodedImage.equalsIgnoreCase("")) {
            byte[] b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
            bitmap1 = BitmapFactory.decodeByteArray(b, 0, b.length);
            iv_photo.setImageBitmap(bitmap1);

        }

        iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        float deg = iv_photo.getRotation() + 90F;
                        iv_photo.animate().rotation(deg);//Do something after 100ms
                    }
                }, 500);
            }
        });

    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }

    public Bitmap getScreenshotBmp() {
        btn_pdf.setVisibility(View.GONE);
        int imageNum = 1;
        FileOutputStream fileOutputStream = null;

        path = new File(Environment.getExternalStorageDirectory(), "BioData");
        if (!path.exists()) {
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory(), "BioData");
            wallpaperDirectory.mkdirs();
        }

        //String uniqueID = UUID.randomUUID().toString();
        String fileName = "item_" + imageNum + ".jpg";
        File file = new File(path, fileName);
        while (file.exists()) {
            imageNum++;
            fileName = "item_" + imageNum + ".jpg";
            file = new File(path, fileName);
        }
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);

        try {
            fileOutputStream.flush();
            fileOutputStream.close();
            galleryAddPic();
            btn_pdf.setVisibility(View.VISIBLE);
            Snackbar snackbar = Snackbar.make(relative, "Biodata Saved", Snackbar.LENGTH_LONG)
                    .setAction("Open", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openFolder();
                        }
                    });
            snackbar.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public void openFolder() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setDataAndType(Uri.parse(Environment.getExternalStorageDirectory().getPath() + File.separator + "Biodata" + File.separator), "*/*");
        intent.addCategory("android.intent.category.DEFAULT");
        startActivity(Intent.createChooser(intent, "Open folder"));

    }

    private void galleryAddPic() {
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "Biodata")));
        sendBroadcast(intent);
    }
}

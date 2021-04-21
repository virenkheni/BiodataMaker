package com.biodata.biodatamaker.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.View;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Template1 extends AppCompatActivity {
    TextView tv_name, tv_name1, tv_dob, tv_height, tv_weight, tv_hobby, tv_occ, tv_mobile, tv_address,
            tv_college, tv_np, tv_father, tv_mother, tv_focc, tv_mocc, tv_bro, tv_bro_title, tv_mname, tv_mname_title, tv_mvillage, tv_maddress;

    TinyDB db;
    Button btn_download;

    ImageView iv_photo;
    RelativeLayout relative;
    LinearLayout ll_photo;
    FrameLayout frameLayout;
    RelativeLayout mainLayout;
    File path;

    Bitmap bitmap;
    ByteArrayOutputStream bytearrayoutputstream;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template1);


        tv_name = findViewById(R.id.tv_name);
        tv_name1 = findViewById(R.id.tv_name1);
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
        btn_download = findViewById(R.id.btn_download);
        frameLayout = findViewById(R.id.framelayout);
        iv_photo = findViewById(R.id.iv_photo);
        mainLayout = (RelativeLayout) findViewById(R.id.relative);
        relative = findViewById(R.id.relativee);
        ll_photo = findViewById(R.id.ll_photo);
        //mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());


        db = new TinyDB(this);


        tv_name.setText(db.getString("name"));
        tv_name1.setText(db.getString("name"));
        tv_dob.setText(db.getString("dob"));
        tv_height.setText(db.getString("height") + " inch");
        tv_weight.setText(db.getString("weight") + " KG");
        if (!db.getString("hobby").isEmpty()) {
            tv_hobby.setText(db.getString("hobby"));
        } else {
            tv_hobby.setVisibility(View.GONE);
        }

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
        if (db.getString("college1").isEmpty()) {
            tv_college.setVisibility(View.GONE);
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

        iv_photo.setImageBitmap(StringToBitMap(db.getString("pic")));

        bytearrayoutputstream = new ByteArrayOutputStream();

        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bitmap = loadBitmapFromView(frameLayout, frameLayout.getWidth(), frameLayout.getHeight());
                try {
                    getScreenshotBmp();
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });
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

    public static Bitmap loadBitmapFromView(View view, int i, int i2) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(createBitmap));
        return createBitmap;
    }

    public Bitmap getScreenshotBmp() {
        btn_download.setVisibility(View.GONE);
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
            btn_download.setVisibility(View.VISIBLE);
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
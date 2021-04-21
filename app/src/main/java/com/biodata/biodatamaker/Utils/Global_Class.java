package com.biodata.biodatamaker.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.biodata.biodatamaker.R;

import java.util.ArrayList;
import java.util.List;


public class Global_Class {

    public static List<Element> Array_List_Main = new ArrayList<>();


    // Check InterNet Connection
    public static boolean CheckInternetConnection(Activity Activity) {
        ConnectivityManager cm = (ConnectivityManager) Activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = Activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Activity.getResources().getColor(R.color.colorPrimary));
            }
            return true;
        } else {
            //Toast.makeText(Activity, "Please check your internet connection", Toast.LENGTH_LONG).show();

            return false;
        }
    }

  //  public static String AppPackage = "https://play.google.com/store/apps/details?id=" + "com.ipl.schedule.ipl2018";


}

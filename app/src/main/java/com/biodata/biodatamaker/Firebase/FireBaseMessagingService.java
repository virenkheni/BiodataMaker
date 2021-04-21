package com.biodata.biodatamaker.Firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;


import com.biodata.biodatamaker.Activity.HomeActivity;
import com.biodata.biodatamaker.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class FireBaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = FireBaseMessagingService.class.getSimpleName();
    public static int NOTIFICATION_ID = 5000;

    String msg;
    String pageType;
    String itemId;
    private Bitmap bitmap;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void generateNotification(Context context, String title,
                                            String message, Intent intent, int id, Bitmap bitmap) {

        NOTIFICATION_ID = random(0, NOTIFICATION_ID);

        int icon = R.drawable.ic_biodata_small;

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(NOTIFICATION_SERVICE);

        PendingIntent pendingIntent = null;


        if (intent != null) {

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP
                    | Intent.FLAG_ACTIVITY_NEW_TASK);

            int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);

            pendingIntent = PendingIntent.getActivity(context, iUniqueId,
                    intent, PendingIntent.FLAG_CANCEL_CURRENT);


        }
        String notificationMode = "";
        Notification.Builder notificationBuilder = new Notification.Builder(
                context).setContentTitle(title).setSmallIcon(icon)
                .setLargeIcon(bitmap).setTicker("" + title)
                .setContentText("" + message).setAutoCancel(true);

        //notificationMode = Prefs.getString(PrefsKeys.NOTIFICATION_MODE, "");
        if (notificationMode.isEmpty()) {
            notificationBuilder.setDefaults(Notification.DEFAULT_SOUND);

        }

        if (intent != null) {
            notificationBuilder.setContentIntent(pendingIntent);
        }

        notificationBuilder.setStyle(new Notification.BigPictureStyle()
                .bigPicture(bitmap).setSummaryText(message));

        /*notificationBuilder.setStyle(new Notification.BigTextStyle()
                .setBigContentTitle("" + title).bigText("" + message));*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setColor(Color.parseColor("#000000"));
        }

        Notification notification = notificationBuilder.build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String channelId = context.getPackageName();

            notificationBuilder.setChannelId(channelId);

            CharSequence name = context.getString(R.string.app_name);
            String description = "Notification";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel mChannel = new NotificationChannel(channelId, name, importance);

            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.parseColor("#000000"));

            mChannel.enableVibration(true);
            notificationManager.createNotificationChannel(mChannel);

        }
        notificationManager.notify(id, notification);

    }

    public static int random(int min, int max) {

        int value = Math
                .round((float) (min + (Math.random() * ((max - min) + 1))));

        if (value > max) {
            return max;
        } else if (value < min) {
            return min;
        } else {
            return value;
        }

    }


    //----------------------- other methods ------------------------------------

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e(TAG, "From: " + remoteMessage.getFrom());

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        /**
         * Fcm Sends notification then retireve from here.
         */


        //message will contain the Push Message
        String title = remoteMessage.getData().get("title");
        String message = remoteMessage.getData().get("message");
        String imageUri = remoteMessage.getData().get("image");
        String url = remoteMessage.getData().get("url");

        bitmap = getBitmapfromUrl(imageUri);

        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("url", url);


        generateNotification(this, title, message, intent, NOTIFICATION_ID, bitmap);


    }


    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }


}

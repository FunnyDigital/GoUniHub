package com.patrick.bitvilltenologies.gounihub.FirebaseService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.view.Display;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.patrick.bitvilltenologies.gounihub.Config.Config;
import com.patrick.bitvilltenologies.gounihub.Helper.NotificationHelper;
import com.patrick.bitvilltenologies.gounihub.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MyFirebaseMessagingService extends FirebaseMessagingService {




    Target target = new Target() {

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
                showNotificationwithIamgeLevel26(bitmap);

showNotificationwithIamge(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showNotificationwithIamgeLevel26(Bitmap bitmap) {

        NotificationHelper helper = new NotificationHelper(getBaseContext() );


       Notification.Builder  builder = helper.getChannel(Config.title,Config.message,bitmap);
       helper.getManager().notify(0,builder.build());
    }

    private void showNotificationwithIamge(Bitmap bitmap) {
        NotificationCompat.BigPictureStyle style= new NotificationCompat.BigPictureStyle();
        style.setSummaryText(Config.message);
        style.bigPicture(bitmap);

        Uri defaultsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(getApplicationContext(), com.patrick.bitvilltenologies.gounihub.Notification.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,0);

        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder)new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(Config.title)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setLargeIcon(bitmap)
                .setSound(defaultsound)
                .setStyle(style);

        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,notificationBuilder.build());


    }


    @Override


    public void onMessageReceived(RemoteMessage remoteMessage) {


       if (remoteMessage.getData()!=null)
           Config.imageLink = remoteMessage.getData().get("i");
           getimage(remoteMessage);
    }

    private void getimage(final RemoteMessage remoteMessage) {
        Config.message = remoteMessage.getNotification().getBody();
        Config.title = remoteMessage.getNotification().getTitle();

        if (remoteMessage.getData() != null) {

        }
        android.os.Handler uiHandler = new android.os.Handler(Looper.getMainLooper());

        uiHandler.post(new Runnable() {
            @Override
            public void run() {

                Picasso.with(getApplicationContext()).load(remoteMessage.getData().get("i")).into(target);

            }
        });




    }
}

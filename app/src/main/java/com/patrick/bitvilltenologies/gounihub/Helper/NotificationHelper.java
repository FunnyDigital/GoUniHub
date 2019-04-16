package com.patrick.bitvilltenologies.gounihub.Helper;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.patrick.bitvilltenologies.gounihub.Config.Config;
import com.patrick.bitvilltenologies.gounihub.Notification;
import com.patrick.bitvilltenologies.gounihub.R;

public class NotificationHelper  extends ContextWrapper {
    private static final String gouni_channel_id = "com.patrick.bitvilltenologies.gounihub";
    private static final String gouni_channel_name = "gounihub";
    private  NotificationManager manager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            createChannel();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel() {

        NotificationChannel channel = new NotificationChannel(gouni_channel_id, gouni_channel_name, NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableVibration(true);
        channel.enableLights(true);
        channel.setLockscreenVisibility(Notification.RECEIVER_VISIBLE_TO_INSTANT_APPS);

        getManager().createNotificationChannel(channel);

    }

    public NotificationManager getManager() {
if ( manager== null)
    manager= (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
return manager;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public android.app.Notification.Builder getChannel (String title, String body, Bitmap bitmap){

        android.app.Notification.Style style = new android.app.Notification.BigPictureStyle().bigPicture(bitmap);

        Uri defaultsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(getApplicationContext(),Notification.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,0);


      return new android.app.Notification.Builder(getApplicationContext(),gouni_channel_id)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(Config.title)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setSound(defaultsound)
                .setStyle(style);
    }


}

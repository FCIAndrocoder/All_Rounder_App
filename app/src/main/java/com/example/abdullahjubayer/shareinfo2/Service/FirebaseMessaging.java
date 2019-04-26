package com.example.abdullahjubayer.shareinfo2.Service;


import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.abdullahjubayer.shareinfo2.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

import static com.example.abdullahjubayer.shareinfo2.Service.App.Chenal_ID;

public class FirebaseMessaging extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String,String> data=remoteMessage.getData();
        Log.e("data",data.toString());
        String title="Titiled" ;
        String message="Message";

        Random random=new Random(100);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notification_)
                .setContentTitle( title)
                .setContentText(message)
                .setAutoCancel(true)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setLights(Color.RED, 3000, 3000)
                .setWhen(System.currentTimeMillis())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                //.setContentIntent(intent)
                ;


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(random.nextInt(), builder.build());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder builder1 = new NotificationCompat.Builder(this,Chenal_ID)
                    .setSmallIcon(R.drawable.ic_notification_)
                    .setContentTitle( title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                    .setLights(Color.RED, 3000, 3000)
                    .setCategory(NotificationCompat.CATEGORY_ALARM)
                    .setWhen(System.currentTimeMillis())
                    //.setContentIntent(intent)
                    ;


            NotificationManagerCompat notificationManager1 = NotificationManagerCompat.from(this);
            notificationManager1.notify(random.nextInt(), builder1.build());
        }
        else {
            //PendingIntent intent=PendingIntent.getActivity(this,0,new Intent(this, Post_activity.class),0);

        }
    }
}

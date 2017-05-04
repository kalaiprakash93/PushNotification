package com.selfservit.notify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.selfservit.msupport.MainActivity;
import com.selfservit.msupport.R;

/**
 * Created by HP on 05-04-17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        //Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        sendNotification((String) remoteMessage.getData().values().toArray()[0]);
       // sendNotification(remoteMessage.getNotification().getBody());
    }

    private void sendNotification(String body) {
        Intent intent = new Intent(this,mPushNotification.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0/*Request Code*/,intent,PendingIntent.FLAG_ONE_SHOT);
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notifiBuilder = new NotificationCompat. Builder(this)
                .setSmallIcon(R.drawable.icon)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setGroupSummary(true)
                .setContentTitle("New Ticket")
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager =(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /*id of Notification*/, notifiBuilder.build());
    }
}

package com.selfservit.notify;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by HP on 05-04-17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    static ArrayList<String> notifications = new ArrayList<String>();
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        //Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        String title,text,id;
        if (remoteMessage.getNotification() != null) {
            title = remoteMessage.getNotification().getTitle();
            text = remoteMessage.getNotification().getBody();
            id = remoteMessage.getMessageId();
        }else{
            title = remoteMessage.getData().get("title");
            text = remoteMessage.getData().get("message");
            id = remoteMessage.getData().get("id");
        }
        if(TextUtils.isEmpty(id)){
            Random rand = new Random();
            int  n = rand.nextInt(50) + 1;
            id = Integer.toString(n);
        }
        sendNotification(title, text,id);
    }

    private void sendNotification(String title,String messageBody,String messageid) {


        Intent intent = new Intent(this, mPushNotification.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
        PendingIntent pendingIntent = PendingIntent.getActivity(this, messageid.hashCode()    /*Request Code*/, intent, PendingIntent.FLAG_ONE_SHOT );
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
       // Bitmap bitmap = BitmapFactory.decodeResource(getResources(), getApplicationContext().getApplicationInfo().icon);
        int count= notifications.size();
        NotificationCompat.Builder notifiBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(getApplicationContext().getApplicationInfo().icon)
                // .setLargeIcon(bitmap);
                //.setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                .setContentTitle("MSupport")
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setContentIntent(pendingIntent);
        notifications.add(messageBody);
        if(count == 0){
            count = 1;
          //  notifiBuilder.setContentText(count +" "+title +" from Support.")
                   notifiBuilder . setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(notifications.get(0)));
        }else{
            count ++ ;
            NotificationCompat.InboxStyle inboxStyle =
                    new NotificationCompat.InboxStyle();
            // Sets a title for the Inbox in expanded layout
            notifiBuilder.setContentText(count +" "+title +" from Support.");
            inboxStyle.setBigContentTitle("MSupport");
            inboxStyle.setSummaryText("You Have "+ count +" "+title);
            // Moves events into the expanded layout
           // notifications.add(messageBody);
            for (int i=0; i < notifications.size(); i++) {
                inboxStyle.addLine(notifications.get(i));
            }
            // Moves the expanded layout object into the notification object.
            notifiBuilder.setStyle(inboxStyle);
        }
       /* NotificationCompat.Builder notifiBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(getApplicationContext().getApplicationInfo().icon)
               // .setLargeIcon(bitmap);
               //.setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                .setContentTitle("MSupport")*/


      /*  NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();
        // Sets a title for the Inbox in expanded layout
        inboxStyle.setBigContentTitle("MSupport");
        inboxStyle.setSummaryText("You Have "+ count +" "+title);
        // Moves events into the expanded layout
        notifications.add(messageBody);
        for (int i=0; i < notifications.size(); i++) {
            inboxStyle.addLine(notifications.get(i));
        }
        // Moves the expanded layout object into the notification object.
        notifiBuilder.setStyle(inboxStyle);*/
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /*id of Notification*/, notifiBuilder.build());
    }
}

package com.strechyourbody.rammp.stretchbody.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.strechyourbody.rammp.stretchbody.R;

/**
 * Created by Mathias on 8/6/17.
 */

//Starts a notification
public class NotificationService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle prevBundle = intent.getExtras();
        String title = prevBundle.getString("title");
        String content = prevBundle.getString("content");

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.red_heart)//Hay que cambiar este icono
                        .setContentTitle(title)
                        .setContentText(content);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }

}

package com.strechyourbody.rammp.stretchbody.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.strechyourbody.rammp.stretchbody.Activities.ExerciseDetailActivity;
import com.strechyourbody.rammp.stretchbody.Activities.ProfileUserActivity;
import com.strechyourbody.rammp.stretchbody.Activities.ProgramDetailActivity;
import com.strechyourbody.rammp.stretchbody.R;

/**
 * Created by mbp on 8/26/17.
 */

public class ProgramAlarmService extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle prevBundle = intent.getExtras();
        String idProgram = prevBundle.getString("idProgram");
        String nameProgram = prevBundle.getString("nameProgram");

        Intent notificationIntent = new Intent(context, ProgramDetailActivity.class);
        notificationIntent.putExtra("idProgram",idProgram);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(ProgramDetailActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(100,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.red_heart)//Hay que cambiar este icono
                        .setContentTitle(nameProgram)
                        .setContentText("Ya es hora de estirar el cuerpo.")
                        .setContentIntent(pendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }
}

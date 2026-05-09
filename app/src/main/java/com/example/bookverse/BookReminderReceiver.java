package com.example.bookverse;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BookReminderReceiver extends BroadcastReceiver {

    private static final long INTERVAL = 60 * 1000; // 1 minute

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper.showBookReminderNotification(context);
    }

    public static void scheduleReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, BookReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + INTERVAL,
                INTERVAL,
                pendingIntent
        );
    }

}


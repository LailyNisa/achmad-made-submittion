package com.achmad.madeacademy.moviecataloguemvp.utils;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.achmad.madeacademy.moviecataloguemvp.R;
import com.achmad.madeacademy.moviecataloguemvp.data.remote.model.movie.Result;
import com.achmad.madeacademy.moviecataloguemvp.ui.discover.DiscoverActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String TYPE_REPEATING = "Daily Reminder Catalogue";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_MESSAGE_ARRAYLIST = "message_arraylist";
    public static final String EXTRA_TYPE = "type";
    public static final String TYPE_ONE_TIME = "Release Reminder Catalogue";
    private final static String GROUP_KEY_EMAILS = "group_key_release";
    private final static int NOTIFICATION_REQUEST_CODE = 200;
    private static final int MAX_NOTIFICATION = 5;
    private final List<Result> stackNotif = new ArrayList<>();
    private int idNotification = 0;


    private final int ID_ONETIME = 100;
    private final int ID_REPEATING = 101;
    private String DATE_FORMAT = "yyyy-MM-dd";
    private String TIME_FORMAT = "HH:mm";

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        List<Result> messageObject = intent.getParcelableArrayListExtra(EXTRA_MESSAGE_ARRAYLIST);

        String title = type.equalsIgnoreCase(TYPE_ONE_TIME) ? TYPE_ONE_TIME : TYPE_REPEATING;
        int notifId = type.equalsIgnoreCase(TYPE_ONE_TIME) ? ID_ONETIME : ID_REPEATING;
        // an Intent broadcast.
        switch (notifId) {
            case ID_REPEATING:
                Toast.makeText(context, "Daily success", Toast.LENGTH_SHORT).show();
                showNotification(context, title, message, ID_REPEATING);
                break;
            case ID_ONETIME:
                Toast.makeText(context, "Release success", Toast.LENGTH_SHORT).show();
                showNotification(context, title, message, ID_ONETIME);
                break;
            default:
                Log.d("Error Alarm", "Alarm Error");
        }
//        if (notifId == ID_REPEATING){
//            Toast.makeText(context,"Daily success",Toast.LENGTH_SHORT).show();
//            showNotification(context, title, message, notifId);
//        }else {
//            Toast.makeText(context,"Release success",Toast.LENGTH_SHORT).show();
//            showNotification(context, title, message, notifId);
//        }


    }

    public void setRepeatingAlarm(Context context, String type, String time, String message) {
        if (isDateInvalid(time, TIME_FORMAT)) return;

        AlarmManager alarmReceiver = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);

        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0);
        if (alarmReceiver != null) {
            alarmReceiver.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        Toast.makeText(context, "Daily Reminder set up", Toast.LENGTH_SHORT).show();
    }

    public void setRepeatingAlarmRelease(Context context, String type, String time, String message) {
        if (isDateInvalid(time, TIME_FORMAT)) return;

        AlarmManager alarmReceiver = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
//        intent.putParcelableArrayListExtra(EXTRA_MESSAGE_ARRAYLIST, (ArrayList<? extends Parcelable>) message);
        intent.putExtra(EXTRA_TYPE, type);

        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_ONETIME, intent, 0);
        if (alarmReceiver != null) {
            alarmReceiver.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        Toast.makeText(context, "Release Reminder set up", Toast.LENGTH_SHORT).show();
    }

    public boolean isDateInvalid(String date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
            df.setLenient(false);
            df.parse(date);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }

    public void showNotification(Context context, String title, String message, int notifId) {
        String channel_id = "Channel_1";
        String channel_name = "Navigation channel";
        long[] vibrationPattern = new long[]{1000, 1000, 1000, 1000, 1000};
        Intent notifMainIntent = new Intent(context.getApplicationContext(), DiscoverActivity.class);
        PendingIntent pendingIntent = TaskStackBuilder.create(context)
                .addParentStack(DiscoverActivity.class)
                .addNextIntent(notifMainIntent)
                .getPendingIntent(notifId, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channel_id)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_add_alert_yellow_24dp)
                .setColor(ContextCompat.getColor(context, android.R.color.background_dark))
                .setVibrate(vibrationPattern)
                .setSound(alarmSound)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channel_id, channel_name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(vibrationPattern);
            builder.setChannelId(channel_id);
            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();
        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }
    }

    private void sendNotif(Context context, List<Result> message, int idNotification) {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_add_alert_yellow_24dp);
        Intent intent = new Intent(context, DiscoverActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), ID_ONETIME, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder;
        stackNotif.addAll(message);
        //Melakukan pengecekan jika idNotification lebih kecil dari Max Notif
        String CHANNEL_ID = "channel_01";
        String CHANNEL_NAME = "release channel";
        if (idNotification < MAX_NOTIFICATION) {
            mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle("New Email from " + stackNotif.get(idNotification).getTitle())
                    .setContentText(stackNotif.get(idNotification).getOverview())
                    .setSmallIcon(R.drawable.ic_add_alert_yellow_24dp)
                    .setLargeIcon(largeIcon)
                    .setGroup(GROUP_KEY_EMAILS)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
        } else {
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                    .addLine("Release Movie Today " + stackNotif.get(idNotification).getTitle())
                    .addLine("Release Movie Today " + stackNotif.get(idNotification - 1).getTitle())
                    .setBigContentTitle(idNotification + " new Release Movie")
                    .setSummaryText("mail@dicoding");
            mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(idNotification + " Release Movie Today")
                    .setContentText("Don't miss this movie !!!")
                    .setSmallIcon(R.drawable.ic_add_alert_yellow_24dp)
                    .setGroup(GROUP_KEY_EMAILS)
                    .setGroupSummary(true)
                    .setContentIntent(pendingIntent)
                    .setStyle(inboxStyle)
                    .setAutoCancel(true);
        }
         /*
        Untuk android Oreo ke atas perlu menambahkan notification channel
        Materi ini akan dibahas lebih lanjut di modul extended
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /* Create or update. */
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            mBuilder.setChannelId(CHANNEL_ID);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = mBuilder.build();

        if (mNotificationManager != null) {
            mNotificationManager.notify(idNotification, notification);
        }
    }

    public void cancelAlarm(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        int requestCode = type.equalsIgnoreCase(TYPE_ONE_TIME) ? ID_ONETIME : ID_REPEATING;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();
        switch (requestCode) {
            case ID_ONETIME:
                Toast.makeText(context, "Release Reminder canceled", Toast.LENGTH_SHORT).show();
                break;
            case ID_REPEATING:
                Toast.makeText(context, "Daily Reminder canceled", Toast.LENGTH_SHORT).show();
                break;
            default:
                Log.d("Error Alarm", "Alarm Error");
        }
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }


    }

    public boolean isAlarmSet(Context context, String type) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        int requestCode = type.equalsIgnoreCase(TYPE_ONE_TIME) ? ID_ONETIME : ID_REPEATING;
        return PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_NO_CREATE) != null;
    }
}

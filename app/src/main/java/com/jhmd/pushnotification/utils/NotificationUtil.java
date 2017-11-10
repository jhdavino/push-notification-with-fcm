package com.jhmd.pushnotification.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateUtils;
import android.widget.RemoteViews;

import com.jhmd.pushnotification.R;
import com.jhmd.pushnotification.views.MainActivity;

import java.util.Map;

/**
 * Created by josehenrique on 09/11/17.
 */

public class NotificationUtil {

    private static final String TAG = "NotificationUtil";

    /**
     * Create and show a simple notification containing the received FCM message.
     * @param context the context application.
     * @param message FCM message body received.
     */
    public static void showStandardNotification(Context context, String message) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getString(R.string.default_notification_channel_id))
                .setSmallIcon(R.drawable.ic_phonelink_ring_primary_24dp)
                .setContentTitle("Notification with FCM By @jhdavino")
                .setContentText(message)
                // notification will be dismissed when tapped
                .setAutoCancel(true);

        showNotification(context, builder.build(), 0);
    }


    /**
     * Create and show a notification with custom layout containing the received FCM message.
     * @param context the context application.
     * @param data FCM message body received.
     */
    public static void showCustomContentViewNotification(Context context, Map<String, String> data) {

        RemoteViews remoteViews = createRemoteViews(context,
                R.layout.notification_custom_content,
                R.drawable.ic_phonelink_ring_primary_24dp,
                data.get("title"), data.get("message"));

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getString(R.string.default_notification_channel_id))
                .setSmallIcon(R.drawable.ic_phonelink_ring_primary_24dp)
                .setContentTitle(data.get("title"))
                .setContentText(data.get("message"))
                // setting the ContentLayout with remoteViews
                .setContent(remoteViews)
                // notification will be dismissed when tapped
                .setAutoCancel(true)
                // tapping notification will open MainActivity
                .setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0))
                // setting style to DecoratedCustomViewStyle() is necessary for custom views to display
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle());

        showNotification(context, builder.build(), 0);
    }


    private static RemoteViews createRemoteViews(Context context, int layout, int iconResource, String title, String message) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), layout);
                    remoteViews.setImageViewResource(R.id.image_icon, iconResource);
                    remoteViews.setTextViewText(R.id.text_title, title);
                    remoteViews.setTextViewText(R.id.text_message, message);
                    remoteViews.setTextViewText(R.id.timestamp, DateUtils.formatDateTime(context, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));

        return remoteViews;
    }


    private static void showNotification(Context context, Notification notification, int id) {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, notification);
    }
}

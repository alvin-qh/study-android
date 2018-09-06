package alvin.base.service.foreground.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.IBinder;
import android.support.annotation.Nullable;

import alvin.base.service.R;
import alvin.base.service.common.broadcasts.ServiceBroadcasts;
import dagger.android.DaggerService;

public class ForegroundService extends DaggerService {
    private static final String PRIMARY_CHANNEL = "default";
//    private static final String SECONDARY_CHANNEL = "second";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotification();
        sendBroadcast(new Intent(ServiceBroadcasts.ACTION_SERVICE_CREATED));
    }

    private void createNotification() {
        final Notification.Builder notificationBuilder;
        final NotificationChannel channel = new NotificationChannel(
                PRIMARY_CHANNEL,
                PRIMARY_CHANNEL,
                NotificationManager.IMPORTANCE_DEFAULT);

        final NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager == null) {
            return;
        }

        notificationManager.createNotificationChannel(channel);
        notificationBuilder = new Notification.Builder(this, PRIMARY_CHANNEL);

        final PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                new Intent(ServiceBroadcasts.ACTION_SERVICE_NOTIFY), 0);

        final Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("Foreground Service")
                .setContentText("This is an android Foreground Service demo")
                .setTicker("Ticker")
                .setTimeoutAfter(2000)
                .setShowWhen(true)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(Icon.createWithResource(this, R.drawable.ic_action_setting))
                .setLargeIcon(Icon.createWithResource(this, R.drawable.ic_action_setting))
                .setContentIntent(pendingIntent)
                .build();
        notification.flags |= Notification.FLAG_NO_CLEAR;

        startForeground(1, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sendBroadcast(new Intent(ServiceBroadcasts.ACTION_SERVICE_DESTROYED));
    }
}

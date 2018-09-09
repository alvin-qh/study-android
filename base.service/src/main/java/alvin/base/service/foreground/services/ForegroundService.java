package alvin.base.service.foreground.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.IBinder;
import android.support.annotation.Nullable;

import alvin.base.service.R;

import static alvin.base.service.common.broadcasts.ServiceBroadcasts.ACTION_SERVICE_CREATED;
import static alvin.base.service.common.broadcasts.ServiceBroadcasts.ACTION_SERVICE_DESTROYED;
import static alvin.base.service.common.broadcasts.ServiceBroadcasts.ACTION_SERVICE_NOTIFY;

public class ForegroundService extends Service {
    private static final String CHANNEL_ID = "default";
    private boolean isForeground = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sendBroadcast(new Intent(ACTION_SERVICE_CREATED));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isForeground) {
            destroyNotification();
        } else {
            createNotification();
        }
        return START_STICKY;
    }

    private void destroyNotification() {
        stopForeground(true);
        isForeground = false;
    }

    private void createNotification() {
        final NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (manager == null) {
            return;
        }

        final NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID,
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);
        channel.enableVibration(true);

        manager.createNotificationChannel(channel);

        final Notification.Builder builder = new Notification.Builder(this, CHANNEL_ID);
        final PendingIntent intent = PendingIntent.getBroadcast(this, 100,
                new Intent(ACTION_SERVICE_NOTIFY), PendingIntent.FLAG_UPDATE_CURRENT);

        final Notification notification = builder
                .setContentTitle("Foreground Service")
                .setContentText("A foreground service is running...")
                .setContentIntent(intent)
                .setTicker("Ticker")
                .setOngoing(true)
                .setWhen(System.currentTimeMillis())
                .setShowWhen(true)
                .setSmallIcon(Icon.createWithResource(this, R.drawable.ic_action_setting))
                .setLargeIcon(Icon.createWithResource(this, R.drawable.ic_action_setting))
                .build();

//        notification.flags |= Notification.FLAG_NO_CLEAR;
        startForeground(1, notification);
        isForeground = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        sendBroadcast(new Intent(ACTION_SERVICE_DESTROYED));
    }
}

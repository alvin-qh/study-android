package alvin.base.service.foreground.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import alvin.base.service.R;
import alvin.base.service.common.broadcasts.ServiceBroadcasts;
import alvin.lib.common.utils.Versions;
import dagger.android.DaggerService;

public class ForegroundService extends DaggerService {

    @Inject Versions version;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sendBroadcast(new Intent(ServiceBroadcasts.ACTION_SERVICE_CREATED));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotification();
        return super.onStartCommand(intent, flags, startId);
    }

    private void createNotification() {
        final Notification.Builder notificationBuilder;

        if (version.isEqualOrGreatThan()) {
            final String channelId = getString(R.string.notification_channel);
            final String channelName = "";

            final NotificationChannel channel = new NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH);

            final NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }

            notificationBuilder = new Notification.Builder(this, channelId);
        } else {
            notificationBuilder = new Notification.Builder(this);
        }

        final PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                new Intent(ServiceBroadcasts.ACTION_SERVICE_NOTIFY), 0);

        final Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("")
                .setContentText("")
                .setTicker("")
                .setSmallIcon(R.mipmap.ic_launcher_round)
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

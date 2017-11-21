package alvin.base.service.foreground.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import alvin.base.service.R;
import alvin.base.service.common.broadcasts.ServiceBroadcasts;
import dagger.android.DaggerService;

public class ForegroundService extends DaggerService {

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
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1,
                new Intent(ServiceBroadcasts.ACTION_SERVICE_NOTIFY), 0);

        final String channelId = getString(R.string.notification_channel);
        Notification notification = new Notification.Builder(this, channelId)
                .setOngoing(true)
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

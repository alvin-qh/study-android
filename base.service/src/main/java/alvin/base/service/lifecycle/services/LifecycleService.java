package alvin.base.service.lifecycle.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import alvin.base.service.lifecycle.broadcasts.LifecycleBroadcasts;
import dagger.android.AndroidInjection;

public class LifecycleService extends Service {
    private static final String TAG = LifecycleService.class.getSimpleName();

    public static final String EXTRA_ARGUMENTS_MODE = "mode";

    private int serviceStartId = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "Service is bind");

        return new Binder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        boolean result = super.onUnbind(intent);
        Log.d(TAG, "Service is unbind");

        return result;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        AndroidInjection.inject(this);

        sendBroadcast(new Intent(LifecycleBroadcasts.ACTION_SERVICE_CREATED));

        Log.d(TAG, "Service is created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        this.serviceStartId = startId;
        int result = getStartResult(intent, flags);

        Log.d(TAG, "Service is started, with type " + result);
        return result;
    }

    private int getStartResult(Intent intent, int flags) {
        int result;

        switch (flags) {
        case START_FLAG_RETRY:
            result = START_STICKY;
            break;
        case START_FLAG_REDELIVERY:
            result = START_REDELIVER_INTENT;
            break;
        default:
            result = intent.getIntExtra(EXTRA_ARGUMENTS_MODE, START_STICKY);
        }

        return result;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        sendBroadcast(new Intent(LifecycleBroadcasts.ACTION_SERVICE_DESTROYED));

        Log.d(TAG, "Service is destroyed");
    }

    public void stopMySelf() {
        stopSelf(serviceStartId);
    }
}

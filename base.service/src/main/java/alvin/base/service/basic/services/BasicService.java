package alvin.base.service.basic.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.common.base.Strings;

import java.time.ZoneId;

import javax.inject.Inject;

import alvin.base.service.basic.broadcasts.BasicBroadcasts;
import alvin.lib.common.rx.RxManager;
import dagger.android.AndroidInjection;

public class BasicService extends Service {
    private static final String TAG = BasicService.class.getSimpleName();

    public static final String EXTRA_ARGUMENTS_TIMEZONE = "time_zone";
    public static final String EXTRA_ARGUMENTS_MODE = "mode";

    private int serviceStartId = 0;

    private ZoneId zoneId;

    @Inject RxManager rxManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new BasicServiceBinder(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        AndroidInjection.inject(this);

        Log.d(TAG, "Service is created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        this.serviceStartId = startId;

        zoneId = getTimezoneFromIntent(intent);

        int result = getStartResult(intent, flags);

        sendBroadcast(new Intent(BasicBroadcasts.ACTION_SERVICE_STARTED));

        Log.d(TAG, "Service is started, get zone id is " + zoneId);

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

    private ZoneId getTimezoneFromIntent(@Nullable Intent intent) {
        String timeZone = null;

        if (intent != null) {
            timeZone = intent.getStringExtra(EXTRA_ARGUMENTS_TIMEZONE);
        }

        if (Strings.isNullOrEmpty(timeZone)) {
            timeZone = "Asia/Shanghai";
        }

        return ZoneId.of(timeZone);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        rxManager.clear();

        Log.d(TAG, "Service is destroyed");
    }

    public void stopMySelf() {
        stopSelf(serviceStartId);
    }

    @NonNull
    public RxManager getRxManager() {
        return rxManager;
    }

    @NonNull
    public ZoneId getZoneId() {
        return zoneId;
    }
}

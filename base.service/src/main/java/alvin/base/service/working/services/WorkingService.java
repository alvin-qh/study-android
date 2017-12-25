package alvin.base.service.working.services;

import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Strings;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import alvin.lib.common.rx.RxDecorator;
import dagger.android.DaggerService;
import io.reactivex.Observable;

public class WorkingService extends DaggerService {
    public static final String EXTRA_ARG_ZONE = "zone";

    @Inject RxDecorator rxDecorator;

    private ZoneId zoneId;

    private static final Set<OnServiceCallbackListener> listeners = new HashSet<>();

    private transient boolean destroyed = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        destroyed = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        getZoneIdFromIntent(intent);

        startServiceLoop();

        return START_REDELIVER_INTENT;
    }

    private void getZoneIdFromIntent(Intent intent) {
        final String zoneId = intent.getStringExtra(EXTRA_ARG_ZONE);
        if (Strings.isNullOrEmpty(zoneId)) {
            this.zoneId = ZoneOffset.systemDefault();
        } else {
            this.zoneId = ZoneId.of(zoneId);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyed = true;
    }

    private void startServiceLoop() {
        rxDecorator.<LocalDateTime>de(
                Observable.interval(0L, 1, TimeUnit.SECONDS).flatMap(ignore ->
                        emitter -> {
                            if (!destroyed) {
                                LocalDateTime time = LocalDateTime.now(zoneId);
                                emitter.onNext(time);
                            } else {
                                emitter.onComplete();
                            }
                        })

        ).subscribe(time ->
                listeners.forEach(listener -> listener.onTimeMessage(time)));
    }

    public static void addOnServiceCallbackListener(@NonNull OnServiceCallbackListener listener) {
        listeners.add(listener);
    }

    public static void removeOnServiceCallbackListener(@NonNull OnServiceCallbackListener listener) {
        listeners.remove(listener);
    }

    public interface OnServiceCallbackListener {
        void onTimeMessage(LocalDateTime time);
    }
}

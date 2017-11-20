package alvin.base.service.working.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Strings;

import java.lang.ref.WeakReference;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import alvin.lib.common.rx.ObservableSubscriber;
import alvin.lib.common.rx.RxManager;
import dagger.android.AndroidInjection;

public class WorkingService extends Service {
    public static final String EXTRA_ARG_ZONE = "zone";

    @Inject
    RxManager rxManager;

    private ZoneId zoneId;

    private static WeakReference<WorkingService> serviceRef;

    private Set<OnServiceCallbackListener> listeners = new HashSet<>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        serviceRef = new WeakReference<>(this);

        AndroidInjection.inject(this);
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

        serviceRef.clear();
        rxManager.clear();
    }

    public static boolean isAvailable() {
        return serviceRef.get() != null;
    }

    public static WeakReference<WorkingService> getServiceRef() {
        return serviceRef;
    }

    private void startServiceLoop() {
        final ObservableSubscriber<LocalDateTime> subscriber =
                rxManager.interval(0L, 1, TimeUnit.SECONDS, emitter -> {
                    if (isAvailable()) {
                        LocalDateTime time = LocalDateTime.now(zoneId);
                        emitter.onNext(time);
                    } else {
                        emitter.onComplete();
                    }
                });

        subscriber.subscribe(
                time -> listeners.forEach(listener -> listener.onTimeMessage(time)),
                throwable -> { },
                () -> { }
        );
    }

    public void addOnServiceCallbackListener(@NonNull OnServiceCallbackListener listener) {
        listeners.add(listener);
    }

    public void removeOnServiceCallbackListener(@NonNull OnServiceCallbackListener listener) {
        listeners.remove(listener);
    }

    public interface OnServiceCallbackListener {
        void onTimeMessage(LocalDateTime time);
    }
}

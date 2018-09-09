package alvin.base.service.working.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class WorkingService extends Service {
    public static final String EXTRA_ARG_ZONE = "zone";

    private static final List<WeakReference<OnServiceCallbackListener>> listeners = new LinkedList<>();

    private ZoneId zoneId;

    private Disposable disposable;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
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

        if (disposable != null) {
            disposable.dispose();
        }
    }

    private void startServiceLoop() {
        disposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(ignore -> {
                    synchronized (listeners) {
                        final Iterator<WeakReference<OnServiceCallbackListener>> iter = listeners.iterator();
                        while (iter.hasNext()) {
                            final OnServiceCallbackListener l = iter.next().get();
                            if (l == null) {
                                iter.remove();
                            }
                        }
                        return Lists.newArrayList(listeners);
                    }
                })
                .subscribe(listeners -> {
                    if (!listeners.isEmpty()) {
                        LocalDateTime now = LocalDateTime.now(zoneId);
                        listeners.stream()
                                .map(Reference::get)
                                .forEach(l -> l.onTimeMessage(now));
                    }
                });
    }

    public static void addOnServiceCallbackListener(@NonNull OnServiceCallbackListener listener) {
        synchronized (listeners) {
            listeners.add(new WeakReference<>(listener));
        }
    }

    public static void removeOnServiceCallbackListener(@NonNull OnServiceCallbackListener listener) {
        synchronized (listeners) {
            final Iterator<WeakReference<OnServiceCallbackListener>> iter = listeners.iterator();
            while (iter.hasNext()) {
                final OnServiceCallbackListener l = iter.next().get();
                if (l == null || l == listener) {
                    iter.remove();
                }
            }
        }
    }

    public interface OnServiceCallbackListener {
        void onTimeMessage(LocalDateTime time);
    }
}

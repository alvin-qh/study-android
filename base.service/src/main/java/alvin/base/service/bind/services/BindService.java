package alvin.base.service.bind.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.ArraySet;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class BindService extends Service {
    public static final String EXTRA_ARG_ZONE = "zone";

    private ZoneId zoneId;

    private final Set<WeakReference<Consumer<LocalDateTime>>> callbacks = new ArraySet<>();
    private Disposable disposable;

    /**
     * If on one to start this service, This method will be invoke when this service
     * has been bind first time.
     */
    @Override
    public void onCreate() {
        super.onCreate();

        startWorking();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        disposable.dispose();
        callbacks.clear();   // clear all callback functions, stop callback
    }

    private void startWorking() {
        disposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(ignore -> {
                    synchronized (callbacks) {
                        final Iterator<WeakReference<Consumer<LocalDateTime>>> iter = callbacks.iterator();
                        while (iter.hasNext()) {
                            final Consumer<LocalDateTime> callback = iter.next().get();
                            if (callback == null) {
                                iter.remove();
                            }
                        }
                        return Lists.newArrayList(callbacks);
                    }
                })
                .subscribe(callbacks -> {
                    if (!callbacks.isEmpty()) {
                        LocalDateTime now = LocalDateTime.now(zoneId);
                        callbacks.stream()
                                .map(Reference::get)
                                .forEach(callback -> callback.accept(now));
                    }
                });
    }

    /**
     * When service has been bind, return an object of IBinder.
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        final String zoneId = intent.getStringExtra(EXTRA_ARG_ZONE);
        if (Strings.isNullOrEmpty(zoneId)) {
            this.zoneId = ZoneOffset.systemDefault();
        } else {
            this.zoneId = ZoneId.of(zoneId);
        }

        // return the instance of IBinder interface
        return new ServiceBinder();
    }

    public class ServiceBinder extends Binder {
        private WeakReference<Consumer<LocalDateTime>> identity;

        /**
         * Add a callback function to service.
         */
        public void addTimeCallback(@NonNull Consumer<LocalDateTime> consumer) {
            identity = new WeakReference<>(consumer);
            synchronized (callbacks) {
                callbacks.add(identity);
            }
        }

        /**
         * Remove callback function from service.
         */
        public void removeTimeCallback() {
            synchronized (callbacks) {
                callbacks.remove(identity);
            }
        }
    }
}

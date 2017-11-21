package alvin.base.service.bind.services;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
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
import java.util.function.Consumer;

import javax.inject.Inject;

import alvin.base.service.bind.presenters.BindPresenter;
import alvin.lib.common.rx.ObservableSubscriber;
import alvin.lib.common.rx.RxManager;
import dagger.android.DaggerService;

public class BindService extends DaggerService {
    public static final String EXTRA_ARG_ZONE = "zone";

    @Inject RxManager rxManager;

    private boolean available = false;

    private ZoneId zoneId;

    private final ServiceBinder binder = new ServiceBinder();
    private final Set<Consumer<LocalDateTime>> timeCallback = new HashSet<>();

    /**
     * If on one to start this service, This method will be invoke when this service
     * has been bind first time.
     */
    @Override
    public void onCreate() {
        super.onCreate();

        available = true;
        startWorking();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        available = false;
        timeCallback.clear();   // clear all callback functions, stop callback
        rxManager.clear();
    }

    private void startWorking() {
        final ObservableSubscriber<LocalDateTime> subscriber =
                rxManager.interval(0, 1, TimeUnit.SECONDS, emitter -> {
                    if (available) {
                        emitter.onNext(LocalDateTime.now(zoneId));
                    } else {
                        emitter.onComplete();
                    }
                });

        subscriber.subscribe(time ->
                // call every callback functions and pass result
                timeCallback.forEach(consumer -> consumer.accept(time))
        );
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
        return binder;
    }

    public class ServiceBinder extends Binder {

        /**
         * Add a callback function to service.
         *
         * @see BindPresenter#bindService(Context)
         */
        public void addTimeCallback(@NonNull Consumer<LocalDateTime> consumer) {
            timeCallback.add(consumer);
        }

        /**
         * Remove callback function from service.
         *
         * @see BindPresenter#unbindService(Context)
         */
        public void remoteTimeCallback(@NonNull Consumer<LocalDateTime> consumer) {
            timeCallback.remove(consumer);
        }
    }
}

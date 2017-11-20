package alvin.base.service.bind.services;

import android.app.Service;
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

import alvin.lib.common.rx.ObservableSubscriber;
import alvin.lib.common.rx.RxManager;
import dagger.android.AndroidInjection;

public class BindService extends Service {
    public static final String EXTRA_ARG_ZONE = "zone";

    @Inject RxManager rxManager;

    private boolean available = false;

    private ZoneId zoneId;

    private final ServiceBinder binder = new ServiceBinder();
    private final Set<Consumer<LocalDateTime>> timeCallback = new HashSet<>();

    @Override
    public void onCreate() {
        super.onCreate();

        AndroidInjection.inject(this);

        available = true;
        startWorking();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        available = false;
        timeCallback.clear();
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

        subscriber.subscribe(time -> timeCallback.forEach(consumer -> consumer.accept(time)));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        final String zoneId = intent.getStringExtra(EXTRA_ARG_ZONE);
        if (Strings.isNullOrEmpty(zoneId)) {
            this.zoneId = ZoneOffset.systemDefault();
        } else {
            this.zoneId = ZoneId.of(zoneId);
        }
        return binder;
    }


    public class ServiceBinder extends Binder {
        public void addTimeCallback(@NonNull Consumer<LocalDateTime> consumer) {
            timeCallback.add(consumer);
        }

        public void remoteTimeCallback(@NonNull Consumer<LocalDateTime> consumer) {
            timeCallback.remove(consumer);
        }
    }
}

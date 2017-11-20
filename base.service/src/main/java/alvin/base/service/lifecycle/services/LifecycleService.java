package alvin.base.service.lifecycle.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import alvin.base.service.common.broadcasts.ServiceBroadcasts;
import alvin.base.service.lifecycle.presenters.LifecyclePresenter;
import dagger.android.AndroidInjection;

public class LifecycleService extends Service {
    private static final String TAG = LifecycleService.class.getSimpleName();

    public static final String EXTRA_ARGUMENTS_MODE = "mode";

    private int serviceStartId = 0;

    /**
     * When service is bind.
     * <p>
     * The instance of {@link IBinder} must be returned.
     * The instance of {@link IBinder} is a communication of this service
     * <ul>
     * <li>If service has not been created, created the service, and onCreate method will be called.</li>
     * <li>If service has been created, increase the <b>Reference Counter</b> of this service.</li>
     * </ul>
     *
     * @see LifecyclePresenter#bindService(Context)
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "Service is bind");

        return new Binder();
    }

    /**
     * Unbind this service.
     * <p>
     * Decrease the <b>Reference Counter</b> of this service, if <b>Reference Counter</b> is
     * zero, then:
     * <ul>
     * <li>If service was started by {@link Context#startService(Intent)}, the service
     * will be destroyed after {@link Context#stopService(Intent)} has been called</li>
     * <li>If service was started by {@link Context#bindService(Intent, ServiceConnection, int)},
     * the service will destroyed immediately</li>
     * </ul>
     *
     * @see LifecyclePresenter#unbindService(Context)
     */
    @Override
    public boolean onUnbind(Intent intent) {
        boolean result = super.onUnbind(intent);
        Log.d(TAG, "Service is unbind");

        return result;
    }

    /**
     * When service is created.
     * <p>
     * If {@link Context#startService(Intent)} or
     * {@link Context#bindService(Intent, ServiceConnection, int)} is called first time, the
     * service will be created, and this method will be called once time.
     */
    @Override
    public void onCreate() {
        super.onCreate();

        AndroidInjection.inject(this);

        sendBroadcast(new Intent(ServiceBroadcasts.ACTION_SERVICE_CREATED));

        Log.d(TAG, "Service is created");
    }

    /**
     * When service has been started.
     * <p>
     * If {@link Context#startService(Intent)} called, this method will be call once, and
     * some arguments can pass by {@link Intent} object.
     * <p>
     * Service can be started many times, but can be created only once before service destroyed,
     * so this method will be called many times too.
     */
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

    /**
     * When service was destroyed.
     * <p>
     * If the <b>Reference Counter</b> is zero and Service is stoped, the service should be
     * destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();

        sendBroadcast(new Intent(ServiceBroadcasts.ACTION_SERVICE_DESTROYED));

        Log.d(TAG, "Service is destroyed");
    }

    public void stopMySelf() {
        stopSelf(serviceStartId);
    }
}

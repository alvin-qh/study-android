package alvin.adv.service.intent.services;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import alvin.adv.service.common.broadcasts.ServiceBroadcasts;
import alvin.adv.service.intent.tasks.Task;
import dagger.android.DaggerIntentService;

public class IntentService extends DaggerIntentService {
    public static final String EXTRA_ARG_NAME = "name";

    @Inject Task task;

    private static Set<OnJobStatusChangeListener> listeners = new HashSet<>();
    private Handler handler;

    public IntentService() {
        super(IntentService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        handler = new Handler(getMainLooper());
        sendBroadcast(new Intent(ServiceBroadcasts.ACTION_SERVICE_CREATED));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        sendBroadcast(new Intent(ServiceBroadcasts.ACTION_SERVICE_DESTROYED));

        handler.removeCallbacks(null);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String name = intent.getStringExtra(EXTRA_ARG_NAME);

            handler.postDelayed(() -> listeners.forEach(l -> l.onStart(name)), 0);
            final long timeSpend = task.work();
            handler.postDelayed(() -> listeners.forEach(l -> l.onFinish(name, timeSpend)), 0);
        }
    }

    public static void addOnJobStatusChangeListener(@NonNull OnJobStatusChangeListener l) {
        listeners.add(l);
    }

    public static void removeOnJobStatusChangeListener(@NonNull OnJobStatusChangeListener l) {
        listeners.remove(l);
    }

    public interface OnJobStatusChangeListener {
        void onStart(String jobName);

        void onFinish(String jobName, long timeSpend);
    }
}

package alvin.base.service.intent.services;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import alvin.base.service.common.broadcasts.ServiceBroadcasts;

public class IntentService extends android.app.IntentService {
    public static final String EXTRA_ARG_NAME = "name";
    private static final List<WeakReference<OnJobStatusChangeListener>> LISTENERS = new LinkedList<>();

    private final Task task = new Task(1000, 3000);

    private Handler mainThreadHandler;

    public IntentService() {
        super(IntentService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mainThreadHandler = new Handler(getMainLooper());
        sendBroadcast(new Intent(ServiceBroadcasts.ACTION_SERVICE_CREATED));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mainThreadHandler.removeCallbacksAndMessages(null);
        sendBroadcast(new Intent(ServiceBroadcasts.ACTION_SERVICE_DESTROYED));
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String name = intent.getStringExtra(EXTRA_ARG_NAME);
            final List<OnJobStatusChangeListener> listeners = filterListenersList();

            mainThreadHandler.postDelayed(() -> listeners.forEach(l -> l.onStart(name)), 0);

            final long timeSpend = task.work();
            mainThreadHandler.postDelayed(() -> listeners.forEach(l -> l.onFinish(name, timeSpend)), 0);
        }
    }

    private static List<OnJobStatusChangeListener> filterListenersList() {
        synchronized (LISTENERS) {
            final Iterator<WeakReference<OnJobStatusChangeListener>> iter = LISTENERS.iterator();
            while (iter.hasNext()) {
                final OnJobStatusChangeListener l = iter.next().get();
                if (l == null) {
                    iter.remove();
                }
            }
            return LISTENERS.stream().map(Reference::get).collect(Collectors.toList());
        }
    }

    public static void addOnJobStatusChangeListener(@NonNull OnJobStatusChangeListener l) {
        synchronized (LISTENERS) {
            LISTENERS.add(new WeakReference<>(l));
        }
    }

    public static void removeOnJobStatusChangeListener(@NonNull OnJobStatusChangeListener l) {
        synchronized (LISTENERS) {
            final Iterator<WeakReference<OnJobStatusChangeListener>> iter = LISTENERS.iterator();
            while (iter.hasNext()) {
                final OnJobStatusChangeListener listener = iter.next().get();
                if (listener == null || l == listener) {
                    iter.remove();
                }
            }
        }
    }

    public interface OnJobStatusChangeListener {
        void onStart(String jobName);

        void onFinish(String jobName, long timeSpend);
    }
}

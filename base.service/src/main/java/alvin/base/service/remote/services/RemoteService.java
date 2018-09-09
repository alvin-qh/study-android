package alvin.base.service.remote.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.ArraySet;
import android.util.Log;

import com.google.common.collect.Lists;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import alvin.base.service.remote.IOnJobStatusChangeListener;
import alvin.base.service.remote.IRemoteBinder;
import alvin.base.service.remote.models.Job;
import alvin.base.service.remote.models.JobResponse;

/**
 * Config this service as remote service.
 * <p>
 * Remote service ran in an independent process.
 * <p>
 * <pre>
 * &lt;service android:name=".remote.services.RemoteService"
 *          android:exported="true"
 *          android:process="alvin.base.service.remote.services.RemoteService"
 *          android:permission="alvin.base.service.permission.REMOTE_SERVICE"&gt;
 *          &lt;intent-filter&gt;
 *              &lt;action android:name="alvin.base.service.remote.aidls.IRemoteBinder"/&gt;
 *          &lt;/intent-filter&gt;
 * &lt;/service&gt;
 * </pre>
 * <p>
 * android:exported: true if service can connect by any other applications.
 * android:process: name of process to run the service.
 * android:permission: permission to connect this remote service. the client must use same permission as server
 * <pre>
 *  &lt;uses-permission android:name="alvin.base.service.permission.REMOTE_SERVICE" /&gt;
 * </pre>
 * <p>
 * Remote service config must have an {@code <intent-filter>} elements, the action name must be
 * the full name of AIDL class name which return from {@link RemoteService#onBind(Intent)} method
 */
public class RemoteService extends Service {
    private static final String TAG = RemoteService.class.getSimpleName();

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final Task task = new Task(1000, 3000);

    private final Set<WeakReference<IOnJobStatusChangeListener>> listeners = new ArraySet<>();

    /**
     * Get binder from AIDL interface.
     * {@link IRemoteBinder.Stub} is a proxy class use in service.
     */
    private class Binder extends IRemoteBinder.Stub {
        private WeakReference<IOnJobStatusChangeListener> identity;

        @Override
        public void addOnJobStatusChangeListener(IOnJobStatusChangeListener l) {
            identity = new WeakReference<>(l);
            synchronized (listeners) {
                listeners.add(identity);
            }
        }

        @Override
        public void removeOnJobStatusChangeListener() {
            synchronized (listeners) {
                listeners.remove(identity);
            }
        }

        @Override
        public void addNewJob(Job job) {
            executorService.execute(() -> {
                final List<WeakReference<IOnJobStatusChangeListener>> refs = filterListeners();
                refs.forEach(ref -> {
                    final IOnJobStatusChangeListener l = ref.get();
                    if (l != null) {
                        try {
                            l.onJobStart(job.getName());
                        } catch (RemoteException e) {
                            ref.clear();
                            Log.e(TAG, "Remote exception caused", e);
                        }
                    }
                });

                final JobResponse response = task.runJob(job);
                refs.forEach(ref -> {
                    final IOnJobStatusChangeListener l = ref.get();
                    if (l != null) {
                        try {
                            l.onJobFinish(response);
                        } catch (RemoteException e) {
                            ref.clear();
                            Log.e(TAG, "Remote exception caused", e);
                        }
                    }
                });
            });
        }
    }

    private List<WeakReference<IOnJobStatusChangeListener>> filterListeners() {
        synchronized (listeners) {
            final Iterator<WeakReference<IOnJobStatusChangeListener>> iter = listeners.iterator();
            while (iter.hasNext()) {
                final IOnJobStatusChangeListener l = iter.next().get();
                if (l == null) {
                    iter.remove();
                }
            }
            return Lists.newArrayList(listeners);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        listeners.clear();
        executorService.shutdown();
    }
}

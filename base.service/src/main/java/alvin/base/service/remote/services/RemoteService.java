package alvin.base.service.remote.services;

import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import alvin.base.service.remote.aidls.IOnJobStatusChangeListener;
import alvin.base.service.remote.aidls.IRemoteBinder;
import alvin.base.service.remote.aidls.models.Job;
import alvin.base.service.remote.aidls.models.JobResponse;
import alvin.base.service.remote.tasks.Task;
import dagger.android.DaggerService;

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
public class RemoteService extends DaggerService {
    private static final String TAG = RemoteService.class.getSimpleName();

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * Get binder from AIDL interface.
     * {@link IRemoteBinder.Stub} is a proxy class use in service.
     */
    private IRemoteBinder.Stub binder = new IRemoteBinder.Stub() {

        @Override
        public void addOnJobStatusChangeListener(String key, IOnJobStatusChangeListener l)
                throws RemoteException {
            listeners.put(key, l);
        }

        @Override
        public void removeOnJobStatusChangeListener(String key)
                throws RemoteException {
            listeners.remove(key);
        }

        @Override
        public void addNewJob(Job job) throws RemoteException {
            executorService.execute(() -> {
                listeners.values().forEach(l -> {
                    try {
                        l.onJobStart(job.getName());
                    } catch (RemoteException e) {
                        Log.e(TAG, "Remote exception caused", e);
                    }
                });

                final JobResponse response = task.runJob(job);

                listeners.values().forEach(l -> {
                    try {
                        l.onJobFinish(response);
                    } catch (RemoteException e) {
                        Log.e(TAG, "Remote exception caused", e);
                    }
                });
            });
        }
    };

    @Inject Task task;

    private final Map<String, IOnJobStatusChangeListener> listeners = new ConcurrentHashMap<>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        listeners.clear();
        executorService.shutdown();
    }
}

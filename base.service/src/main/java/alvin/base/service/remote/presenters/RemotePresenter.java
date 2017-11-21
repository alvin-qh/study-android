package alvin.base.service.remote.presenters;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Collections;

import javax.inject.Inject;

import alvin.base.service.remote.RemoteContracts;
import alvin.base.service.remote.aidls.IOnJobStatusChangeListener;
import alvin.base.service.remote.aidls.IRemoteBinder;
import alvin.base.service.remote.aidls.models.Job;
import alvin.base.service.remote.aidls.models.JobResponse;
import alvin.lib.mvp.PresenterAdapter;

public class RemotePresenter extends PresenterAdapter<RemoteContracts.View>
        implements RemoteContracts.Presenter {

    private static final String TAG = RemotePresenter.class.getSimpleName();

    private ServiceConnection conn;
    private IRemoteBinder binder;

    private int jobId = 1;

    private Handler handler;

    /**
     * Create an AIDL instance by stub of AIDL interface.
     * <p>
     * {@link IOnJobStatusChangeListener.Stub} is a proxy class use in client.
     */
    private IOnJobStatusChangeListener listener = new IOnJobStatusChangeListener.Stub() {
        @Override
        public void onJobStart(String name) throws RemoteException {
            handler.postDelayed(() -> withView(view -> view.jobStarted(name)), 0);
        }

        @Override
        public void onJobFinish(JobResponse response) throws RemoteException {
            handler.postDelayed(() -> withView(view -> view.jobFinished(response)), 0);
        }
    };

    @Inject
    public RemotePresenter(@NonNull RemoteContracts.View view) {
        super(view);
    }

    @Override
    public void bindService(Context context) {
        if (conn == null) {
            // Create intent with name of remote service action
            Intent intent = new Intent("alvin.base.service.remote.aidls.IRemoteBinder");
            // Set application package name of remote service
            intent.setPackage("alvin.base.service");

            // Make ServiceConnection instance to connect to service
            conn = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    // Get IRemoteBinder instance from IRemoteBinder.Stub.asInterface method
                    // This instance can use in client to connect service
                    binder = IRemoteBinder.Stub.asInterface(iBinder);
                    try {
                        binder.addOnJobStatusChangeListener(getClass().getName(), listener);
                    } catch (RemoteException e) {
                        Log.e(TAG, "Cannot add listener", e);
                    }
                }

                @Override
                public void onServiceDisconnected(ComponentName componentName) {
                    unbindService(context);
                }
            };

            context.bindService(intent, conn, Context.BIND_AUTO_CREATE);

            handler = new Handler(Looper.getMainLooper());
        }
    }

    @Override
    public void unbindService(Context context) {
        if (conn != null) {
            try {
                binder.removeOnJobStatusChangeListener(getClass().getName());
            } catch (RemoteException e) {
                Log.e(TAG, "Cannot remote listener", e);
            }
            context.unbindService(conn);
            conn = null;
        }
    }

    @Override
    public void startWork() {
        try {
            binder.addNewJob(new Job("Job_" + jobId++, Collections.emptyMap()));
        } catch (RemoteException e) {
            Log.e(TAG, "Cannot add job", e);
        }
    }
}

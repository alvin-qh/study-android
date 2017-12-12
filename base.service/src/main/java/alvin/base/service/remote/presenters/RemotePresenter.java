package alvin.base.service.remote.presenters;

import android.os.Handler;
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
import alvin.lib.mvp.adapters.ViewPresenterAdapter;

public class RemotePresenter extends ViewPresenterAdapter<RemoteContracts.View>
        implements RemoteContracts.Presenter {

    private static final String TAG = RemotePresenter.class.getSimpleName();

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
    public void onStop() {
        super.onStop();
        serviceUnbound();
    }

    @Override
    public void serviceBound(@NonNull IRemoteBinder binder) {
        try {
            binder.addOnJobStatusChangeListener(getClass().getName(), listener);
            this.binder = binder;
            this.handler = new Handler(Looper.getMainLooper());
        } catch (RemoteException e) {
            Log.e(TAG, "Cannot add listener", e);
        }
    }

    @Override
    public void serviceUnbound() {
        if (binder != null) {
            try {
                binder.removeOnJobStatusChangeListener(getClass().getName());
                handler.removeCallbacks(null);
            } catch (RemoteException e) {
                Log.e(TAG, "Cannot remote listener", e);
            }
            binder = null;
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

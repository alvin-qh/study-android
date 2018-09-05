package alvin.adv.service.remote.views;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Collections;

import alvin.adv.service.R;
import alvin.adv.service.remote.IOnJobStatusChangeListener;
import alvin.adv.service.remote.IRemoteBinder;
import alvin.adv.service.remote.models.Job;
import alvin.adv.service.remote.models.JobResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class RemoteActivity extends DaggerAppCompatActivity {
    private static final String TAG = RemoteActivity.class.getSimpleName();
    private static final float ONE_SECOND_MS_FLOAT = 1000f;
    private static final String KEY = RemoteActivity.class.getName();

    @BindView(R.id.sv_job_response) ScrollView svJobResponse;
    @BindView(R.id.tv_job_response) TextView tvJobResponse;

    private ServiceConnection conn;
    private IRemoteBinder binder;

    private Handler handler;

    private int jobId = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.remote_activity);

        ButterKnife.bind(this);

        handler = new Handler(getMainLooper());
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (conn == null) {
            jobId = 1;

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
                        binder.addOnJobStatusChangeListener(KEY, new IOnJobStatusChangeListener.Stub() {

                            @Override
                            public void onJobStart(String name) throws RemoteException {
                                handler.post(() -> jobStarted(name));
                            }

                            @Override
                            public void onJobFinish(JobResponse response) throws RemoteException {
                                handler.post(() -> jobFinished(response));
                            }
                        });
                    } catch (RemoteException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

                @Override
                public void onServiceDisconnected(ComponentName componentName) {
                    disconnect();
                }
            };

            bindService(intent, conn, Context.BIND_AUTO_CREATE);
        }
    }

    private void disconnect() {
        if (binder != null) {
            try {
                binder.removeOnJobStatusChangeListener(KEY);
            } catch (RemoteException e) {
                Log.e(TAG, e.getMessage());
            }
            binder = null;
        }
        if (conn != null) {
            unbindService(conn);
            conn = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        disconnect();
    }

    private void jobStarted(String name) {
        tvJobResponse.append(String.format("\nJob %s is started", name));
    }

    private void jobFinished(JobResponse response) {
        tvJobResponse.append(String.format("\nJob %s is finished, time spend %ss\n",
                response.getName(), response.getTimeSpend() / ONE_SECOND_MS_FLOAT));

        handler.post(() -> svJobResponse.fullScroll(ScrollView.FOCUS_DOWN));
    }

    @OnClick(R.id.btn_work)
    void onWorkButtonClick(Button b) {
        try {
            binder.addNewJob(new Job("Job_" + jobId++, Collections.emptyMap()));
            handler.post(() -> svJobResponse.fullScroll(ScrollView.FOCUS_DOWN));
        } catch (RemoteException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}

package alvin.base.service.remote.views;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Collections;

import alvin.base.service.R;
import alvin.base.service.remote.IOnJobStatusChangeListener;
import alvin.base.service.remote.IRemoteBinder;
import alvin.base.service.remote.models.Job;
import alvin.base.service.remote.models.JobResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RemoteActivity extends AppCompatActivity {
    private static final String TAG = RemoteActivity.class.getSimpleName();

    private static final float ONE_SECOND_MS_FLOAT = 1000f;

    @BindView(R.id.sv_job_response)
    ScrollView svJobResponse;

    @BindView(R.id.tv_job_response)
    TextView tvJobResponse;

    private ServiceConnection conn;
    private IRemoteBinder binder;

    private int jobId = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_remote);

        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (conn == null) {
            jobId = 1;

            // Create intent with name of remote service action
            Intent intent = new Intent("alvin.services.REMOTE_SERVICE");
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
                        binder.addOnJobStatusChangeListener(new IOnJobStatusChangeListener.Stub() {

                            @Override
                            public void onJobStart(String name) {
                                runOnUiThread(() -> jobStarted(name));
                            }

                            @Override
                            public void onJobFinish(JobResponse response) {
                                runOnUiThread(() -> jobFinished(response));
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
                binder.removeOnJobStatusChangeListener();
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

        runOnUiThread(() -> svJobResponse.fullScroll(ScrollView.FOCUS_DOWN));
    }

    @OnClick(R.id.btn_work)
    void onWorkButtonClick() {
        try {
            if (binder != null) {
                binder.addNewJob(new Job("Job_" + jobId++, Collections.emptyMap()));
                runOnUiThread(() -> svJobResponse.fullScroll(ScrollView.FOCUS_DOWN));
            }
        } catch (RemoteException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}

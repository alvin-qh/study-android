package alvin.base.service.remote.views;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import javax.inject.Inject;

import alvin.base.service.R;
import alvin.base.service.remote.RemoteContracts;
import alvin.base.service.remote.aidls.IRemoteBinder;
import alvin.base.service.remote.aidls.models.JobResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class RemoteActivity extends DaggerAppCompatActivity implements RemoteContracts.View {
    public static final float ONE_SECOND_MS_FLOAT = 1000f;

    @Inject RemoteContracts.Presenter presenter;

    @BindView(R.id.sv_job_response) ScrollView svJobResponse;
    @BindView(R.id.tv_job_response) TextView tvJobResponse;

    private ServiceConnection conn;

    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.remote_activity);

        ButterKnife.bind(this);

        handler = new Handler(getMainLooper());
    }

    @Override
    protected void onStart() {
        super.onStart();

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
                    presenter.serviceBound(IRemoteBinder.Stub.asInterface(iBinder));
                }

                @Override
                public void onServiceDisconnected(ComponentName componentName) {
                    presenter.serviceUnbound();
                }
            };

            bindService(intent, conn, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        presenter.onStop();
    }

    @Override
    public void jobStarted(String name) {
        tvJobResponse.append(String.format("\nJob %s is started", name));
    }

    @Override
    public void jobFinished(JobResponse response) {
        tvJobResponse.append(String.format("\nJob %s is finished, time spend %ss\n",
                response.getName(), response.getTimeSpend() / ONE_SECOND_MS_FLOAT));

        handler.post(() -> svJobResponse.fullScroll(ScrollView.FOCUS_DOWN));
    }

    @OnClick(R.id.btn_work)
    public void onWorkButtonClick(Button b) {
        presenter.startWork();

        handler.post(() -> svJobResponse.fullScroll(ScrollView.FOCUS_DOWN));
    }
}

package alvin.base.service.remote.views;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import javax.inject.Inject;

import alvin.base.service.R;
import alvin.base.service.remote.RemoteContracts;
import alvin.base.service.remote.aidls.models.JobResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class RemoteActivity extends AppCompatActivity implements RemoteContracts.View {
    public static final float ONE_SECOND_MS_FLOAT = 1000f;

    @Inject RemoteContracts.Presenter presenter;

    @BindView(R.id.sv_job_response) ScrollView svJobResponse;
    @BindView(R.id.tv_job_response) TextView tvJobResponse;

    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.remote_activity);

        ButterKnife.bind(this);

        AndroidInjection.inject(this);

        handler = new Handler(getMainLooper());
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter.bindService(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        presenter.unbindService(this);
    }

    @Override
    public Context context() {
        return this;
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

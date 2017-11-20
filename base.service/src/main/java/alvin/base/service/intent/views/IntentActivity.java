package alvin.base.service.intent.views;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import javax.inject.Inject;

import alvin.base.service.R;
import alvin.base.service.intent.IntentContracts;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class IntentActivity extends AppCompatActivity implements IntentContracts.View {

    private static final float ONE_SECOND_MS_FLOAT = 1000f;

    @Inject IntentContracts.Presenter presenter;

    @BindView(R.id.rg_service_status) RadioGroup rgServiceStatus;
    @BindView(R.id.sv_time) ScrollView svTime;
    @BindView(R.id.tv_time) TextView tvTime;

    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_activity);

        ButterKnife.bind(this);

        AndroidInjection.inject(this);

        handler = new Handler(getMainLooper());
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter.registerReceiver(this);
        presenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        presenter.unregisterReceiver(this);
        presenter.onStop();
    }

    @OnClick(R.id.btn_work)
    public void onWorkButtonClick(Button b) {
        presenter.workOnce(this);
    }

    @Override
    public Context context() {
        return this;
    }

    @Override
    public void serviceCreated() {
        rgServiceStatus.check(R.id.rb_created);
    }

    @Override
    public void serviceDestroyed() {
        rgServiceStatus.check(R.id.rb_destroyed);
    }

    @Override
    public void onJobStart(String jobName) {
        tvTime.append(String.format("\n%s is started", jobName));
        handler.post(() -> svTime.fullScroll(ScrollView.FOCUS_DOWN));
    }

    @Override
    public void onJobFinish(String jobName, long timeSpend) {
        tvTime.append(String.format("\n%s is finished, time spend %ss\n",
                jobName, timeSpend / ONE_SECOND_MS_FLOAT));
        handler.post(() -> svTime.fullScroll(ScrollView.FOCUS_DOWN));
    }
}

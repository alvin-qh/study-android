package alvin.adv.service.intent.views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.common.base.Strings;

import alvin.adv.service.R;
import alvin.adv.service.common.broadcasts.ServiceBroadcasts;
import alvin.adv.service.intent.IntentContracts;
import alvin.adv.service.intent.services.IntentService;
import alvin.lib.common.utils.IntentFilters;
import alvin.lib.mvp.contracts.adapters.ActivityAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntentActivity
        extends ActivityAdapter<IntentContracts.Presenter>
        implements IntentContracts.View {

    private static final float ONE_SECOND_MS_FLOAT = 1000f;

    private final IntentService.OnJobStatusChangeListener onJobStatusChangeListener =
            new IntentService.OnJobStatusChangeListener() {
                @Override
                public void onStart(String jobName) {
                    onJobStart(jobName);
                }

                @Override
                public void onFinish(String jobName, long timeSpend) {
                    onJobFinish(jobName, timeSpend);
                }
            };

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (!Strings.isNullOrEmpty(action)) {
                switch (action) {
                case ServiceBroadcasts.ACTION_SERVICE_CREATED:
                    serviceCreated();
                    break;
                case ServiceBroadcasts.ACTION_SERVICE_DESTROYED:
                    serviceDestroyed();
                    break;
                default:
                    break;
                }
            }
        }
    };

    @BindView(R.id.rg_service_status) RadioGroup rgServiceStatus;
    @BindView(R.id.sv_job_response) ScrollView svJobResponse;
    @BindView(R.id.tv_job_response) TextView tvJobResponse;

    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_activity);

        ButterKnife.bind(this);

        handler = new Handler(getMainLooper());
    }

    @Override
    protected void onResume() {
        super.onResume();
        connect();
    }

    private void connect() {
        final IntentFilter filter = IntentFilters.newBuilder()
                .addAction(ServiceBroadcasts.ACTION_SERVICE_CREATED)
                .addAction(ServiceBroadcasts.ACTION_SERVICE_DESTROYED)
                .build();
        registerReceiver(receiver, filter);

        IntentService.addOnJobStatusChangeListener(onJobStatusChangeListener);
    }

    private void disconnect() {
        unregisterReceiver(receiver);
        IntentService.removeOnJobStatusChangeListener(onJobStatusChangeListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        disconnect();
    }

    @OnClick(R.id.btn_work)
    public void onWorkButtonClick(Button b) {
        presenter.makeNewJob();
    }

    private void serviceCreated() {
        rgServiceStatus.check(R.id.rb_created);
    }

    private void serviceDestroyed() {
        rgServiceStatus.check(R.id.rb_destroyed);
    }

    private void onJobStart(String jobName) {
        tvJobResponse.append(String.format("\n%s is started", jobName));
        handler.post(() -> svJobResponse.fullScroll(ScrollView.FOCUS_DOWN));
    }

    private void onJobFinish(String jobName, long timeSpend) {
        tvJobResponse.append(String.format("\n%s is finished, time spend %ss\n",
                jobName, timeSpend / ONE_SECOND_MS_FLOAT));
        handler.post(() -> svJobResponse.fullScroll(ScrollView.FOCUS_DOWN));
    }

    @Override
    public void doNewJob(String jobName) {
        startService(
                new Intent(this, IntentService.class)
                        .putExtra(IntentService.EXTRA_ARG_NAME, jobName)
        );
    }
}

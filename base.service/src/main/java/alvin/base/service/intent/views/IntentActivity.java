package alvin.base.service.intent.views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.common.base.Strings;

import alvin.base.service.R;
import alvin.base.service.common.broadcasts.ServiceBroadcasts;
import alvin.base.service.intent.services.IntentService;
import alvin.lib.common.utils.IntentFilters;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntentActivity extends AppCompatActivity {

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

    @BindView(R.id.rg_service_status)
    RadioGroup rgServiceStatus;

    @BindView(R.id.sv_job_response)
    ScrollView svJobResponse;

    @BindView(R.id.tv_job_response)
    TextView tvJobResponse;

    private int jobId = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);

        ButterKnife.bind(this);
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
    public void onWorkButtonClick() {
        startService(new Intent(this, IntentService.class)
                .putExtra(IntentService.EXTRA_ARG_NAME, "Job_" + jobId++));
    }

    private void serviceCreated() {
        rgServiceStatus.check(R.id.rb_created);
    }

    private void serviceDestroyed() {
        rgServiceStatus.check(R.id.rb_destroyed);
    }

    private void onJobStart(String jobName) {
        tvJobResponse.append(String.format("\n%s is started", jobName));
        runOnUiThread(() -> svJobResponse.fullScroll(ScrollView.FOCUS_DOWN));
    }

    private void onJobFinish(String jobName, long timeSpend) {
        tvJobResponse.append(String.format("\n%s is finished, time spend %ss\n",
                jobName, timeSpend / ONE_SECOND_MS_FLOAT));
        runOnUiThread(() -> svJobResponse.fullScroll(ScrollView.FOCUS_DOWN));
    }
}

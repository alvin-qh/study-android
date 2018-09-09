package alvin.base.service.lifecycle.views;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.common.base.Strings;

import alvin.base.service.R;
import alvin.base.service.common.broadcasts.ServiceBroadcasts;
import alvin.base.service.lifecycle.services.LifecycleService;
import alvin.lib.common.utils.IntentFilters;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LifecycleActivity extends AppCompatActivity {

    private static final String TAG = LifecycleActivity.class.getSimpleName();

    @BindView(R.id.rg_service_status)
    RadioGroup rgServiceStatus;

    @BindView(R.id.tv_service_start_count)
    TextView tvStartCount;

    private ServiceManager sm;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lifecycle);
        ButterKnife.bind(this);

        final Intent intent = new Intent(this, LifecycleService.class)
                .putExtra(LifecycleService.EXTRA_ARGUMENTS_MODE, Service.START_REDELIVER_INTENT);
        sm = new ServiceManager(this, intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final IntentFilter filter = IntentFilters.newBuilder()
                .addAction(ServiceBroadcasts.ACTION_SERVICE_CREATED)
                .addAction(ServiceBroadcasts.ACTION_SERVICE_DESTROYED)
                .build();
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @OnClick(R.id.btn_start_service)
    public void onStartButtonClick() {
        sm.start();
        showStartCount();
    }

    @OnClick(R.id.btn_stop_service)
    public void onStopButtonClick() {
        sm.stop();
        showStartCount();
    }

    @OnClick(R.id.btn_bind_service)
    public void onBindButtonClick() {
        sm.bind(new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder binder) {
                Log.i(TAG, "The service was connected");
                showStartCount();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                sm.remove(this);
                Log.i(TAG, "The service was accidentally disconnected");
            }
        });
    }

    @OnClick(R.id.btn_unbind_service)
    public void onUnbindButtonClick() {
        sm.unbind();
        showStartCount();
    }

    private void showStartCount() {
        tvStartCount.setText(String.valueOf(sm.getStartCount()));
    }

    public void serviceDestroyed() {
        rgServiceStatus.check(R.id.rb_destroyed);
    }

    public void serviceCreated() {
        rgServiceStatus.check(R.id.rb_created);
    }

}

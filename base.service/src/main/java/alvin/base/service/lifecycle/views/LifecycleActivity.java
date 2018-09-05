package alvin.adv.service.lifecycle.views;

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
import android.util.Log;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.common.base.Strings;

import javax.inject.Inject;

import alvin.adv.service.R;
import alvin.adv.service.common.broadcasts.ServiceBroadcasts;
import alvin.adv.service.lifecycle.LifecycleContracts;
import alvin.adv.service.lifecycle.services.LifecycleService;
import alvin.lib.common.utils.IntentFilters;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class LifecycleActivity extends DaggerAppCompatActivity
        implements LifecycleContracts.View {

    private static final String TAG = LifecycleActivity.class.getSimpleName();

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

    @Inject LifecycleContracts.Presenter presenter;

    @BindView(R.id.rg_service_status) RadioGroup rgServiceStatus;
    @BindView(R.id.tv_service_start_count) TextView tvStartCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lifecycle_activity);
        ButterKnife.bind(this);
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
    public void onStartButtonClick(Button b) {
        final Intent intent = new Intent(this, LifecycleService.class)
                .putExtra(LifecycleService.EXTRA_ARGUMENTS_MODE, Service.START_REDELIVER_INTENT);

        // Start the service and pass the arguments from intent object.
        // IService can be start many times, but IService#onCreate method only be called one time,
        // and IService#onStartCommand method should be called many times.
        // Started service can be stoped by Context#stopService.
        startService(intent);

        presenter.serviceStarted();
    }

    @OnClick(R.id.btn_stop_service)
    public void onStopButtonClick(Button b) {
        stopService(new Intent(this, LifecycleService.class));
        presenter.serviceStoped();
    }

    @OnClick(R.id.btn_bind_service)
    public void onBindButtonClick(Button b) {
        ServiceConnection conn = new ServiceConnection() {

            /**
             * Callback when service is connected, the {@link IBinder} instance will be passed,
             *
             * @see LifecycleService#onBind(Intent)
             */
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder binder) {
                Log.i(TAG, "The service was connected");
            }

            /**
             * Callback only service will be killed unexpected.
             */
            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Log.i(TAG, "The service was accidentally disconnected");
            }
        };

        final Intent intent = new Intent(this, LifecycleService.class);

        // Bind service, the IService#onBind method will be called one time, and Reference Counter
        // should be increase
        bindService(intent, conn, Context.BIND_AUTO_CREATE);

        presenter.serviceBound(conn);
    }

    @OnClick(R.id.btn_unbind_service)
    public void onUnbindButtonClick(Button b) {
        presenter.serviceUnbound();
    }

    public void serviceDestroyed() {
        rgServiceStatus.check(R.id.rb_destroyed);
    }

    public void serviceCreated() {
        rgServiceStatus.check(R.id.rb_created);
    }

    @Override
    public void showStartCount(int count) {
        tvStartCount.setText(String.valueOf(count));
    }
}

package alvin.base.service.lifecycle.views;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import javax.inject.Inject;

import alvin.base.service.R;
import alvin.base.service.common.broadcasts.ServiceBroadcasts;
import alvin.base.service.lifecycle.LifecycleContracts;
import alvin.base.service.lifecycle.services.LifecycleService;
import alvin.lib.common.utils.IntentFilters;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class LifecycleActivity extends DaggerAppCompatActivity
        implements LifecycleContracts.View {

    private static final String TAG = LifecycleActivity.class.getSimpleName();

    @Inject LifecycleContracts.Presenter presenter;

    @BindView(R.id.rg_service_status) RadioGroup rgServiceStatus;
    @BindView(R.id.tv_service_start_count) TextView tvStartCount;

    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lifecycle_activity);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (receiver == null) {
            receiver = presenter.getReceiver();
            registerReceiver(receiver,
                    IntentFilters.newBuilder()
                            .addAction(ServiceBroadcasts.ACTION_SERVICE_CREATED)
                            .addAction(ServiceBroadcasts.ACTION_SERVICE_DESTROYED)
                            .build());
        }
        presenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        presenter.onStop();
    }

    @OnClick(R.id.btn_start_service)
    public void onStartButtonClick(Button b) {
        final Intent intent = new Intent(this, LifecycleService.class)
                .putExtra(LifecycleService.EXTRA_ARGUMENTS_MODE, Service.START_REDELIVER_INTENT);

        // Start the service and pass the arguments from intent object.
        // Service can be start many times, but Service#onCreate method only be called one time,
        // and Service#onStartCommand method should be called many times.
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

        // Bind service, the Service#onBind method will be called one time, and Reference Counter
        // should be increase
        bindService(intent, conn, Context.BIND_AUTO_CREATE);

        presenter.serviceBound(conn);
    }

    @OnClick(R.id.btn_unbind_service)
    public void onUnbindButtonClick(Button b) {
        ServiceConnection conn = presenter.unbindService();

        if (conn != null) {
            // Unbind service, the Service#onUnbind method will be called one time, and Reference Counter
            // should be decrease
            unbindService(conn);
        }
    }

    @Override
    public void serviceDestroyed() {
        rgServiceStatus.check(R.id.rb_destroyed);
    }

    @Override
    public void serviceCreated() {
        rgServiceStatus.check(R.id.rb_created);
    }

    @Override
    public void showStartCount(int count) {
        tvStartCount.setText(String.valueOf(count));
    }
}

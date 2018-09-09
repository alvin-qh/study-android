package alvin.base.service.foreground.views;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import alvin.base.service.R;
import alvin.base.service.common.broadcasts.ServiceBroadcasts;
import alvin.base.service.foreground.services.ForegroundService;
import alvin.lib.common.utils.IntentFilters;
import alvin.lib.common.utils.Permissions;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForegroundActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    @BindView(R.id.btn_start)
    Button btnStart;

    @BindView(R.id.btn_switch)
    Button btnSwitch;

    @BindView(R.id.btn_stop)
    Button btnStop;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action != null) {
                switch (action) {
                case ServiceBroadcasts.ACTION_SERVICE_CREATED:
                    serviceCreated();
                    break;
                case ServiceBroadcasts.ACTION_SERVICE_DESTROYED:
                    serviceDestroyed();
                    break;
                case ServiceBroadcasts.ACTION_SERVICE_NOTIFY:
                    serviceNotified();
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

        setContentView(R.layout.activity_foreground);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(receiver, IntentFilters.newBuilder()
                .addAction(ServiceBroadcasts.ACTION_SERVICE_CREATED)
                .addAction(ServiceBroadcasts.ACTION_SERVICE_DESTROYED)
                .addAction(ServiceBroadcasts.ACTION_SERVICE_NOTIFY)
                .build());

        final Permissions permissions = new Permissions(this, Manifest.permission.FOREGROUND_SERVICE);
        final Permissions.Status status = permissions.requestPermissions(PERMISSION_REQUEST_CODE);
        if (status == Permissions.Status.ALLOWED) {
            onPermissionsAllow();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(receiver);
    }

    private void onPermissionsAllow() {
        btnStart.setEnabled(true);
    }

    @OnClick(R.id.btn_start)
    public void onStartServiceButtonClicked() {
        Intent intent = new Intent(this, ForegroundService.class);
        startForegroundService(intent);
    }

    @OnClick(R.id.btn_switch)
    public void onSwitchButtonClicked() {
        Intent intent = new Intent(this, ForegroundService.class);
        startService(intent);
    }

    @OnClick(R.id.btn_stop)
    public void onStopServiceButtonClicked() {
        Intent intent = new Intent(this, ForegroundService.class);
        stopService(intent);
    }

    private void serviceCreated() {
        btnStart.setEnabled(false);
        btnStop.setEnabled(true);
        btnSwitch.setEnabled(true);
    }

    private void serviceDestroyed() {
        btnStart.setEnabled(true);
        btnSwitch.setEnabled(false);
        btnStop.setEnabled(false);
    }

    private void serviceNotified() {
        Toast.makeText(this,"Notification content was clicked", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            final Permissions.Status status = Permissions.checkPermissionsWithResults(permissions, grantResults);
            if (status == Permissions.Status.ALLOWED) {
                onPermissionsAllow();
            }
        }
    }
}

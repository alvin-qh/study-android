package alvin.base.service.foreground.views;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Button;

import javax.inject.Inject;

import alvin.base.service.R;
import alvin.base.service.common.broadcasts.ServiceBroadcasts;
import alvin.base.service.foreground.ForegroundContracts;
import alvin.base.service.foreground.services.ForegroundService;
import alvin.lib.common.utils.IntentFilters;
import alvin.lib.common.utils.Permissions;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class ForegroundActivity extends DaggerAppCompatActivity
        implements ForegroundContracts.View {
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Inject ForegroundContracts.Presenter presenter;

    @BindView(R.id.btn_start_service) Button btnStartService;
    @BindView(R.id.btn_stop_service) Button btnStopService;

    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.foreground_activity);

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
            receiver = presenter.getBroadcastReceiver();
            registerReceiver(receiver,
                    IntentFilters.newBuilder()
                            .addAction(ServiceBroadcasts.ACTION_SERVICE_CREATED)
                            .addAction(ServiceBroadcasts.ACTION_SERVICE_DESTROYED)
                            .build()
            );
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        final Permissions permissions = new Permissions(this, Manifest.permission.FOREGROUND_SERVICE);
        final Permissions.Status status = permissions.requestPermissions(PERMISSION_REQUEST_CODE);
        if (status == Permissions.Status.ALLOWED) {
            onPermissionsAllow();
        }
    }

    private void onPermissionsAllow() {
        findViewById(R.id.btn_start_service).setEnabled(true);
        findViewById(R.id.btn_stop_service).setEnabled(true);
    }

    @OnClick(R.id.btn_start_service)
    public void onStartServiceButtonClick(Button b) {
        Intent intent = new Intent(this, ForegroundService.class);
        startForegroundService(intent);
    }

    @OnClick(R.id.btn_stop_service)
    public void onStopServiceButtonClick(Button b) {
        Intent intent = new Intent(this, ForegroundService.class);
        stopService(intent);
    }

    @Override
    public void serviceCreated() {
        btnStartService.setEnabled(false);
        btnStopService.setEnabled(true);
    }

    @Override
    public void serviceDestroyed() {
        btnStartService.setEnabled(true);
        btnStopService.setEnabled(false);
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

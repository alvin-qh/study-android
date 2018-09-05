package alvin.adv.service.foreground.views;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import javax.inject.Inject;

import alvin.adv.service.R;
import alvin.adv.service.common.broadcasts.ServiceBroadcasts;
import alvin.adv.service.foreground.ForegroundContracts;
import alvin.adv.service.foreground.services.ForegroundService;
import alvin.lib.common.utils.IntentFilters;
import alvin.lib.common.utils.Versions;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class ForegroundActivity extends DaggerAppCompatActivity
        implements ForegroundContracts.IView {

    @Inject ForegroundContracts.Presenter presenter;
    @Inject Versions version;

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

    @OnClick(R.id.btn_start_service)
    public void onStartServiceButtonClick(Button b) {
        Intent intent = new Intent(this, ForegroundService.class);

        if (version.isEqualOrGreatThan()) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
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
}

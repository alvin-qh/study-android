package alvin.base.service.working.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.inject.Inject;

import alvin.base.service.R;
import alvin.base.service.working.WorkingContracts;
import alvin.base.service.working.services.WorkingService;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class WorkingActivity extends DaggerAppCompatActivity implements WorkingContracts.View {

    @Inject WorkingContracts.Presenter presenter;

    @BindView(R.id.btn_connect_service) Button btnConnectService;
    @BindView(R.id.btn_disconnect_service) Button btnDisconnectService;
    @BindView(R.id.btn_start_service) Button btnStartService;
    @BindView(R.id.btn_stop_service) Button btnStopService;
    @BindView(R.id.tv_time) TextView tvTime;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.working_activity);

        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();

        disconnectService();
        stopService();
    }

    @OnClick(R.id.btn_start_service)
    public void onStartButtonClick(Button b) {
        Intent intent = new Intent(this, WorkingService.class);
        intent.putExtra(WorkingService.EXTRA_ARG_ZONE, "Asia/Shanghai");
        startService(intent);

        btnStartService.setEnabled(false);
        btnStopService.setEnabled(true);
        btnConnectService.setEnabled(true);
    }

    @OnClick(R.id.btn_connect_service)
    public void onConnectButtonClick(Button b) {
        final WorkingService service = WorkingService.getServiceRef().get();
        if (service != null) {
            service.addOnServiceCallbackListener(presenter.getCallbackListener());

            btnConnectService.setEnabled(false);
            btnDisconnectService.setEnabled(true);
        }
    }

    @OnClick(R.id.btn_disconnect_service)
    public void onDisconnectButtonClick(Button b) {
        if (disconnectService()) {
            btnConnectService.setEnabled(true);
            btnDisconnectService.setEnabled(false);
        }
    }

    private boolean disconnectService() {
        final WorkingService service = WorkingService.getServiceRef().get();
        if (service != null) {
            service.removeOnServiceCallbackListener(presenter.getCallbackListener());
        }
        return service != null;
    }

    @OnClick(R.id.btn_stop_service)
    public void onStopButtonClick(Button b) {
        stopService();

        btnStartService.setEnabled(true);
        btnStopService.setEnabled(false);
        btnConnectService.setEnabled(false);
        btnDisconnectService.setEnabled(false);
    }

    private void stopService() {
        Intent intent = new Intent(this, WorkingService.class);
        stopService(intent);
    }

    @Override
    public void showTime(LocalDateTime time) {
        tvTime.setText(time.format(formatter));
    }
}

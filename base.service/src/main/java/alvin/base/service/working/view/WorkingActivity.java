package alvin.base.service.working.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import alvin.base.service.R;
import alvin.base.service.working.WorkingContracts;
import alvin.base.service.working.services.WorkingService;
import alvin.lib.mvp.contracts.adapters.ActivityAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkingActivity
        extends ActivityAdapter<WorkingContracts.Presenter>
        implements WorkingContracts.View {

    @BindView(R.id.btn_connect_service) Button btnConnectService;
    @BindView(R.id.btn_disconnect_service) Button btnDisconnectService;
    @BindView(R.id.btn_start_service) Button btnStartService;
    @BindView(R.id.btn_stop_service) Button btnStopService;
    @BindView(R.id.tv_time) TextView tvTime;

    private final WorkingService.OnServiceCallbackListener onServiceCallbackListener =
            time -> presenter.gotResult(time);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.working_activity);

        ButterKnife.bind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        WorkingService.removeOnServiceCallbackListener(onServiceCallbackListener);
        stopService(new Intent(this, WorkingService.class));
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
        WorkingService.addOnServiceCallbackListener(onServiceCallbackListener);

        btnConnectService.setEnabled(false);
        btnDisconnectService.setEnabled(true);
    }

    @OnClick(R.id.btn_disconnect_service)
    public void onDisconnectButtonClick(Button b) {
        WorkingService.removeOnServiceCallbackListener(onServiceCallbackListener);

        btnConnectService.setEnabled(true);
        btnDisconnectService.setEnabled(false);
    }

    @OnClick(R.id.btn_stop_service)
    public void onStopButtonClick(Button b) {
        stopService(new Intent(this, WorkingService.class));

        btnStartService.setEnabled(true);
        btnStopService.setEnabled(false);
        btnConnectService.setEnabled(false);
        btnDisconnectService.setEnabled(false);
    }

    @Override
    public void showResult(final String result) {
        tvTime.setText(result);
    }
}

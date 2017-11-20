package alvin.base.service.working.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.inject.Inject;

import alvin.base.service.R;
import alvin.base.service.working.WorkingContracts;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class WorkingActivity extends AppCompatActivity implements WorkingContracts.View {

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

        AndroidInjection.inject(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.disconnectService();
    }

    @Override
    public Context context() {
        return this;
    }

    @OnClick(R.id.btn_start_service)
    public void onStartButtonClick(Button b) {
        presenter.startService(this);
    }

    @OnClick(R.id.btn_connect_service)
    public void onConnectButtonClick(Button b) {
        presenter.connectService();
    }

    @OnClick(R.id.btn_disconnect_service)
    public void onDisconnectButtonClick(Button b) {
        presenter.disconnectService();
    }

    @OnClick(R.id.btn_stop_service)
    public void onStopButtonClick(Button b) {
        presenter.stopService(this);
    }

    @Override
    public void showTime(LocalDateTime time) {
        tvTime.setText(time.format(formatter));
    }

    @Override
    public void serviceStarted() {
        btnStartService.setEnabled(false);
        btnStopService.setEnabled(true);
        btnConnectService.setEnabled(true);
    }

    @Override
    public void onServiceConnected() {
        btnConnectService.setEnabled(false);
        btnDisconnectService.setEnabled(true);
    }

    @Override
    public void onServiceDisconnected() {
        btnConnectService.setEnabled(true);
        btnDisconnectService.setEnabled(false);
    }

    @Override
    public void serviceStoped() {
        btnStartService.setEnabled(true);
        btnStopService.setEnabled(false);
        btnConnectService.setEnabled(false);
        btnDisconnectService.setEnabled(false);
    }
}

package alvin.base.service.working.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import alvin.base.service.R;
import alvin.base.service.working.services.WorkingService;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkingActivity extends AppCompatActivity {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @BindView(R.id.btn_connect_service)
    Button btnConnectService;

    @BindView(R.id.btn_disconnect_service)
    Button btnDisconnectService;

    @BindView(R.id.btn_start_service)
    Button btnStartService;

    @BindView(R.id.btn_stop_service)
    Button btnStopService;

    @BindView(R.id.tv_time)
    TextView tvTime;

    private final WorkingService.OnServiceCallbackListener onServiceCallbackListener = this::showResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working);

        ButterKnife.bind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        WorkingService.removeOnServiceCallbackListener(onServiceCallbackListener);
        stopService(new Intent(this, WorkingService.class));
    }

    @OnClick(R.id.btn_start_service)
    public void onStartButtonClick() {
        Intent intent = new Intent(this, WorkingService.class);
        intent.putExtra(WorkingService.EXTRA_ARG_ZONE, "Asia/Shanghai");
        startService(intent);

        btnStartService.setEnabled(false);
        btnStopService.setEnabled(true);
        btnConnectService.setEnabled(true);
    }

    @OnClick(R.id.btn_connect_service)
    public void onConnectButtonClick() {
        WorkingService.addOnServiceCallbackListener(onServiceCallbackListener);

        btnConnectService.setEnabled(false);
        btnDisconnectService.setEnabled(true);
    }

    @OnClick(R.id.btn_disconnect_service)
    public void onDisconnectButtonClick() {
        WorkingService.removeOnServiceCallbackListener(onServiceCallbackListener);

        btnConnectService.setEnabled(true);
        btnDisconnectService.setEnabled(false);
    }

    @OnClick(R.id.btn_stop_service)
    public void onStopButtonClick() {
        stopService(new Intent(this, WorkingService.class));

        btnStartService.setEnabled(true);
        btnStopService.setEnabled(false);
        btnConnectService.setEnabled(false);
        btnDisconnectService.setEnabled(false);
    }

    private void showResult(LocalDateTime time) {
        runOnUiThread(() -> tvTime.setText(time.format(DATE_TIME_FORMATTER)));
    }
}

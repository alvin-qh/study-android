package alvin.base.service.bind.views;

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
import alvin.base.service.bind.BindContracts;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class BindActivity extends AppCompatActivity implements BindContracts.View {

    @Inject BindContracts.Presenter presenter;

    @BindView(R.id.btn_bind_service) Button btnBindService;
    @BindView(R.id.btn_unbind_service) Button btnUnbindService;
    @BindView(R.id.tv_time) TextView tvTime;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bind_activity);
        ButterKnife.bind(this);

        AndroidInjection.inject(this);
    }

    @OnClick(R.id.btn_bind_service)
    public void onBindButtonClick(Button b) {
        presenter.bindService(this);
    }

    @OnClick(R.id.btn_unbind_service)
    public void onUnbindButtonClick(Button b) {
        presenter.unbindService(this);
    }

    @Override
    public Context context() {
        return this;
    }

    @Override
    public void showTime(LocalDateTime time) {
        tvTime.setText(time.format(formatter));
    }

    @Override
    public void serviceBind() {
        btnBindService.setEnabled(false);
        btnUnbindService.setEnabled(true);
    }

    @Override
    public void serviceUnbind() {
        btnBindService.setEnabled(true);
        btnUnbindService.setEnabled(false);
    }
}

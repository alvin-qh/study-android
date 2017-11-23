package alvin.base.service.bind.views;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import javax.inject.Inject;

import alvin.base.service.R;
import alvin.base.service.bind.BindContracts;
import alvin.base.service.bind.services.BindService;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class BindActivity extends DaggerAppCompatActivity implements BindContracts.View {

    @Inject BindContracts.Presenter presenter;

    @BindView(R.id.btn_bind_service) Button btnBindService;
    @BindView(R.id.btn_unbind_service) Button btnUnbindService;
    @BindView(R.id.tv_time) TextView tvTime;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private ServiceConnection connection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bind_activity);

        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.onDestroy();
    }

    @OnClick(R.id.btn_bind_service)
    public void onBindButtonClick(Button b) {
        if (connection == null) {
            Intent intent = new Intent(this, BindService.class);
            intent.putExtra(BindService.EXTRA_ARG_ZONE, "Asia/Shanghai");

            connection = new ServiceConnection() {
                /**
                 * When service is connected, this method will be callback.
                 * The service name and IBinder instance will be given.
                 *
                 * @see BindService.ServiceBinder#addTimeCallback(Consumer)
                 */
                @Override
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    if (iBinder instanceof BindContracts.ServiceBinder) {
                        presenter.serviceBind((BindContracts.ServiceBinder) iBinder);
                    }
                }

                /**
                 * Only service is killed unexpected, this method will be callback.
                 */
                @Override
                public void onServiceDisconnected(ComponentName componentName) {
                    presenter.serviceUnbind();
                }
            };

            // Bind service
            bindService(intent, connection, Context.BIND_AUTO_CREATE);

            btnBindService.setEnabled(false);
            btnUnbindService.setEnabled(true);
        }
    }

    @OnClick(R.id.btn_unbind_service)
    public void onUnbindButtonClick(Button b) {
        if (connection != null) {
            unbindService(connection);
            presenter.serviceUnbind();
        }
        btnBindService.setEnabled(true);
        btnUnbindService.setEnabled(false);
    }

    @Override
    public void showTime(LocalDateTime time) {
        tvTime.setText(time.format(formatter));
    }
}

package alvin.adv.service.bind.views;

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
import java.util.function.Consumer;

import alvin.adv.service.R;
import alvin.adv.service.bind.BindContracts;
import alvin.adv.service.bind.services.BindService;
import alvin.lib.mvp.contracts.adapters.ActivityAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BindActivity
        extends ActivityAdapter<BindContracts.Presenter>
        implements BindContracts.View {

    @BindView(R.id.btn_bind_service) Button btnBindService;
    @BindView(R.id.btn_unbind_service) Button btnUnbindService;
    @BindView(R.id.tv_time) TextView tvTime;

    private final Consumer<LocalDateTime> callback = dateTime -> presenter.gotResult(dateTime);

    private ServiceConnection connection;
    private BindService.ServiceBinder binder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bind_activity);

        ButterKnife.bind(this);
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
                    binder = (BindService.ServiceBinder) iBinder;
                    binder.addTimeCallback(callback);
                }

                /**
                 * Only service is killed unexpected, this method will be callback.
                 */
                @Override
                public void onServiceDisconnected(ComponentName componentName) {
                    disconnect();
                }
            };

            // Bind service
            bindService(intent, connection, Context.BIND_AUTO_CREATE);

            btnBindService.setEnabled(false);
            btnUnbindService.setEnabled(true);
        }
    }

    private void disconnect() {
        if (binder != null) {
            binder.removeTimeCallback(callback);
            binder = null;
        }
        if (connection != null) {
            unbindService(connection);
            connection = null;
        }
    }

    @OnClick(R.id.btn_unbind_service)
    public void onUnbindButtonClick(Button b) {
        disconnect();
        btnBindService.setEnabled(true);
        btnUnbindService.setEnabled(false);
    }

    @Override
    public void showResult(String result) {
        tvTime.setText(result);
    }
}

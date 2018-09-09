package alvin.base.service.bind.views;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import alvin.base.service.R;
import alvin.base.service.bind.services.BindService;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BindActivity extends AppCompatActivity {
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @BindView(R.id.btn_bind_service) Button btnBindService;
    @BindView(R.id.btn_unbind_service) Button btnUnbindService;
    @BindView(R.id.tv_time) TextView tvTime;

    private final Consumer<LocalDateTime> callback = dateTime ->
            runOnUiThread(() -> showResult(DATETIME_FORMATTER.format(dateTime)));

    private ServiceConnection connection;
    private BindService.ServiceBinder binder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind);

        ButterKnife.bind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        disconnect(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (connection != null) {
            final Intent intent = new Intent(this, BindService.class);
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
        }
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
                    disconnect(true);
                }
            };

            // Bind service
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
            bindService(intent, connection, Context.BIND_AUTO_CREATE);

            btnBindService.setEnabled(false);
            btnUnbindService.setEnabled(true);
        }
    }

    private void disconnect(boolean keep) {
        if (binder != null) {
            binder.removeTimeCallback();
            binder = null;
        }
        if (connection != null) {
            unbindService(connection);
        }
        if (!keep) {
            connection = null;
        }
    }

    @OnClick(R.id.btn_unbind_service)
    public void onUnbindButtonClick(Button b) {
        btnBindService.setEnabled(true);
        btnUnbindService.setEnabled(false);

        disconnect(false);
    }

    public void showResult(String result) {
        tvTime.setText(result);
    }
}

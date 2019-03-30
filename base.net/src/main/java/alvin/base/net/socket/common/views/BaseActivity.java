package alvin.base.net.socket.common.views;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

import alvin.base.net.R;
import alvin.base.net.socket.SocketContracts;
import alvin.lib.mvp.contracts.adapters.ActivityAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public abstract class BaseActivity extends ActivityAdapter<SocketContracts.NativePresenter>
        implements SocketContracts.NativeView {

    private static final String TAG = BaseActivity.class.getSimpleName();

    public static final int ONE_SECOND = 1000;

    @BindView(R.id.text_ip) TextView textIp;
    @BindView(R.id.btn_start) Button btnStart;
    @BindView(R.id.text_time) TextView textTime;

    private Timer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socket_common_activity_base);

        ButterKnife.bind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
        }
        presenter.bye();
    }

    @Override
    public void showRemoteDatetime(@NonNull final LocalDateTime time) {
        textTime.setText(time.format(DateTimeFormatter.ISO_DATE_TIME));
    }

    @Override
    public void errorCaused(@NonNull final Throwable error) {
        Log.e(TAG, "Exception caused", error);

        if (timer != null) {
            timer.cancel();
        }
        Toast.makeText(this, R.string.error_socket_load_data, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void connectReady() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                presenter.readRemoteDatetime();
            }
        }, 0, ONE_SECOND);
    }

    @OnTextChanged(R.id.text_ip)
    public void onTextIpChanged() {
        btnStart.setEnabled(textIp.getText().length() > 0);
    }

    @OnClick(R.id.btn_start)
    public void onButtonStartClicked() {
        presenter.connect(textIp.getText().toString());
    }

    @OnClick(R.id.btn_stop)
    public void onButtonStopClicked() {
        if (timer != null) {
            timer.cancel();
        }
        presenter.bye();
    }

    @Override
    public void disconnected() {
        Toast.makeText(this, R.string.string_network_disconnected, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRemoteBye() {
        textTime.setText(R.string.string_bye);
    }
}

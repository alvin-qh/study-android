package alvin.adv.net.socket.common.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

import alvin.adv.net.R;
import alvin.adv.net.socket.SocketContracts;
import alvin.lib.mvp.contracts.adapters.ActivityAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class BaseActivity
        extends ActivityAdapter<SocketContracts.Presenter>
        implements SocketContracts.View {

    private static final String TAG = BaseActivity.class.getSimpleName();

    public static final int ONE_SECOND = 1000;

    @BindView(R.id.text_time) TextView textTime;

    private Timer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socket_common_activity_base);

        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
        }
        presenter.disconnect();
    }

    @Override
    public void showConnectError() {
        if (timer != null) {
            timer.cancel();
        }
        Toast.makeText(this, R.string.error_socket_connect, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRemoteDatetime(@NonNull final LocalDateTime time) {
        textTime.setText(time.format(DateTimeFormatter.ISO_DATE_TIME));
    }

    @Override
    public void showException(@NonNull final Throwable error) {
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

    @OnClick(R.id.btn_stop)
    public void onButtonStop(Button b) {
        if (timer != null) {
            timer.cancel();
        }
        presenter.disconnect();
    }

    @Override
    public void disconnected() {
        Toast.makeText(this, R.string.string_network_disconnected, Toast.LENGTH_SHORT).show();
    }
}

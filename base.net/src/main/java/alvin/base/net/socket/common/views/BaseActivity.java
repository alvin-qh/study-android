package alvin.base.net.socket.common.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

import alvin.base.net.R;
import alvin.base.net.socket.SocketContract;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class BaseActivity extends AppCompatActivity implements SocketContract.View {

    private static final String TAG = BaseActivity.class.getSimpleName();

    public static final int ONE_SECOND = 1000;
    @BindView(R.id.text_time)
    TextView textTime;

    private SocketContract.Presenter presenter;
    private Timer timer;

    protected abstract SocketContract.Presenter getPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socket_common_activity_base);

        ButterKnife.bind(this);

        presenter = getPresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timer != null) {
            timer.cancel();
        }
        presenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void showConnectError() {
        if (timer != null) {
            timer.cancel();
        }
        Toast.makeText(this, R.string.error_socket_connect, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showRemoteDatetime(@NonNull LocalDateTime time) {
        textTime.setText(time.format(DateTimeFormatter.ISO_DATE_TIME));
    }

    @Override
    public void showException(@NonNull Throwable error) {
        Log.e(TAG, "Exception caused", error);

        if (timer != null) {
            timer.cancel();
        }
        Toast.makeText(this, R.string.error_socket_load_data, Toast.LENGTH_LONG).show();
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
        Toast.makeText(this, R.string.string_network_disconnected, Toast.LENGTH_LONG).show();
    }
}

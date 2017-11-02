package alvin.net.socket.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

import alvin.net.R;
import alvin.net.socket.SocketContract;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class SocketBaseActivity extends AppCompatActivity implements SocketContract.View {

    @BindView(R.id.text_time)
    TextView textTime;

    private SocketContract.Presenter presenter;
    private Timer timer;

    protected abstract SocketContract.Presenter getPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);

        ButterKnife.bind(this);

        presenter = getPresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.doStarted();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timer != null) {
            timer.cancel();
        }
        presenter.doStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.doDestroy();
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
    public void showRemoteError() {
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
        }, 0, 1000);
    }

    @OnClick(R.id.btn_stop)
    public void onButtonStop(Button b) {
        if (timer != null) {
            timer.cancel();
        }
        presenter.disconnect();
    }
}

package alvin.base.net.socket.presenters;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.common.base.Strings;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.inject.Inject;

import alvin.base.net.socket.SocketContracts;
import alvin.base.net.socket.common.commands.CommandAck;
import alvin.base.net.socket.network.NettyException;
import alvin.base.net.socket.network.NettySocket;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;


public class SocketNettyPresenter extends PresenterAdapter<SocketContracts.NativeView>
        implements SocketContracts.NativePresenter {
    private static final String TAG = SocketNettyPresenter.class.getSimpleName();

    private final NettySocket socket;
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    @Inject
    public SocketNettyPresenter(SocketContracts.NativeView view, NettySocket socket) {
        super(view);
        this.socket = socket;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        this.socket.close();
        this.mainThreadHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void connect() {
        this.socket.connect(new NettySocket.Callback() {
            @Override
            public void onReceived(CommandAck ack) {
                whenCommandReceived(ack);
            }

            @Override
            public void onException(NettyException err) {
                whenErrorCaused(err);
            }

            @Override
            public void onDisconnect() {
                with(SocketContracts.NativeView::disconnected);
            }
        });
    }

    @Override
    public void bye() {
        this.socket.disconnect(command -> Log.d(TAG, "Byt command was sent"));
    }


    @Override
    public void readRemoteDatetime() {
        socket.getRemoteTime(command -> Log.d(TAG, "RemoteTime command was sent"));
    }

    private void whenCommandReceived(CommandAck ack) {
        switch (ack.getCmd()) {
        case "time-ack":
            if (!Strings.isNullOrEmpty(ack.getValue())) {
                LocalDateTime time = LocalDateTime.from(DateTimeFormatter.ISO_DATE_TIME.parse(ack.getValue()));
                mainThreadHandler.post(() -> with(view -> view.showRemoteDatetime(time)));
            }
            break;
        case "bye-ack":
            socket.close();
            mainThreadHandler.post(() -> with(SocketContracts.NativeView::showRemoteBye));
            break;
        default:
            break;
        }
    }

    private void whenErrorCaused(Throwable err) {
        mainThreadHandler.post(() -> with(view -> view.errorCaused(err)));
    }
}

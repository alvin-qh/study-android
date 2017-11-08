package alvin.base.net.socket.presenters;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.google.common.base.Strings;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import alvin.base.net.socket.models.Command;
import alvin.base.net.socket.models.CommandAck;
import alvin.base.net.socket.net.ChannelContext;
import alvin.base.net.socket.net.SocketNetty;
import alvin.base.net.socket.net.SocketNetworkException;
import alvin.lib.mvp.PresenterAdapter;

import static alvin.base.net.socket.SocketContract.Presenter;
import static alvin.base.net.socket.SocketContract.View;

public class SocketNettyPresenter extends PresenterAdapter<View> implements Presenter, SocketNetty.OnNetworkFutureListener {

    private SocketNetty socket;
    private Handler mainThreadHandler;

    public SocketNettyPresenter(@NonNull View view) {
        super(view);
    }

    @Override
    public void started() {
        super.started();
        this.mainThreadHandler = new Handler(Looper.getMainLooper());
        this.socket = new SocketNetty(this);
    }

    @Override
    public void stoped() {
        super.stoped();
        mainThreadHandler.removeCallbacksAndMessages(null);
        disconnect();
    }

    @Override
    public void destroyed() {
        super.destroyed();
    }

    @Override
    public void readRemoteDatetime() {
        if (socket != null) {
            socket.getRemoteTime();
        }
    }

    @Override
    public void disconnect() {
        if (socket != null) {
            socket.disconnect();
        }
    }

    @Override
    public void onConnected(ChannelContext context) {
        withView(View::connectReady);
    }

    @Override
    public void onDisconnected(ChannelContext context) {
        withView(View::disconnected);
    }

    @Override
    public void onCommandReceived(final ChannelContext context, final CommandAck ack) {
        mainThreadHandler.post(() -> {
            switch (ack.getCmd()) {
            case "time-ack":
                if (!Strings.isNullOrEmpty(ack.getValue())) {
                    LocalDateTime time = LocalDateTime.from(DateTimeFormatter.ISO_DATE_TIME.parse(ack.getValue()));
                    withView(view -> view.showRemoteDatetime(time));
                }
                break;
            case "bye-ack":
                socket.close();
                break;
            }
        });
    }

    @Override
    public void onCommandSent(ChannelContext context, Command cmd) {
    }

    @Override
    public void onError(final ChannelContext context, final Throwable t) {
        mainThreadHandler.post(() -> withView(view -> {
            if (t instanceof SocketNetworkException) {
                switch (((SocketNetworkException) t).getErrorStatus()) {
                case CONNECT:
                    view.showConnectError();
                    break;
                default:
                    view.showDefaultError(t);
                }
            } else {
                view.showDefaultError(t);
            }
        }));
    }
}

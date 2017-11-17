package alvin.base.net.socket.netty.presenters;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.google.common.base.Strings;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import alvin.base.net.socket.SocketContract;
import alvin.base.net.socket.common.models.Command;
import alvin.base.net.socket.common.models.CommandAck;
import alvin.base.net.socket.netty.net.ChannelContext;
import alvin.base.net.socket.netty.net.SocketNetty;
import alvin.base.net.socket.netty.net.SocketNetworkException;
import alvin.lib.mvp.PresenterAdapter;


public class NettyPresenter extends PresenterAdapter<SocketContract.View>
        implements SocketContract.Presenter, SocketNetty.OnNetworkFutureListener {

    private SocketNetty socket;
    private Handler mainThreadHandler;

    public NettyPresenter(@NonNull SocketContract.View view) {
        super(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.mainThreadHandler = new Handler(Looper.getMainLooper());
        this.socket = new SocketNetty(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mainThreadHandler.removeCallbacksAndMessages(null);
        disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
        withView(SocketContract.View::connectReady);
    }

    @Override
    public void onDisconnected(ChannelContext context) {
        withView(SocketContract.View::disconnected);
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
            default:
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

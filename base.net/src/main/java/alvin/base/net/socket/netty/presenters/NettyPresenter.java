package alvin.adv.net.socket.netty.presenters;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.google.common.base.Strings;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.inject.Inject;

import alvin.adv.net.socket.SocketContracts;
import alvin.adv.net.socket.common.models.Command;
import alvin.adv.net.socket.common.models.CommandAck;
import alvin.adv.net.socket.netty.net.ChannelContext;
import alvin.adv.net.socket.netty.net.SocketNetty;
import alvin.adv.net.socket.netty.net.SocketNetworkException;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;


public class NettyPresenter
        extends PresenterAdapter<SocketContracts.View>
        implements SocketContracts.Presenter, SocketNetty.OnNetworkFutureListener {

    private SocketNetty socket;
    private Handler mainThreadHandler;

    @Inject
    public NettyPresenter(@NonNull SocketContracts.View view) {
        super(view);
    }

    @Override
    public void connect() {
        this.mainThreadHandler = new Handler(Looper.getMainLooper());
        this.socket = new SocketNetty(this);
    }

    @Override
    public void disconnect() {
        mainThreadHandler.removeCallbacksAndMessages(null);
        if (socket != null) {
            socket.disconnect();
        }
    }


    @Override
    public void readRemoteDatetime() {
        if (socket != null) {
            socket.getRemoteTime();
        }
    }

    @Override
    public void onConnected(ChannelContext context) {
        with(SocketContracts.View::connectReady);
    }

    @Override
    public void onDisconnected(ChannelContext context) {
        mainThreadHandler.post(() -> with(SocketContracts.View::disconnected));
    }

    @Override
    public void onCommandReceived(final ChannelContext context, final CommandAck ack) {
        mainThreadHandler.post(() -> {
            switch (ack.getCmd()) {
            case "time-ack":
                if (!Strings.isNullOrEmpty(ack.getValue())) {
                    LocalDateTime time = LocalDateTime.from(DateTimeFormatter.ISO_DATE_TIME.parse(ack.getValue()));
                    with(view -> view.showRemoteDatetime(time));
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
        mainThreadHandler.post(() -> with(view -> {
            if (t instanceof SocketNetworkException) {
                switch (((SocketNetworkException) t).getErrorStatus()) {
                case CONNECT:
                    view.showConnectError();
                    break;
                default:
                    view.showException(t);
                }
            } else {
                view.showException(t);
            }
        }));
    }
}

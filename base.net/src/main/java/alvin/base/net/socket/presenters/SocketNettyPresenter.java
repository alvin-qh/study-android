package alvin.base.net.socket.presenters;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.google.common.base.Strings;

import java.lang.ref.WeakReference;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import alvin.base.net.socket.SocketContract;
import alvin.base.net.socket.models.Command;
import alvin.base.net.socket.models.CommandAck;
import alvin.base.net.socket.net.ChannelContext;
import alvin.base.net.socket.net.SocketNetty;
import alvin.base.net.socket.net.SocketNetworkException;

public class SocketNettyPresenter implements SocketContract.Presenter, SocketNetty.OnNetworkFutureListener {
    private final WeakReference<SocketContract.View> viewRef;

    private SocketNetty socket;
    private Handler mainThreadHandler;

    public SocketNettyPresenter(@NonNull SocketContract.View view) {
        this.viewRef = new WeakReference<>(view);
    }

    private void withView(Consumer<SocketContract.View> consumer) {
        SocketContract.View view = viewRef.get();
        if (view != null) {
            consumer.accept(view);
        }
    }

    @Override
    public void doStarted() {
        this.mainThreadHandler = new Handler(Looper.getMainLooper());
        this.socket = new SocketNetty(this);
    }

    @Override
    public void doStop() {
        mainThreadHandler.removeCallbacksAndMessages(null);
        disconnect();
    }

    @Override
    public void doDestroy() {
        viewRef.clear();
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
                    view.showRemoteError();
                }
            } else {
                view.showRemoteError();
            }
        }));
    }
}

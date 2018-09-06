package alvin.base.net.status.handlers;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.ArraySet;

import java.util.Set;

import alvin.base.net.status.network.NetworkStatus;

public final class NetStatusEventHandler {
    private static final NetStatusEventHandler INSTANCE = new NetStatusEventHandler();

    private final Handler handler;
    private final Set<OnNetStatusChangedListener> netStatusChangedListeners = new ArraySet<>();

    private NetStatusEventHandler() {
        handler = new MessageHandler(netStatusChangedListeners);
    }

    public void addOnNetStatusChangedListener(@NonNull OnNetStatusChangedListener l) {
        netStatusChangedListeners.add(l);
    }

    public void removeOnNetStatusChangedListener(@NonNull OnNetStatusChangedListener l) {
        netStatusChangedListeners.remove(l);
    }

    public void send(NetworkStatus status) {
        Message message = new Message();
        message.obj = status;
        handler.sendMessage(message);
    }

    public static NetStatusEventHandler getInstance() {
        return INSTANCE;
    }

    private static final class MessageHandler extends Handler {
        private final Set<OnNetStatusChangedListener> netStatusChangedListeners;

        private MessageHandler(Set<OnNetStatusChangedListener> netStatusChangedListeners) {
            this.netStatusChangedListeners = netStatusChangedListeners;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            final NetworkStatus status = (NetworkStatus) msg.obj;
            netStatusChangedListeners.forEach(l -> l.onNetworkStatusChanged(status));
        }
    }
}

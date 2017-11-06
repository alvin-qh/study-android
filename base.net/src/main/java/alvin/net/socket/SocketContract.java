package alvin.net.socket;

import android.support.annotation.NonNull;

import java.time.LocalDateTime;

public final class SocketContract {

    private SocketContract() {
    }

    public interface View {
        void showConnectError();

        void showRemoteDatetime(@NonNull LocalDateTime time);

        void showRemoteError();

        void connectReady();

        void disconnected();
    }

    public interface Presenter {
        void doStarted();

        void doStop();

        void doDestroy();

        void readRemoteDatetime();

        void disconnect();
    }
}

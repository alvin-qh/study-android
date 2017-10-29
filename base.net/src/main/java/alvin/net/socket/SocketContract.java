package alvin.net.socket;

import java.time.LocalDateTime;

public final class SocketContract {

    public interface View {
        void showConnectError();

        void showRemoteDatetime(LocalDateTime time);

        void showRemoteError();

        void connectReady();
    }

    public interface Presenter {
        void doStarted();

        void doStop();

        void doDestroy();

        void readRemoteDatetime();

        void disconnect();
    }
}

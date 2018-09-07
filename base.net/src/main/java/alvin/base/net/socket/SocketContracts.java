package alvin.base.net.socket;

import java.time.LocalDateTime;

import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.IView;

public interface SocketContracts {

    interface NativeView extends IView {

        void errorCaused(Throwable error);

        void showRemoteDatetime(LocalDateTime time);

        void connectReady();

        void disconnected();

        void showRemoteBye();
    }

    interface NativePresenter extends IPresenter {

        void readRemoteDatetime();

        void connect();

        void bye();
    }
}

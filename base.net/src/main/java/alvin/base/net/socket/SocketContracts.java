package alvin.adv.net.socket;

import android.support.annotation.NonNull;

import java.time.LocalDateTime;

import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.IView;

public interface SocketContracts {

    interface View extends IView {

        void showConnectError();

        void showException(@NonNull Throwable error);

        void showRemoteDatetime(@NonNull LocalDateTime time);

        void connectReady();

        void disconnected();
    }

    interface Presenter extends IPresenter {

        void readRemoteDatetime();

        void connect();

        void disconnect();
    }
}

package alvin.base.net.socket;

import android.support.annotation.NonNull;

import java.time.LocalDateTime;

import alvin.lib.mvp.IPresenter;
import alvin.lib.mvp.IView;

public interface SocketContract {

    interface View extends IView {

        void showConnectError();

        void showRemoteDatetime(@NonNull LocalDateTime time);

        void connectReady();

        void disconnected();
    }

    interface Presenter extends IPresenter {

        void readRemoteDatetime();

        void disconnect();
    }
}

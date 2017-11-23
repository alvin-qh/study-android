package alvin.base.net.socket;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.time.LocalDateTime;

import alvin.base.net.R;
import alvin.lib.mvp.IPresenter;
import alvin.lib.mvp.IView;

public interface SocketContract {

    interface View extends IView {

        void showConnectError();

        void showException(@NonNull Throwable error);

        void showRemoteDatetime(@NonNull LocalDateTime time);

        void connectReady();

        void disconnected();
    }

    interface Presenter extends IPresenter {

        void readRemoteDatetime();

        void disconnect();
    }
}

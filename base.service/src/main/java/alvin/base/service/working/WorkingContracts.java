package alvin.base.service.working;

import android.content.Context;

import java.time.LocalDateTime;

import alvin.lib.mvp.IPresenter;
import alvin.lib.mvp.IView;

public interface WorkingContracts {

    interface View extends IView {

        void showTime(LocalDateTime time);

        void serviceStarted();

        void onServiceConnected();

        void onServiceDisconnected();

        void serviceStoped();
    }

    interface Presenter extends IPresenter {

        void startService(Context context);

        void stopService(Context context);

        void connectService();

        void disconnectService();
    }
}

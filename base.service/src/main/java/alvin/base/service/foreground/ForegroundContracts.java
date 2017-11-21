package alvin.base.service.foreground;

import android.content.Context;

import alvin.lib.mvp.IPresenter;
import alvin.lib.mvp.IView;

public interface ForegroundContracts {

    interface View extends IView {

        void serviceCreated();

        void serviceDestroyed();
    }

    interface Presenter extends IPresenter {

        void startReceiver(Context context);

        void stopReceiver(Context context);

        void startService(Context context);

        void stopService(Context context);
    }
}

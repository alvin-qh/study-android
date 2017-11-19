package alvin.base.service.lifecycle;

import android.content.Context;

import alvin.lib.mvp.IPresenter;
import alvin.lib.mvp.IView;

public interface LifecycleContracts {

    interface View extends IView {
        void serviceDestroyed();

        void serviceCreated();

        void showStartCount(int count);
    }

    interface Presenter extends IPresenter {
        void registerReceiver(Context context);

        void unregisterReceiver(Context context);

        void startService(Context context);

        void bindService(Context context);

        void unbindService(Context context);

        void stopService(Context context);
    }
}

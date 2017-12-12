package alvin.base.service.lifecycle;

import android.content.BroadcastReceiver;
import android.content.ServiceConnection;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.IView;

public interface LifecycleContracts {

    interface View extends IView {
        void serviceDestroyed();

        void serviceCreated();

        void showStartCount(int count);
    }

    interface Presenter extends IPresenter {

        @NonNull
        BroadcastReceiver getReceiver();

        void serviceStarted();

        void serviceStoped();

        void serviceBound(@NonNull ServiceConnection conn);

        @Nullable
        ServiceConnection unbindService();
    }
}

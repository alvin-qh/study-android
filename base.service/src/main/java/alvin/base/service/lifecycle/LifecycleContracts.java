package alvin.base.service.lifecycle;

import android.content.ServiceConnection;
import android.support.annotation.NonNull;

import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.IView;

public interface LifecycleContracts {

    interface View extends IView {

        void showStartCount(int count);

        void unbindService(final ServiceConnection conn);
    }

    interface Presenter extends IPresenter {

        void serviceStarted();

        void serviceStoped();

        void serviceBound(@NonNull ServiceConnection conn);

        void serviceUnbound();
    }
}

package alvin.base.service.foreground;

import android.content.BroadcastReceiver;

import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.IView;

public interface ForegroundContracts {

    interface View extends IView {

        void serviceCreated();

        void serviceDestroyed();
    }

    interface Presenter extends IPresenter {

        BroadcastReceiver getBroadcastReceiver();
    }
}

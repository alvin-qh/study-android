package alvin.base.service.foreground;

import android.content.BroadcastReceiver;

import alvin.lib.mvp.IPresenter;
import alvin.lib.mvp.IView;

public interface ForegroundContracts {

    interface View extends IView {

        void serviceCreated();

        void serviceDestroyed();
    }

    interface Presenter extends IPresenter {

        BroadcastReceiver getBroadcastReceiver();
    }
}

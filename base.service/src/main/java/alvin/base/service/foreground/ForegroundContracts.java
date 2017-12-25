package alvin.base.service.foreground;

import android.content.BroadcastReceiver;

import alvin.lib.mvp.contracts.IPresenter;

public interface ForegroundContracts {

    interface IView extends alvin.lib.mvp.contracts.IView {

        void serviceCreated();

        void serviceDestroyed();
    }

    interface Presenter extends IPresenter {

        BroadcastReceiver getBroadcastReceiver();
    }
}

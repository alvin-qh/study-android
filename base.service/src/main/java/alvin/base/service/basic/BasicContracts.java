package alvin.base.service.basic;

import alvin.lib.mvp.IPresenter;
import alvin.lib.mvp.IView;

public interface BasicContracts {

    interface View extends IView {
        void serviceStarted();

        void serviceDestroyed();
    }

    interface Presenter extends IPresenter {
    }
}

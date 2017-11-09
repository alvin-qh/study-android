package alvin.base.mvp.scope;

import alvin.lib.mvp.IPresenter;
import alvin.lib.mvp.IView;

public interface ScopeContract {

    interface View extends IView {
        void showSingletonService(String name);
    }

    interface Presenter extends IPresenter {
    }
}

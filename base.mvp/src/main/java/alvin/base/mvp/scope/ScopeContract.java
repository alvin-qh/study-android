package alvin.base.mvp.scope;

import alvin.lib.mvp.IPresenter;
import alvin.lib.mvp.IView;

public interface ScopeContract {

    interface ScopeActivityView extends IView {
        void showSingletonService(String name);
    }

    interface ScopeFragmentView extends ScopeActivityView {
        void showSessionService(String name);
    }

    interface ScopeActivityPresenter extends IPresenter {
    }

    interface ScopeFragmentPresenter extends IPresenter {

    }
}

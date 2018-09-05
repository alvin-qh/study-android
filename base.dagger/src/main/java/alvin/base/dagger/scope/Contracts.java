package alvin.base.dagger.scope;

import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.IView;

public interface Contracts {

    interface View extends IView {
        void showActivityScopeService(String name);

        void showFragmentScopeService(String name);
    }

    interface Presenter extends IPresenter {
        void serviceName();
    }
}

package alvin.base.dagger.scope.subcomponent;

import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.IView;

public interface SubcomponentContracts {

    interface View extends IView {
        void showActivityScopeService(String name);
    }

    interface Presenter extends IPresenter {
        void serviceName();
    }
}

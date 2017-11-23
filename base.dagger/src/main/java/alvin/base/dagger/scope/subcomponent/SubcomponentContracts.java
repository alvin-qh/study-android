package alvin.base.dagger.scope.subcomponent;

import alvin.lib.mvp.IPresenter;
import alvin.lib.mvp.IView;

public interface SubcomponentContracts {

    interface View extends IView {
        void showActivityScopeService(String name);
    }

    interface Presenter extends IPresenter {
    }
}

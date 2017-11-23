package alvin.base.dagger.scope.dependency;

import alvin.lib.mvp.IPresenter;
import alvin.lib.mvp.IView;

public interface DependencyContracts {

    interface View extends IView {
        void showActivityScopeService(String name);
    }

    interface Presenter extends IPresenter {
    }
}

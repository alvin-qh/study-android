package alvin.base.dagger.scope.subcomponent.fragment;

import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.IView;

public interface FragmentContracts {

    interface View extends IView {
        void showActivityScopeService(String name);

        void showFragmentScopeService(String name);
    }

    interface Presenter extends IPresenter {
    }
}

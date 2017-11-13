package alvin.base.mvp.scope.subcomponent.fragment;

import alvin.lib.mvp.IPresenter;
import alvin.lib.mvp.IView;

public interface FragmentContracts {

    interface View extends IView {
        void showActivityScopeService(String name);

        void showFragmentScopeService(String name);
    }

    interface Presenter extends IPresenter {
    }
}

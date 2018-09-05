package alvin.base.dagger.scope;

import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.IView;

public interface ScopeContracts {

    interface ActivityView extends IView {
        void showActivityScopeService(String name);
    }

    interface FragmentView extends IView {
        void showActivityScopeService(String name);

        void showFragmentScopeService(String name);
    }

    interface ActivityPresenter extends IPresenter {
        void serviceName();
    }

    interface FragmentPresenter extends IPresenter {
        void serviceName();
    }
}

package alvin.base.mvp.scope;

import alvin.lib.mvp.IPresenter;
import alvin.lib.mvp.IView;

public interface ScopeContract {

    interface ActivityView extends IView {
        void showActivityScopeService(String name);
    }

    interface FragmentView extends IView {
        void showActivityScopeService(String name);

        void showFragmentScopeService(String name);
    }

    interface ActivityPresenter extends IPresenter {
    }

    interface FragmentPresenter extends IPresenter {
    }
}

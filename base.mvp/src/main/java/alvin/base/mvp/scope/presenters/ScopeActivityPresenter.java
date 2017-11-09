package alvin.base.mvp.scope.presenters;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import alvin.base.mvp.scope.ScopeContract;
import alvin.base.mvp.scope.domain.service.ScopeActivityService;
import alvin.base.mvp.utils.ObjectNames;
import alvin.lib.mvp.PresenterAdapter;

public class ScopeActivityPresenter extends PresenterAdapter<ScopeContract.ScopeActivityView>
        implements ScopeContract.ScopeActivityPresenter {

    private final ScopeActivityService scopeActivityService;

    @Inject
    ScopeActivityPresenter(@NonNull ScopeContract.ScopeActivityView view,
                           @NonNull ScopeActivityService scopeActivityService) {
        super(view);
        this.scopeActivityService = scopeActivityService;
    }

    @Override
    public void started() {
        super.started();

        withView(view -> view.showSingletonService(ObjectNames.simpleName(scopeActivityService)));
    }
}

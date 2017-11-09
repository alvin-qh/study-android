package alvin.base.mvp.scope.presenters;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import alvin.base.mvp.scope.ScopeContract;
import alvin.base.mvp.scope.domain.service.ScopeActivityService;
import alvin.base.mvp.scope.domain.service.ScopeFragmentService;
import alvin.base.mvp.utils.ObjectNames;
import alvin.lib.mvp.PresenterAdapter;

public class ScopeFragmentPresenter extends PresenterAdapter<ScopeContract.ScopeFragmentView>
        implements ScopeContract.ScopeFragmentPresenter {

    private final ScopeActivityService scopeActivityService;
    private final ScopeFragmentService scopeFragmentService;

    @Inject
    ScopeFragmentPresenter(@NonNull ScopeContract.ScopeFragmentView view,
                           @NonNull ScopeActivityService scopeActivityService,
                           @NonNull ScopeFragmentService scopeFragmentService) {
        super(view);
        this.scopeActivityService = scopeActivityService;
        this.scopeFragmentService = scopeFragmentService;
    }

    @Override
    public void started() {
        super.started();

        withView(view -> view.showSingletonService(ObjectNames.simpleName(scopeActivityService)));
        withView(view -> view.showSessionService(ObjectNames.simpleName(scopeFragmentService)));
    }
}

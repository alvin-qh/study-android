package alvin.base.dagger.scope.presenters;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import alvin.adv.dagger.scope.domain.service.ActivityScopeService;
import alvin.adv.dagger.utils.ObjectNames;
import alvin.base.dagger.scope.ScopeContracts;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;

public class DependencyPresenter extends PresenterAdapter<ScopeContracts.View> implements ScopeContracts.Presenter {

    private final ActivityScopeService activityScopeService;

    @Inject
    DependencyPresenter(@NonNull ScopeContracts.View view,
                        @NonNull ActivityScopeService activityScopeService) {
        super(view);
        this.activityScopeService = activityScopeService;
    }

    @Override
    public void serviceName() {
        with(view -> view.showActivityScopeService(ObjectNames.simpleName(activityScopeService)));
    }
}

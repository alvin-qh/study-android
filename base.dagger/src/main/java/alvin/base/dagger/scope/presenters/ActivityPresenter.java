package alvin.base.dagger.scope.presenters;

import javax.inject.Inject;

import alvin.base.dagger.scope.ScopeContracts;
import alvin.base.dagger.scope.domain.service.ActivityScopeService;
import alvin.base.dagger.utils.ObjectNames;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;

import static alvin.base.dagger.scope.ScopeContracts.ActivityView;

public class ActivityPresenter extends PresenterAdapter<ActivityView> implements ScopeContracts.ActivityPresenter {

    private final ActivityScopeService activityScopeService;

    @Inject
    ActivityPresenter(ActivityView view, ActivityScopeService activityScopeService) {
        super(view);
        this.activityScopeService = activityScopeService;
    }

    @Override
    public void serviceName() {
        with(view -> view.showActivityScopeService(ObjectNames.simpleName(activityScopeService)));
    }
}

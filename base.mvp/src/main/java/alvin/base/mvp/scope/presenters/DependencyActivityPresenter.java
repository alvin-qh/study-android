package alvin.base.mvp.scope.presenters;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import alvin.base.mvp.scope.ScopeContract;
import alvin.base.mvp.scope.domain.service.ActivityScopeService;
import alvin.base.mvp.utils.ObjectNames;
import alvin.lib.mvp.PresenterAdapter;

public class DependencyActivityPresenter extends PresenterAdapter<ScopeContract.ActivityView>
        implements ScopeContract.ActivityPresenter {

    private final ActivityScopeService activityScopeService;

    @Inject
    DependencyActivityPresenter(@NonNull ScopeContract.ActivityView view,
                                @NonNull ActivityScopeService activityScopeService) {
        super(view);
        this.activityScopeService = activityScopeService;
    }

    @Override
    public void started() {
        super.started();

        withView(view -> view.showActivityScopeService(ObjectNames.simpleName(activityScopeService)));
    }
}

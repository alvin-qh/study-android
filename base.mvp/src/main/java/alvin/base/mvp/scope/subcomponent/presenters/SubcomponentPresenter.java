package alvin.base.mvp.scope.subcomponent.presenters;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import alvin.base.mvp.scope.domain.service.ActivityScopeService;
import alvin.base.mvp.scope.subcomponent.SubcomponentContracts;
import alvin.base.mvp.utils.ObjectNames;
import alvin.lib.mvp.PresenterAdapter;

public class SubcomponentPresenter extends PresenterAdapter<SubcomponentContracts.View>
        implements SubcomponentContracts.Presenter {

    private final ActivityScopeService activityScopeService;

    @Inject
    SubcomponentPresenter(@NonNull SubcomponentContracts.View view,
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

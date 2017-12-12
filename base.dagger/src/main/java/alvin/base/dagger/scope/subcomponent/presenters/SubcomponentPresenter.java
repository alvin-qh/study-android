package alvin.base.dagger.scope.subcomponent.presenters;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import alvin.base.dagger.scope.domain.service.ActivityScopeService;
import alvin.base.dagger.scope.subcomponent.SubcomponentContracts;
import alvin.base.dagger.utils.ObjectNames;
import alvin.lib.mvp.adapters.ViewPresenterAdapter;

public class SubcomponentPresenter extends ViewPresenterAdapter<SubcomponentContracts.View>
        implements SubcomponentContracts.Presenter {

    private final ActivityScopeService activityScopeService;

    @Inject
    SubcomponentPresenter(@NonNull SubcomponentContracts.View view,
                          @NonNull ActivityScopeService activityScopeService) {
        super(view);
        this.activityScopeService = activityScopeService;
    }

    @Override
    public void onStart() {
        super.onStart();

        withView(view -> view.showActivityScopeService(ObjectNames.simpleName(activityScopeService)));
    }
}

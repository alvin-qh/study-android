package alvin.base.mvp.scope.presenters;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import alvin.base.mvp.scope.ScopeContract;
import alvin.base.mvp.scope.domain.service.ActivityScopeService;
import alvin.base.mvp.scope.domain.service.FragmentScopeService;
import alvin.base.mvp.utils.ObjectNames;
import alvin.lib.mvp.PresenterAdapter;

public class SubcompFragmentPresenter extends PresenterAdapter<ScopeContract.FragmentView>
        implements ScopeContract.FragmentPresenter {

    private final ActivityScopeService activityScopeService;
    private final FragmentScopeService fragmentScopeService;

    @Inject
    SubcompFragmentPresenter(@NonNull ScopeContract.FragmentView view,
                             @NonNull ActivityScopeService activityScopeService,
                             @NonNull FragmentScopeService fragmentScopeService) {
        super(view);
        this.activityScopeService = activityScopeService;
        this.fragmentScopeService = fragmentScopeService;
    }

    @Override
    public void started() {
        super.started();

        withView(view -> view.showActivityScopeService(ObjectNames.simpleName(activityScopeService)));
        withView(view -> view.showFragmentScopeService(ObjectNames.simpleName(fragmentScopeService)));
    }
}

package alvin.base.dagger.scope.subcomponent.fragment.presenters;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import alvin.base.dagger.scope.domain.service.ActivityScopeService;
import alvin.base.dagger.scope.domain.service.FragmentScopeService;
import alvin.base.dagger.scope.subcomponent.fragment.FragmentContracts;
import alvin.base.dagger.utils.ObjectNames;
import alvin.lib.mvp.adapters.ViewPresenterAdapter;

public class FragmentPresenter extends ViewPresenterAdapter<FragmentContracts.View>
        implements FragmentContracts.Presenter {

    private final ActivityScopeService activityScopeService;
    private final FragmentScopeService fragmentScopeService;

    @Inject
    FragmentPresenter(@NonNull FragmentContracts.View view,
                      @NonNull ActivityScopeService activityScopeService,
                      @NonNull FragmentScopeService fragmentScopeService) {
        super(view);
        this.activityScopeService = activityScopeService;
        this.fragmentScopeService = fragmentScopeService;
    }

    @Override
    public void onStart() {
        super.onStart();

        withView(view -> view.showActivityScopeService(ObjectNames.simpleName(activityScopeService)));
        withView(view -> view.showFragmentScopeService(ObjectNames.simpleName(fragmentScopeService)));
    }
}

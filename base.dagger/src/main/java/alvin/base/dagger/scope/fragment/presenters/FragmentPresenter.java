package alvin.base.dagger.scope.fragment.presenters;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import alvin.adv.dagger.scope.dependency.fragment.FragmentContracts;
import alvin.adv.dagger.scope.domain.service.ActivityScopeService;
import alvin.adv.dagger.scope.domain.service.FragmentScopeService;
import alvin.adv.dagger.utils.ObjectNames;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;

public class FragmentPresenter
        extends PresenterAdapter<FragmentContracts.View>
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
    public void serviceName() {
        with(view -> view.showActivityScopeService(ObjectNames.simpleName(activityScopeService)));
        with(view -> view.showFragmentScopeService(ObjectNames.simpleName(fragmentScopeService)));
    }
}

package alvin.base.dagger.scope.presenters;

import javax.inject.Inject;

import alvin.base.dagger.scope.ScopeContracts;
import alvin.base.dagger.scope.domain.service.ActivityScopeService;
import alvin.base.dagger.scope.domain.service.FragmentScopeService;
import alvin.base.dagger.utils.ObjectNames;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;

import static alvin.base.dagger.scope.ScopeContracts.FragmentView;

public class FragmentPresenter extends PresenterAdapter<FragmentView>
        implements ScopeContracts.FragmentPresenter {

    private final ActivityScopeService activityScopeService;
    private final FragmentScopeService fragmentScopeService;

    @Inject
    FragmentPresenter(FragmentView view,
                      ActivityScopeService activityScopeService,
                      FragmentScopeService fragmentScopeService) {
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

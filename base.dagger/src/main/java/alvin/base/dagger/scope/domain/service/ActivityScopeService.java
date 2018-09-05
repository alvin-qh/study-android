package alvin.adv.dagger.scope.domain.service;

import javax.inject.Inject;

import alvin.adv.dagger.scope.Scopes;


@Scopes.Activity
public class ActivityScopeService {

    @Inject
    public ActivityScopeService() {
    }
}

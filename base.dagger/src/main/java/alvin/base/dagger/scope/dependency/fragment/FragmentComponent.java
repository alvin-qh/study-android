package alvin.base.dagger.scope.dependency.fragment;

import alvin.base.dagger.scope.Scopes;
import alvin.base.dagger.scope.dependency.DependencyComponent;
import alvin.base.dagger.scope.dependency.fragment.views.DependencyFragment;
import dagger.Component;

@Scopes.Fragment
@Component(dependencies = {DependencyComponent.class},
        modules = {FragmentModule.class})
public interface FragmentComponent {

    void inject(DependencyFragment fragment);

    FragmentContracts.View view();

    FragmentContracts.Presenter presenter();
}

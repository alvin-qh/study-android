package alvin.base.dagger.scope.subcomponent;

import alvin.base.dagger.scope.Scopes;
import alvin.base.dagger.scope.subcomponent.fragment.FragmentComponent;
import alvin.base.dagger.scope.subcomponent.views.SubcomponentActivity;
import dagger.Component;

@Scopes.Activity
@Component(modules = {SubcomponentModule.class})
public interface SubcomponentComponent {

    void inject(SubcomponentActivity activity);

    FragmentComponent.Builder fragmentComponentBuilder();
}

package alvin.base.mvp.scope.subcomponent;

import alvin.base.mvp.scope.Scopes;
import alvin.base.mvp.scope.subcomponent.fragment.FragmentComponent;
import alvin.base.mvp.scope.subcomponent.views.SubcomponentActivity;
import dagger.Component;

@Scopes.Activity
@Component(modules = {SubcomponentModule.class})
public interface SubcomponentComponent {

    void inject(SubcomponentActivity activity);

    FragmentComponent.Builder fragmentComponentBuilder();
}

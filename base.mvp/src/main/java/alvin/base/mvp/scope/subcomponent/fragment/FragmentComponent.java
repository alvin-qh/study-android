package alvin.base.mvp.scope.subcomponent.fragment;

import alvin.base.mvp.scope.Scopes;
import alvin.base.mvp.scope.subcomponent.fragment.views.SubcomponentFragment;
import dagger.Subcomponent;

@Scopes.Fragment
@Subcomponent(modules = {FragmentModule.class})
public interface FragmentComponent {
    void inject(SubcomponentFragment fragment);

    @Subcomponent.Builder
    interface Builder {
        Builder fragmentModule(FragmentModule module);

        FragmentComponent build();
    }
}

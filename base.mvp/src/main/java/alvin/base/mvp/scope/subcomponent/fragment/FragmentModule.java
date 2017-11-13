package alvin.base.mvp.scope.subcomponent.fragment;

import alvin.base.mvp.scope.Scopes;
import alvin.base.mvp.scope.subcomponent.fragment.presenters.FragmentPresenter;
import alvin.base.mvp.scope.subcomponent.fragment.views.SubcomponentFragment;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {FragmentModule.BindModule.class})
public class FragmentModule {

    private final SubcomponentFragment fragment;

    public FragmentModule(SubcomponentFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @Scopes.Fragment
    public FragmentContracts.View view() {
        return fragment;
    }

    @Module
    public interface BindModule {

        @Binds
        @Scopes.Fragment
        FragmentContracts.Presenter presenter(FragmentPresenter presenter);
    }
}

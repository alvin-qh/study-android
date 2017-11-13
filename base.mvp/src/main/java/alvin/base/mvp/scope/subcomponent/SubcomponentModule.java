package alvin.base.mvp.scope.subcomponent;

import alvin.base.mvp.scope.Scopes;
import alvin.base.mvp.scope.subcomponent.presenters.SubcomponentPresenter;
import alvin.base.mvp.scope.subcomponent.views.SubcomponentActivity;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {SubcomponentModule.BindModule.class})
public final class SubcomponentModule {

    private final SubcomponentActivity activity;

    public SubcomponentModule(SubcomponentActivity activity) {
        this.activity = activity;
    }

    @Provides
    @Scopes.Activity
    SubcomponentContracts.View view() {
        return activity;
    }

    @Module
    public interface BindModule {

        @Binds
        @Scopes.Activity
        SubcomponentContracts.Presenter presenter(SubcomponentPresenter presenter);
    }
}

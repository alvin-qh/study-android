package alvin.base.mvp.scope.di;

import alvin.base.mvp.scope.ScopeContract;
import alvin.base.mvp.scope.presenters.SubcompActivityPresenter;
import alvin.base.mvp.scope.presenters.SubcompFragmentPresenter;
import alvin.base.mvp.scope.views.SubcomponentActivity;
import alvin.base.mvp.scope.views.SubcomponentFragment;
import dagger.Binds;
import dagger.Component;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;

public interface SubcomponentComponents {

    @Scopes.Activity
    @Component(modules = {SubcomponentActivityModule.class})
    interface SubcomponentActivityComponent {

        void inject(SubcomponentActivity activity);

        SubcomponentFragmentComponent.Builder subcomponentFragmentComponentBuilder();
    }

    @Module(includes = {SubcomponentActivityModule.BindModule.class})
    final class SubcomponentActivityModule {

        private final SubcomponentActivity activity;

        public SubcomponentActivityModule(SubcomponentActivity activity) {
            this.activity = activity;
        }

        @Provides
        @Scopes.Activity
        ScopeContract.ActivityView view() {
            return activity;
        }

        @Module
        public interface BindModule {

            @Binds
            @Scopes.Activity
            ScopeContract.ActivityPresenter presenter(SubcompActivityPresenter presenter);
        }
    }

    @Scopes.Fragment
    @Subcomponent(modules = {SubcomponentFragmentModule.class})
    interface SubcomponentFragmentComponent {
        void inject(SubcomponentFragment fragment);

        @Subcomponent.Builder
        interface Builder {
            Builder fragmentModule(SubcomponentFragmentModule module);

            SubcomponentFragmentComponent build();
        }
    }

    @Module(includes = {SubcomponentFragmentModule.BindModule.class})
    class SubcomponentFragmentModule {

        private final SubcomponentFragment fragment;

        public SubcomponentFragmentModule(SubcomponentFragment fragment) {
            this.fragment = fragment;
        }

        @Provides
        @Scopes.Fragment
        public ScopeContract.FragmentView view() {
            return fragment;
        }

        @Module
        public interface BindModule {

            @Binds
            @Scopes.Fragment
            ScopeContract.FragmentPresenter presenter(SubcompFragmentPresenter presenter);
        }
    }
}

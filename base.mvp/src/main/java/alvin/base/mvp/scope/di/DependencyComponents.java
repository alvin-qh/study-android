package alvin.base.mvp.scope.di;

import alvin.base.mvp.scope.ScopeContract;
import alvin.base.mvp.scope.domain.service.ActivityScopeService;
import alvin.base.mvp.scope.presenters.DependencyActivityPresenter;
import alvin.base.mvp.scope.presenters.DependencyFragmentPresenter;
import alvin.base.mvp.scope.views.DependencyActivity;
import alvin.base.mvp.scope.views.DependencyFragment;
import dagger.Binds;
import dagger.Component;
import dagger.Module;
import dagger.Provides;

public interface DependencyComponents {

    @Scopes.Activity
    @Component(modules = {DependencyActivityModule.class})
    interface DependencyActivityComponent {

        void inject(DependencyActivity activity);

        ScopeContract.ActivityView view();

        ScopeContract.ActivityPresenter presenter();

        ActivityScopeService activityScopeService();
    }

    @Module(includes = {DependencyActivityModule.BindModule.class})
    final class DependencyActivityModule {

        private final DependencyActivity activity;

        public DependencyActivityModule(DependencyActivity activity) {
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
            ScopeContract.ActivityPresenter presenter(DependencyActivityPresenter presenter);
        }
    }

    @Scopes.Fragment
    @Component(dependencies = {DependencyActivityComponent.class},
            modules = {DependencyFragmentModule.class})
    interface DependencyFragmentComponent {

        void inject(DependencyFragment fragment);

        ScopeContract.FragmentView view();

        ScopeContract.FragmentPresenter presenter();
    }

    @Module(includes = {DependencyFragmentModule.BindModule.class})
    class DependencyFragmentModule {

        private final DependencyFragment fragment;

        public DependencyFragmentModule(DependencyFragment fragment) {
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
            ScopeContract.FragmentPresenter presenter(DependencyFragmentPresenter presenter);
        }
    }
}

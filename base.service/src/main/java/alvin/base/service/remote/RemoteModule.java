package alvin.base.service.remote;

import alvin.base.service.remote.presenters.RemotePresenter;
import alvin.base.service.remote.services.RemoteService;
import alvin.base.service.remote.tasks.Task;
import alvin.base.service.remote.views.RemoteActivity;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public interface RemoteModule {

    @ContributesAndroidInjector(modules = {RemoteModule.ViewModule.class})
    RemoteActivity remoteActivity();

    @ContributesAndroidInjector(modules = {RemoteModule.ServiceModule.class})
    RemoteService remoteService();

    @Module(includes = {RemoteModule.ViewModule.BindModule.class})
    class ViewModule {

        @Module
        public interface BindModule {
            @Binds
            RemoteContracts.View view(RemoteActivity activity);

            @Binds
            RemoteContracts.Presenter presenter(RemotePresenter presenter);
        }
    }

    @Module
    class ServiceModule {
        static final int MIN_DELAY = 1000;
        static final int MAX_DELAY = 3000;

        @Provides
        Task task() {
            return new Task(MIN_DELAY, MAX_DELAY);
        }
    }
}

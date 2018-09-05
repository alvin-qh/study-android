package alvin.base.service.remote;

import alvin.base.service.remote.services.RemoteService;
import alvin.base.service.remote.tasks.Task;
import alvin.base.service.remote.views.RemoteActivity;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public interface RemoteModule {

    @ContributesAndroidInjector
    RemoteActivity remoteActivity();

    @ContributesAndroidInjector(modules = {RemoteModule.ServiceModule.class})
    RemoteService remoteService();

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

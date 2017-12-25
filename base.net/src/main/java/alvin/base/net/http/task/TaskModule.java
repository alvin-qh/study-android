package alvin.base.net.http.task;

import alvin.base.net.http.WeatherContracts;
import alvin.base.net.http.common.domain.services.WeatherService;
import alvin.base.net.http.task.presenters.TaskPresenter;
import alvin.base.net.http.task.views.TaskActivity;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public interface TaskModule {

    @ContributesAndroidInjector(modules = {
            ViewModule.class,
            ProvidersModule.class
    })
    TaskActivity activity();

    @Module
    interface ViewModule {
        @Binds
        WeatherContracts.View view(final TaskActivity activity);

        @Binds
        WeatherContracts.Presenter presenter(final TaskPresenter presenter);
    }

    @Module
    class ProvidersModule {
        @Provides
        public static WeatherService weatherService() {
            return new WeatherService();
        }
    }
}

package alvin.adv.net.http.task;

import alvin.adv.net.http.WeatherContracts;
import alvin.adv.net.http.common.domain.services.WeatherService;
import alvin.adv.net.http.task.presenters.TaskPresenter;
import alvin.adv.net.http.task.views.TaskActivity;
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

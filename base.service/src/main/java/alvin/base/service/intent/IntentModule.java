package alvin.base.service.intent;

import alvin.base.service.intent.presenters.IntentPresenter;
import alvin.base.service.intent.services.IntentService;
import alvin.base.service.intent.tasks.Task;
import alvin.base.service.intent.views.IntentActivity;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public interface IntentModule {

    @ContributesAndroidInjector(modules = {IntentModule.ViewModule.class})
    IntentActivity intentActivity();

    @ContributesAndroidInjector(modules = {IntentModule.ServiceModule.class})
    IntentService intentService();

    @Module(includes = {ViewModule.BindModule.class})
    class ViewModule {

        @Module
        public interface BindModule {
            @Binds
            IntentContracts.View view(IntentActivity activity);

            @Binds
            IntentContracts.Presenter presenter(IntentPresenter presenter);
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

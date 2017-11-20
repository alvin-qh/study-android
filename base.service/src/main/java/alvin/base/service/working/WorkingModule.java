package alvin.base.service.working;

import alvin.base.service.working.presenters.WorkingPresenter;
import alvin.base.service.working.services.WorkingService;
import alvin.base.service.working.view.WorkingActivity;
import alvin.lib.common.rx.RxManager;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public interface WorkingModule {

    @ContributesAndroidInjector(modules = {WorkingModule.ViewModule.class})
    WorkingActivity workingActivity();

    @ContributesAndroidInjector(modules = {WorkingModule.ServiceModule.class})
    WorkingService workingService();

    @Module(includes = {ViewModule.BindModule.class})
    class ViewModule {

        @Module
        public interface BindModule {
            @Binds
            WorkingContracts.View view(WorkingActivity activity);

            @Binds
            WorkingContracts.Presenter presenter(WorkingPresenter presenter);
        }
    }

    @Module
    class ServiceModule {

        @Provides
        RxManager rxManager() {
            return RxManager.newBuilder()
                    .withSubscribeOn(Schedulers::io)
                    .withObserveOn(AndroidSchedulers::mainThread)
                    .build();
        }
    }
}

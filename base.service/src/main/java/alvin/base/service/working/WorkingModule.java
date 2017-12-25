package alvin.base.service.working;

import alvin.base.service.working.presenters.WorkingPresenter;
import alvin.base.service.working.services.WorkingService;
import alvin.base.service.working.view.WorkingActivity;
import alvin.lib.common.rx.RxDecorator;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public interface WorkingModule {

    @ContributesAndroidInjector(modules = {ViewModule.class})
    WorkingActivity workingActivity();

    @ContributesAndroidInjector(modules = {ServiceModule.class})
    WorkingService workingService();

    @Module
    interface ViewModule {
        @Binds
        WorkingContracts.View view(final WorkingActivity activity);

        @Binds
        WorkingContracts.Presenter presenter(final WorkingPresenter presenter);
    }

    @Module
    class ServiceModule {
        @Provides
        public RxDecorator rxDecorator() {
            return RxDecorator.newBuilder()
                    .subscribeOn(Schedulers::io)
                    .observeOn(AndroidSchedulers::mainThread)
                    .build();
        }
    }
}

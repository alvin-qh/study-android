package alvin.base.mvp.department;

import alvin.base.mvp.department.presenters.DepartmentPresenter;
import alvin.base.mvp.department.views.DepartmentEditDialog;
import alvin.lib.common.rx.RxManager;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public interface DepartmentModule {

    @ContributesAndroidInjector(modules = {
            ViewModule.class,
            ProvidesModule.class})
    DepartmentEditDialog departmentEditDialog();

    @Module
    interface ViewModule {
        @Binds
        DepartmentContracts.View view(DepartmentEditDialog dialog);

        @Binds
        DepartmentContracts.Presenter presenter(DepartmentPresenter presenter);
    }

    @Module
    class ProvidesModule {

        @Provides
        RxManager rxManager() {
            return RxManager.newBuilder()
                    .withSubscribeOn(Schedulers::io)
                    .withObserveOn(AndroidSchedulers::mainThread)
                    .build();
        }
    }
}

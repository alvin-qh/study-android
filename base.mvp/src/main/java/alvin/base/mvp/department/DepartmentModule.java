package alvin.adv.mvp.department;

import alvin.adv.mvp.department.presenters.DepartmentPresenter;
import alvin.adv.mvp.department.views.DepartmentEditDialog;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

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
    }
}

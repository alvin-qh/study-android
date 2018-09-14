package alvin.base.database.dbflow;

import alvin.base.database.dbflow.presenters.DBFlowPresenter;
import alvin.base.database.dbflow.views.DBFlowActivity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface DBFlowModule {

    @ContributesAndroidInjector(modules = ActivityModule.class)
    DBFlowActivity dbFlowActivity();

    @Module
    interface ActivityModule {
        @Binds
        DBFlowContracts.View view(DBFlowActivity activity);

        @Binds
        DBFlowContracts.Presenter presenter(DBFlowPresenter presenter);
    }
}

package alvin.base.database.dbflow;

import alvin.base.database.dbflow.presenters.DBFlowPresenter;
import alvin.base.database.dbflow.views.DBFlowActivity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface DBFlowModule {

    @ContributesAndroidInjector(modules = {BindModule.class, ProvidersModule.class})
    DBFlowActivity dbFlowActivity();

    @Module
    interface BindModule {
        @Binds
        DBFlowContracts.View view(DBFlowActivity activity);

        @Binds
        DBFlowContracts.Presenter presenter(DBFlowPresenter presenter);
    }

    @Module
    class ProvidersModule {
    }
}

package alvin.base.database.dbflow;

import alvin.base.database.dbflow.presenters.DBFlowPresenter;
import alvin.base.database.dbflow.views.DBFlowActivity;
import dagger.Binds;
import dagger.Module;

@Module
public interface DBFlowModule {

    @Binds
    DBFlowContracts.View view(DBFlowActivity activity);

    @Binds
    DBFlowContracts.Presenter presenter(DBFlowPresenter presenter);
}

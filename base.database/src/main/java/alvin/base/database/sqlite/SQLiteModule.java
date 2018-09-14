package alvin.base.database.sqlite;

import alvin.base.database.sqlite.presenters.SQLitePresenter;
import alvin.base.database.sqlite.views.SQLiteActivity;
import dagger.Binds;
import dagger.Module;

@Module
public interface SQLiteModule {

    @Binds
    SQLiteContracts.View view(SQLiteActivity activity);

    @Binds
    SQLiteContracts.Presenter presenter(SQLitePresenter presenter);
}

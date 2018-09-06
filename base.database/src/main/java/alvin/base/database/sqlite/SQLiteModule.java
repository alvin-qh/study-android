package alvin.base.database.sqlite;

import alvin.base.database.sqlite.presenters.SQLitePresenter;
import alvin.base.database.sqlite.views.SQLiteActivity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface SQLiteModule {

    @ContributesAndroidInjector(modules = {BindModule.class, ProvidersModule.class})
    SQLiteActivity sqLiteActivity();

    @Module
    interface BindModule {
        @Binds
        SQLiteContracts.View view(SQLiteActivity activity);

        @Binds
        SQLiteContracts.Presenter presenter(SQLitePresenter presenter);
    }

    @Module
    class ProvidersModule {
    }
}

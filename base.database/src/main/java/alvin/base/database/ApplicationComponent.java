package alvin.base.database;

import javax.inject.Singleton;

import alvin.base.database.dbflow.DBFlowModule;
import alvin.base.database.sqlite.SQLiteModule;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ApplicationModule.class,
        DBFlowModule.class,
        SQLiteModule.class
})
public interface ApplicationComponent extends AndroidInjector<Application> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<Application> { }
}

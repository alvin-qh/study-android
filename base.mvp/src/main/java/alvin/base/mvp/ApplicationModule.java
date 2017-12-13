package alvin.base.mvp;

import android.content.Context;

import com.raizlabs.android.dbflow.config.FlowManager;

import javax.inject.Singleton;

import alvin.base.mvp.department.DepartmentModule;
import alvin.base.mvp.domain.MainDatabase;
import alvin.base.mvp.main.MainModule;
import alvin.base.mvp.namecard.NameCardModule;
import alvin.lib.common.dbflow.repositories.TransactionManager;
import alvin.lib.common.utils.SystemServices;
import dagger.Module;
import dagger.Provides;

@Module(includes = {
        MainModule.class,
        NameCardModule.class,
        DepartmentModule.class
})
public class ApplicationModule {

    @Singleton
    @Provides
    TransactionManager transactionManager() {
        return new TransactionManager(FlowManager.getDatabase(MainDatabase.class));
    }

    @Singleton
    @Provides
    Context context(final Application application) {
        return application;
    }

    @Singleton
    @Provides
    SystemServices systemServices(final Context context) {
        return new SystemServices(context);
    }
}

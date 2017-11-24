package alvin.base.mvp;

import com.raizlabs.android.dbflow.config.FlowManager;

import javax.inject.Singleton;

import alvin.base.mvp.domain.MainDatabase;
import alvin.base.mvp.main.MainModule;
import alvin.base.mvp.namecard.NameCardModule;
import alvin.lib.common.dbflow.repositories.TransactionManager;
import dagger.Module;
import dagger.Provides;

@Module(includes = {
        MainModule.class,
        NameCardModule.class
})
public class ApplicationModule {

    @Singleton
    @Provides
    TransactionManager transactionManager() {
        return new TransactionManager(FlowManager.getDatabase(MainDatabase.class));
    }
}

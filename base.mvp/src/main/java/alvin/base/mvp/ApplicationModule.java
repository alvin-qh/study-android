package alvin.base.mvp;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;

import javax.inject.Singleton;

import alvin.base.mvp.domain.MainDatabase;
import alvin.base.mvp.main.MainModule;
import alvin.base.mvp.namecard.presenter.NameCardPresenter;
import dagger.Module;
import dagger.Provides;

@Module(includes = {
        MainModule.class,
        NameCardPresenter.class
})
public class ApplicationModule {

    @Singleton
    @Provides
    DatabaseDefinition databaseDefinition() {
        return FlowManager.getDatabase(MainDatabase.class);
    }
}

package alvin.base.mvp;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;

import javax.inject.Singleton;

import alvin.base.mvp.android.di.AndroidContributesActivityModule;
import alvin.base.mvp.android.views.AndroidContributesActivity;
import alvin.base.mvp.common.db.MessageDatabase;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module(includes = {ApplicationModule.PersistModule.class, ApplicationModule.ViewsRegisterModule.class})
interface ApplicationModule {

    @Module
    class PersistModule {

        @Singleton
        @Provides
        DatabaseDefinition databaseDefinition() {
            return FlowManager.getDatabase(MessageDatabase.class);
        }
    }

    @Module
    interface ViewsRegisterModule {

//      @SomeScopes
        @ContributesAndroidInjector(modules = {AndroidContributesActivityModule.class})
        AndroidContributesActivity androidMainActivity();
    }
}

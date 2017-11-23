package alvin.base.dagger;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.time.LocalDate;

import javax.inject.Singleton;

import alvin.base.dagger.android.contributes.ContributesModule;
import alvin.base.dagger.android.contributes.views.ContributesActivity;
import alvin.base.dagger.common.db.MessageDatabase;
import alvin.base.dagger.common.qualifiers.BirthdayMap;
import alvin.base.dagger.common.qualifiers.NameSet;
import alvin.base.dagger.multibindings.StringMapKey;
import alvin.base.dagger.multibindings.views.MultibindingsActivity;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;
import dagger.multibindings.IntoSet;

@Module(includes = {
        ApplicationModule.PersistModule.class,
        ApplicationModule.MultibindingsModule.class,
        ApplicationModule.ViewsRegisterModule.class
})
interface ApplicationModule {

    @Module
    class PersistModule {

        @Singleton @Provides
        DatabaseDefinition databaseDefinition() {
            return FlowManager.getDatabase(MessageDatabase.class);
        }
    }

    @Module
    abstract class MultibindingsModule {

        @Provides @IntoSet @NameSet
        static String name() {
            return "Alvin";
        }

        @Provides @BirthdayMap @IntoMap
        @StringMapKey("Alvin")
        static LocalDate allenBirthday() {
            return LocalDate.of(1981, 3, 17);   // SUPPRESS
        }
    }

    @Module
    interface ViewsRegisterModule {

        // @SomeScopes
        @ContributesAndroidInjector(modules = {ContributesModule.class})
        ContributesActivity contributesActivity();

        @ContributesAndroidInjector(modules = {alvin.base.dagger.multibindings.MultibindingsModule.class})
        MultibindingsActivity multibindingsActivity();
    }
}

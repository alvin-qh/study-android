package alvin.base.dagger;

import com.raizlabs.android.dbflow.config.FlowManager;

import java.time.LocalDate;

import javax.inject.Singleton;

import alvin.base.dagger.common.domain.db.MessageDatabase;
import alvin.base.dagger.common.qualifiers.BirthdayMap;
import alvin.base.dagger.common.qualifiers.NameSet;
import alvin.base.dagger.multibindings.StringMapKey;
import alvin.lib.common.dbflow.repositories.TransactionManager;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.IntoSet;

@Module
class ApplicationModule {

    @Singleton
    @Provides
    TransactionManager transactionManager() {
        return new TransactionManager(FlowManager.getDatabase(MessageDatabase.class));
    }

    @Provides
    @IntoSet
    @NameSet
    static String name() {
        return "Alvin";
    }

    @Provides
    @BirthdayMap
    @IntoMap
    @StringMapKey("Alvin")
    static LocalDate allenBirthday() {
        return LocalDate.of(1981, 3, 17);   // SUPPRESS
    }
}

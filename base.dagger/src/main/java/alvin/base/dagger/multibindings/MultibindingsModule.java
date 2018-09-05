package alvin.base.dagger.multibindings;

import com.google.common.collect.Sets;

import java.time.LocalDate;
import java.util.Set;

import alvin.base.dagger.multibindings.presenters.MultibindingsPresenter;
import alvin.base.dagger.multibindings.qualifiers.BirthdayMap;
import alvin.base.dagger.multibindings.qualifiers.NameSet;
import alvin.base.dagger.multibindings.qualifiers.StringMapKey;
import alvin.base.dagger.multibindings.views.MultibindingsActivity;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.ElementsIntoSet;
import dagger.multibindings.IntoMap;
import dagger.multibindings.IntoSet;

@Module
public interface MultibindingsModule {

    @ContributesAndroidInjector(modules = {
            BindModule.class,
            ProvidesModuleA.class,
            ProvidesModuleB.class,
            ProvidesModuleC.class
    })
    MultibindingsActivity multibindingsActivity();

    @Module
    interface BindModule {
        @Binds
        MultibindingsContracts.View view(MultibindingsActivity activity);

        @Binds
        MultibindingsContracts.Presenter presenter(MultibindingsPresenter presenter);
    }

    @Module
    class ProvidesModuleA {
        @Provides
        @ElementsIntoSet
        @NameSet
        static Set<String> names() {
            return Sets.newHashSet("Emma", "Allen");
        }

        @Provides
        @BirthdayMap
        @IntoMap
        @StringMapKey("Emma")
        static LocalDate birthday() {
            return LocalDate.of(1985, 3, 29);   // SUPPRESS
        }
    }

    @Module
    class ProvidesModuleB {
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
        static LocalDate birthday() {
            return LocalDate.of(1981, 3, 17);   // SUPPRESS
        }
    }

    @Module
    class ProvidesModuleC {
        @Provides
        @BirthdayMap
        @IntoMap
        @StringMapKey("Allen")
        static LocalDate birthday() {
            return LocalDate.of(1979, 12, 1);   // SUPPRESS
        }
    }
}

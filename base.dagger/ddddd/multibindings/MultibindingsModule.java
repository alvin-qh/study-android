package alvin.adv.dagger.multibindings;

import com.google.common.collect.Sets;

import java.time.LocalDate;
import java.util.Set;

import alvin.adv.dagger.common.qualifiers.BirthdayMap;
import alvin.adv.dagger.common.qualifiers.NameSet;
import alvin.adv.dagger.multibindings.presenters.MultibindingsPresenter;
import alvin.adv.dagger.multibindings.views.MultibindingsActivity;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.ElementsIntoSet;
import dagger.multibindings.IntoMap;

@Module
public interface MultibindingsModule {

    @ContributesAndroidInjector(modules = {
            ViewModule.class,
            ProvidesModule.class
    })
    MultibindingsActivity multibindingsActivity();

    @Module
    interface ViewModule {

        @Binds
        MultibindingsContracts.Presenter presenter(MultibindingsPresenter presenter);

        @Binds
        MultibindingsContracts.IView view(MultibindingsActivity activity);
    }

    @Module
    class ProvidesModule {
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
        static LocalDate emmaBirthday() {
            return LocalDate.of(1985, 3, 29);   // SUPPRESS
        }

        @Provides
        @BirthdayMap
        @IntoMap
        @StringMapKey("Allen")
        static LocalDate allenBirthday() {
            return LocalDate.of(1979, 12, 1);   // SUPPRESS
        }
    }
}

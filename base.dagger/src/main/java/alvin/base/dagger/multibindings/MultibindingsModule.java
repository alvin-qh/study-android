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
import dagger.multibindings.ElementsIntoSet;
import dagger.multibindings.IntoMap;
import dagger.multibindings.IntoSet;

@Module
public interface MultibindingsModule {

    @Binds
    MultibindingsContracts.View view(MultibindingsActivity activity);

    @Binds
    MultibindingsContracts.Presenter presenter(MultibindingsPresenter presenter);

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
    @IntoSet
    @NameSet
    static String name() {
        return "Alvin";
    }

    @Provides
    @BirthdayMap
    @IntoMap
    @StringMapKey("Alvin")
    static LocalDate alvinBirthday() {
        return LocalDate.of(1981, 3, 17);   // SUPPRESS
    }

    @Provides
    @BirthdayMap
    @IntoMap
    @StringMapKey("Allen")
    static LocalDate allenBirthday() {
        return LocalDate.of(1979, 12, 1);   // SUPPRESS
    }
}

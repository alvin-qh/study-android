package alvin.base.mvp.multibindings;

import com.google.common.collect.Sets;

import java.time.LocalDate;
import java.util.Set;

import alvin.base.mvp.common.qualifiers.BirthdayMap;
import alvin.base.mvp.common.qualifiers.NameSet;
import alvin.base.mvp.multibindings.presenters.MultibindingsPresenter;
import alvin.base.mvp.multibindings.views.MultibindingsActivity;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ElementsIntoSet;
import dagger.multibindings.IntoMap;

@Module
public abstract class MultibindingsModule {

    @Provides @ElementsIntoSet @NameSet
    static Set<String> names() {
        return Sets.newHashSet("Emma", "Allen");
    }

    @Provides @BirthdayMap @IntoMap
    @StringMapKey("Emma")
    static LocalDate emmaBirthday() {
        return LocalDate.of(1985, 3, 29);   // SUPPRESS
    }

    @Provides @BirthdayMap @IntoMap
    @StringMapKey("Allen")
    static LocalDate allenBirthday() {
        return LocalDate.of(1979, 12, 1);   // SUPPRESS
    }

    @Binds
    public abstract MultibindingsContracts.Presenter presenter(MultibindingsPresenter presenter);

    @Binds
    public abstract MultibindingsContracts.View view(MultibindingsActivity activity);
}

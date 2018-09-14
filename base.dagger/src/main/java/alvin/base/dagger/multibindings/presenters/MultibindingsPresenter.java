package alvin.base.dagger.multibindings.presenters;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import alvin.base.dagger.multibindings.MultibindingsContracts;
import alvin.base.dagger.multibindings.qualifiers.BirthdayMap;
import alvin.base.dagger.multibindings.qualifiers.NameSet;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;


public class MultibindingsPresenter extends PresenterAdapter<MultibindingsContracts.View>
        implements MultibindingsContracts.Presenter {

    private final Set<String> nameSet;
    private final Map<String, LocalDate> birthdayMap;

    @Inject
    MultibindingsPresenter(MultibindingsContracts.View view,
                           @NameSet Set<String> nameSet,
                           @BirthdayMap Map<String, LocalDate> birthdayMap) {
        super(view);
        this.nameSet = nameSet;
        this.birthdayMap = birthdayMap;
    }

    @Override
    public void loadData() {
        with(view -> view.showNameSet(nameSet));
        with(view -> view.showBirthdayMap(birthdayMap));
    }
}

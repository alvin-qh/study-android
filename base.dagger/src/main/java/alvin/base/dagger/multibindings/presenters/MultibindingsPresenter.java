package alvin.base.dagger.multibindings.presenters;

import android.support.annotation.NonNull;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import alvin.base.dagger.common.qualifiers.BirthdayMap;
import alvin.base.dagger.common.qualifiers.NameSet;
import alvin.base.dagger.multibindings.MultibindingsContracts;
import alvin.lib.mvp.adapters.ViewPresenterAdapter;


public class MultibindingsPresenter
        extends ViewPresenterAdapter<MultibindingsContracts.View>
        implements MultibindingsContracts.Presenter {

    private final Set<String> nameSet;

    private Map<String, LocalDate> birthdayMap;

    @Inject
    public MultibindingsPresenter(@NonNull MultibindingsContracts.View view,
                                  @NameSet Set<String> nameSet,
                                  @BirthdayMap Map<String, LocalDate> birthdayMap) {
        super(view);
        this.nameSet = nameSet;
        this.birthdayMap = birthdayMap;
    }

    @Override
    public void onStart() {
        super.onStart();

        withView(view -> view.showNameSet(nameSet));
        withView(view -> view.showBirthdayMap(birthdayMap));
    }
}

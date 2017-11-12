package alvin.base.mvp.multibindings.presenters;

import android.support.annotation.NonNull;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import alvin.base.mvp.common.qualifiers.BirthdayMap;
import alvin.base.mvp.common.qualifiers.NameSet;
import alvin.base.mvp.multibindings.views.MultibindingsMainActivity;
import alvin.lib.mvp.IPresenter;
import alvin.lib.mvp.PresenterAdapter;


public class MultibindingsPresenter
        extends PresenterAdapter<MultibindingsMainActivity> implements IPresenter {

    private final Set<String> nameSet;

    private Map<String, LocalDate> birthdayMap;

    @Inject
    public MultibindingsPresenter(@NonNull MultibindingsMainActivity view,
                                  @NameSet Set<String> nameSet,
                                  @BirthdayMap Map<String, LocalDate> birthdayMap) {
        super(view);
        this.nameSet = nameSet;
        this.birthdayMap = birthdayMap;
    }

    @Override
    public void started() {
        super.started();

        withView(view -> view.showNameSet(nameSet));
        withView(view -> view.showBirthdayMap(birthdayMap));
    }
}

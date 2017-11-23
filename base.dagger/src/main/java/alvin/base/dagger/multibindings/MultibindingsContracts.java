package alvin.base.dagger.multibindings;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import alvin.lib.mvp.IPresenter;
import alvin.lib.mvp.IView;

public interface MultibindingsContracts {

    interface View extends IView {
        void showNameSet(Set<String> nameSet);

        void showBirthdayMap(Map<String, LocalDate> birthdayMap);
    }

    interface Presenter extends IPresenter { }
}

package alvin.base.dagger.multibindings;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import alvin.lib.mvp.contracts.IPresenter;

public interface MultibindingsContracts {

    interface IView extends alvin.lib.mvp.contracts.IView {
        void showNameSet(Set<String> nameSet);

        void showBirthdayMap(Map<String, LocalDate> birthdayMap);
    }

    interface Presenter extends IPresenter {
        void loadData();
    }
}

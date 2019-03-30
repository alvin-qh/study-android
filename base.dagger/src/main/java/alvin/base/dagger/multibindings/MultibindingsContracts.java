package alvin.base.dagger.multibindings;


import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.IView;
import androidx.annotation.NonNull;

public interface MultibindingsContracts {

    interface View extends IView {
        void showNameSet(@NonNull Set<String> nameSet);

        void showBirthdayMap(@NonNull Map<String, LocalDate> birthdayMap);
    }

    interface Presenter extends IPresenter {
        void loadData();
    }
}

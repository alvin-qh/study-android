package alvin.base.database.dbflow;

import android.support.annotation.NonNull;

import java.util.List;

import alvin.base.database.common.domain.models.Gender;
import alvin.base.database.common.domain.models.IPerson;
import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.IView;

public interface DBFlowContracts {

    interface View extends IView {
        void onPersonGot(List<IPerson> persons);

        void onPersonCreate(IPerson person);

        void onPersonUpdate(IPerson person);

        void onPersonDelete(IPerson person);
    }

    interface Presenter extends IPresenter {
        void savePerson(@NonNull IPerson person);

        void getPersons(@NonNull Gender gender);

        void updatePerson(@NonNull IPerson person);

        void deletePerson(@NonNull IPerson person);
    }
}

package alvin.adv.service.working;

import java.time.LocalDateTime;

import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.IView;

public interface WorkingContracts {

    interface View extends IView {
        void showResult(String result);
    }

    interface Presenter extends IPresenter {
        void gotResult(LocalDateTime dateTime);
    }
}

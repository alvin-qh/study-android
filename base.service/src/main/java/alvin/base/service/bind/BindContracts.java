package alvin.base.service.bind;

import java.time.LocalDateTime;

import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.IView;

public interface BindContracts {

    interface View extends IView {
        void showResult(String result);
    }

    interface Presenter extends IPresenter {
        void gotResult(LocalDateTime dateTime);
    }
}

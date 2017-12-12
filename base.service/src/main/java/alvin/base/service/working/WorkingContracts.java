package alvin.base.service.working;

import java.time.LocalDateTime;

import alvin.base.service.working.services.WorkingService;
import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.IView;

public interface WorkingContracts {

    interface View extends IView {
        void showTime(LocalDateTime time);
    }

    interface Presenter extends IPresenter {
        WorkingService.OnServiceCallbackListener getCallbackListener();
    }
}

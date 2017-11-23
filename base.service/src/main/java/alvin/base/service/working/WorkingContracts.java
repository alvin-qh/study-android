package alvin.base.service.working;

import java.time.LocalDateTime;

import alvin.base.service.working.services.WorkingService;
import alvin.lib.mvp.IPresenter;
import alvin.lib.mvp.IView;

public interface WorkingContracts {

    interface View extends IView {
        void showTime(LocalDateTime time);
    }

    interface Presenter extends IPresenter {
        WorkingService.OnServiceCallbackListener getCallbackListener();
    }
}

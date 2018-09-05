package alvin.adv.service.intent;

import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.IView;

public interface IntentContracts {

    interface View extends IView {
        void doNewJob(String jobName);
    }

    interface Presenter extends IPresenter {
        void makeNewJob();
    }
}

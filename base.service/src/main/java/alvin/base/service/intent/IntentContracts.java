package alvin.base.service.intent;

import android.content.BroadcastReceiver;

import alvin.base.service.intent.services.IntentService;
import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.IView;

public interface IntentContracts {

    interface View extends IView {

        void serviceCreated();

        void serviceDestroyed();

        void onJobStart(String jobName);

        void onJobFinish(String jobName, long timeSpend);
    }

    interface Presenter extends IPresenter {

        BroadcastReceiver getBroadcastReceiver();

        String getJobName();

        IntentService.OnJobStatusChangeListener getListener();
    }
}
